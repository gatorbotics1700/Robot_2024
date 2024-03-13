package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants;
import frc.robot.OI;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class PivotSubsystem{
    private TalonFX pivot;
    private DigitalInput ampLimitSwitch;
    private DigitalInput stageLimitSwitch;

    private static final double _kP = 0.2;//TODO tune PID
    private static final double _kI = 0.0;
    private static final double _kD = 0.0;
    private static final int _kIzone = 0;
    private static final double _kPeakOutput = 1.0;

    private Gains pivotGains = new Gains(_kP, _kI, _kD, _kIzone, _kPeakOutput);
    private static double deadband = 7000; //TODO change deadband (in ticks)

    private final double PIVOT_TICKS_PER_DEGREE = 0;//TODO ask build for diameters, etc.
    private final double MANUAL_SPEED = 0.1;
    private final double AMP_ANGLE = 0; //try 93 for now, 96 is more realistic
    private final double SPEAKER_ANGLE = 0;//TODO determine angle
    private final double STAGE_ANGLE = 0; //TODO determine angle
    
    public static enum PivotStates{
        AMP,
        SPEAKER,
        STAGE, //TODO add to buttons/mechanisms
        MANUAL,
        OFF;
        //TODO add climb state?
    }

    private PivotStates pivotState;

    public PivotSubsystem(){
        ampLimitSwitch = new DigitalInput(9); 
        stageLimitSwitch = new DigitalInput(8);//stage limit switch

        pivot = new TalonFX(Constants.PIVOT_MOTOR_CAN_ID);
        pivot.setNeutralMode(NeutralMode.Brake);
        pivot.setSelectedSensorPosition(AMP_ANGLE*PIVOT_TICKS_PER_DEGREE);//sets encoder to recognize starting position as amp (flat to ground is 0 deg)
        pivot.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		/* Config Position Closed Loop gains in slot0, typically kF stays zero. */
            pivot.config_kP(Constants.kPIDLoopIdx, pivotGains.kP, Constants.kTimeoutMs);
            pivot.config_kI(Constants.kPIDLoopIdx, pivotGains.kI, Constants.kTimeoutMs);
            pivot.config_kD(Constants.kPIDLoopIdx, pivotGains.kD, Constants.kTimeoutMs);

        init();
    }
    
    public void init(){
        setState(PivotStates.OFF);
    }

    public void periodic(){//TODO get angles and adjust (is stage lower than amp??)
        System.out.println("CURRENT PIVOT STATE: " + pivotState);
        if((pivotState == PivotStates.AMP) && !atAmp() && !ampLimitSwitch.get()){
            setPivot(AMP_ANGLE);
        }else if((pivotState == PivotStates.SPEAKER) && !atSpeaker()){
            setPivot(SPEAKER_ANGLE);
        }else if(pivotState == PivotStates.STAGE && !atStage() && !stageLimitSwitch.get()){
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
        double desiredTicks = desiredAngle * PIVOT_TICKS_PER_DEGREE; //calculate right ticks
        double diff = desiredTicks - pivot.getSelectedSensorPosition();

        if(Math.abs(diff) > deadband){ //set motor to right ticks
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
        return Math.abs(pivot.getSelectedSensorPosition()-(AMP_ANGLE*PIVOT_TICKS_PER_DEGREE))< deadband;
    }

    public boolean atSpeaker(){
        return Math.abs(pivot.getSelectedSensorPosition()-(SPEAKER_ANGLE*PIVOT_TICKS_PER_DEGREE))< deadband;
    }

    public boolean atStage(){
        return Math.abs(pivot.getSelectedSensorPosition()-(STAGE_ANGLE*PIVOT_TICKS_PER_DEGREE))< deadband;
    }

    public boolean getAmpLimitSwitch(){ // false when NOT pressed, true when pressed
        return ampLimitSwitch.get();
    }

    public boolean getStageLimitSwitch(){ // false when NOT pressed, true when pressed
        return stageLimitSwitch.get();
    }

}