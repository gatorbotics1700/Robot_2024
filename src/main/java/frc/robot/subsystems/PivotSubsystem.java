package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants;
import frc.robot.OI;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class PivotSubsystem{
    public TalonFX pivot;
    private DigitalInput ampLimitSwitch;
    private DigitalInput stageLimitSwitch;

    private static final double _kP = 0.03;//TODO tune PID
    private static final double _kI = 0.0;
    private static final double _kD = 0.0;
    private static final int _kIzone = 0;
    private static final double _kPeakOutput = 1.0;

    private Gains pivotGains = new Gains(_kP, _kI, _kD, _kIzone, _kPeakOutput);
    private PivotStates pivotState;

    private final double PIVOT_TICKS_PER_DEGREE = 2048 * 100 / 360;
    private final double MANUAL_SPEED = 0.1;
    // TODO check if angle values work - changed so that selectedSensorPosition is 0 in init (amp)
    //we want amp angle to be like 90 degrees and parallel to floor to be 0 degrees for clarity
    private final double AMP_ANGLE = 0.0;//93; //try 93 for now, 96 is more realistic
    private final double SPEAKER_ANGLE = -55.0;//45;//TODO test
    private final double STAGE_ANGLE = -70.0;//25;
    private double deadband = 2 * PIVOT_TICKS_PER_DEGREE;
    
    public static enum PivotStates{
        AMP,
        SPEAKER,
        STAGE, //TODO add to buttons/mechanisms
        MANUAL,
        OFF;
        //TODO add climb state?
    }

    public PivotSubsystem(){
        pivot = new TalonFX(Constants.PIVOT_MOTOR_CAN_ID);
        ampLimitSwitch = new DigitalInput(9); 
        stageLimitSwitch = new DigitalInput(5);

        init();
    }

    public void init(){
        pivot.setNeutralMode(NeutralMode.Brake);
        pivot.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		/* Config Position Closed Loop gains in slot0, typically kF stays zero. */
            pivot.config_kP(Constants.kPIDLoopIdx, pivotGains.kP, Constants.kTimeoutMs);
            pivot.config_kI(Constants.kPIDLoopIdx, pivotGains.kI, Constants.kTimeoutMs);
            pivot.config_kD(Constants.kPIDLoopIdx, pivotGains.kD, Constants.kTimeoutMs);
        pivot.setSelectedSensorPosition(AMP_ANGLE*PIVOT_TICKS_PER_DEGREE);//sets encoder to recognize starting position as amp (flat to ground is 0 deg)
        // setSelectedSensorPosition() takes in sensorPos, pidIdx, and timeoutMs, so TODO add kPIDLoopIdx and kTimeoutMs
        // ^ test this by changing the angle constants back to original versions and see print values are nonzero (should be abt 90 degrees and 51200 ticks)
        System.out.println("********PIVOT POSITION IN INIT: " + pivot.getSelectedSensorPosition());
        
        setState(PivotStates.OFF);
    }

    public void periodic(){
        System.out.println("CURRENT PIVOT STATE: " + pivotState);
        System.out.println("position ticks: " + pivot.getSelectedSensorPosition());//prints O.0
        System.out.println(pivot.getSelectedSensorPosition()/PIVOT_TICKS_PER_DEGREE);
        System.out.println("is at amp: " + atAmp());
        System.out.println("is at speaker: " + atSpeaker())
        if((pivotState == PivotStates.AMP) && !atAmp() && !ampLimitSwitch.get()){
            setPivot(AMP_ANGLE);
        }else if((pivotState == PivotStates.SPEAKER) && !atSpeaker()){
            setPivot(SPEAKER_ANGLE);
        }else if(pivotState == PivotStates.STAGE && !atStage() && !stageLimitSwitch.get()){//TODO check if one of these conditions is the problem
            System.out.println("IN STAGE!!!!!!!!");
            setPivot(STAGE_ANGLE);
        }else if(pivotState == PivotStates.MANUAL){
            manual();
        }else if(pivotState == PivotStates.OFF){
            pivot.set(ControlMode.PercentOutput, 0);  
        }else{
            System.out.println("=========UNRECOGNIZED PIVOT STATE: " + pivotState.toString() + "========");
            pivotState = PivotStates.OFF;
        }
    }
    
    public void manual() {//limit switches act as a failsafe
        //System.out.println("+++++++++++IN MANUAL++++++++++");
        if((OI.getCodriverRightAxis() < - 0.2) && !stageLimitSwitch.get()) {
            //System.out.println("TOWARDS STAGE");
            pivot.set(ControlMode.PercentOutput, MANUAL_SPEED);    
        } else if((OI.getCodriverRightAxis() > 0.2) && !ampLimitSwitch.get()) {
            //System.out.println("TOWARDS AMP");
            pivot.set(ControlMode.PercentOutput, -MANUAL_SPEED);  
        } else {
            pivot.set(ControlMode.PercentOutput, 0); 
        }
    }

    public void setPivot(double desiredAngle){
        double desiredTicks = desiredAngle * PIVOT_TICKS_PER_DEGREE; //calculates right ticks
        System.out.println("desiredTicks: " + desiredTicks);
        double diff = desiredTicks - pivot.getSelectedSensorPosition();
        System.out.println("diff: " + diff);

        if(Math.abs(diff) > deadband){ //sets motor to right ticks
            pivot.set(ControlMode.Position, Math.signum(diff) * desiredTicks);
        }else{
            pivot.set(ControlMode.PercentOutput, 0);
        }
    }

    public void setState(PivotStates newState) {
        pivotState = newState;
    }

    public PivotStates getState(){
        return pivotState;
    }

    public boolean atAmp(){
        return (Math.abs(pivot.getSelectedSensorPosition()-(AMP_ANGLE*PIVOT_TICKS_PER_DEGREE)) < deadband);
    }

    public boolean atSpeaker(){
        return (Math.abs(pivot.getSelectedSensorPosition()-(SPEAKER_ANGLE*PIVOT_TICKS_PER_DEGREE)) < deadband);
    }

    public boolean atStage(){
        return (Math.abs(pivot.getSelectedSensorPosition()-(STAGE_ANGLE*PIVOT_TICKS_PER_DEGREE)) < deadband);
    }

    public boolean getAmpLimitSwitch(){ // false when NOT pressed, true when pressed
        return ampLimitSwitch.get();
    }

    public boolean getStageLimitSwitch(){ // false when NOT pressed, true when pressed
        return stageLimitSwitch.get();
    }

}