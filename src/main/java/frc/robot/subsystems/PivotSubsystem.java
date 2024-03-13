package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants;
import frc.robot.OI;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class PivotSubsystem{
    private TalonFX pivot;
    private DigitalInput speakerLimitSwitch;
    private DigitalInput ampLimitSwitch;

    private static final double _kP = 0.2;//TODO tune PID
    private static final double _kI = 0.0;
    private static final double _kD = 0.0;
    private static final int _kIzone = 0;
    private static final double _kPeakOutput = 1.0;

    private Gains pivotGains = new Gains(_kP, _kI, _kD, _kIzone, _kPeakOutput);
    private static double deadband = 7000; //TODO change deadband (in ticks)

    private final double PIVOT_TICKS_PER_DEGREE = 0;//TODO ask build for diameters, etc.
    private final double PIVOT_SPEED = 0.12;
    private final double MANUAL_SPEED = 0.1;
    private final double SPEAKER_ANGLE = 0;//TODO determine angles
    private final double AMP_ANGLE = 0;
    private final double STAGE_ANGLE = 0;
    
    public static enum PivotStates{
        SPEAKER, //TODO add to PID if wanted
        AMP,
        STAGE, //TODO add to buttons/mechanisms
        MANUAL,
        OFF;
        //TODO add climb state?
    }

    private PivotStates pivotState;

    public PivotSubsystem(){
        speakerLimitSwitch = new DigitalInput(8);
        ampLimitSwitch = new DigitalInput(9); 

        pivot = new TalonFX(Constants.PIVOT_MOTOR_CAN_ID);
        pivot.setNeutralMode(NeutralMode.Brake);
        pivot.setSelectedSensorPosition(AMP_ANGLE*PIVOT_TICKS_PER_DEGREE);//TODO make this the top degree (flat to ground is 0 deg)
        pivot.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		/* Config Position Closed Loop gains in slot0, typically kF stays zero. */
            pivot.config_kP(Constants.kPIDLoopIdx, pivotGains.kP, Constants.kTimeoutMs); //TODO add to constants
            pivot.config_kI(Constants.kPIDLoopIdx, pivotGains.kI, Constants.kTimeoutMs);
            pivot.config_kD(Constants.kPIDLoopIdx, pivotGains.kD, Constants.kTimeoutMs);

        init();
    }
    
    public void init(){
        setState(PivotStates.OFF);
    }

    public void periodic(){//TODO get angles and adjust (is stage lower than amp??)
        System.out.println("CURRENT PIVOT STATE: " + pivotState);
        if((pivotState == PivotStates.SPEAKER) && !atSpeaker() && !speakerLimitSwitch.get()){
            setPivot(SPEAKER_ANGLE);
        }else if((pivotState == PivotStates.AMP) && !atAmp() && !ampLimitSwitch.get()){
            setPivot(AMP_ANGLE);
        }else if(pivotState == PivotStates.STAGE && !atStage()){
            setPivot(STAGE_ANGLE);
        }else if(pivotState == PivotStates.MANUAL){
            manual();
        }else if(pivotState == PivotStates.OFF){
            pivot.set(ControlMode.PercentOutput, 0);  
        }else{
            System.out.println("=========UNRECOGNIZED PIVOT STATE: " + pivotState.toString() + "========");
            pivotState = PivotStates.OFF;
            pivot.set(ControlMode.PercentOutput, 0);
        }
    }
   
    public void manual() {//limit switches act as a failsafe
        //System.out.println("+++++++++++IN MANUAL++++++++++");
        if((OI.getCodriverRightAxis() > 0.2) && !speakerLimitSwitch.get()) {
            //System.out.println("TOWARDS SPEAKER");
            pivot.set(ControlMode.PercentOutput, MANUAL_SPEED);    
        } else if((OI.getCodriverRightAxis() < - 0.2) && !ampLimitSwitch.get()) {
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
            pivotMotor.set(ControlMode.Position, Math.signum(diff) * desiredTicks);
        }else{
            pivotMotor.set(ControlMode.Output, 0);
        }
    }

    public void setState(PivotStates newState) {
        pivotState = newState;
    }

    public PivotStates getState(){
        return pivotState;
    }

    public boolean atSpeaker(){
        return Math.abs(pivot.getSelectedSensorPosition()-(SPEAKER_ANGLE*PIVOT_TICKS_PER_DEGREE))< DEADBAND;
    }

    public boolean atAmp(){
        return Math.abs(pivot.getSelectedSensorPosition()-(AMP_ANGLE*PIVOT_TICKS_PER_DEGREE))< DEADBAND;
    }

    public boolean atStage(){
        return Math.abs(pivot.getSelectedSensorPosition()-(STAGE_ANGLE*PIVOT_TICKS_PER_DEGREE))< DEADBAND;
    }

    public boolean getSpeakerLimitSwitch(){ // false when NOT pressed, true when pressed
        return speakerLimitSwitch.get();
    }

    public boolean getAmpLimitSwitch(){ // false when NOT pressed, true when pressed
        return ampLimitSwitch.get();
    }

}