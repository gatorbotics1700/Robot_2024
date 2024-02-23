package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants;
import frc.robot.OI;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class PivotSubsystem{
    public TalonFX pivot;//public for testing
    public DigitalInput topLimitSwitch;
    public DigitalInput bottomLimitSwitch;


    private final double PIVOT_SPEED = 0.08;
    private final double MANUAL_SPEED = 0.06;
    private final double PIVOT_TICKS_PER_DEGREE = (Constants.TICKS_PER_REV * Constants.REVS_PER_ROTATION)/180.0;
    private final double PIVOT_DEADBAND_TICKS = 1000; //TODO: figure out deadband

    private LimelightSubsystem limelightSubsystem = new LimelightSubsystem(); 
    

    public static enum PivotStates{
        SPEAKER,
        AMP,
        MANUAL_UP,
        MANUAL_DOWN,
        VISION,
        OFF;
    }


    private PivotStates pivotState;

    public PivotSubsystem(){
        pivot = new TalonFX(Constants.PIVOT_MOTOR_CAN_ID);
        topLimitSwitch = new DigitalInput(0); //check these ports
        bottomLimitSwitch = new DigitalInput(7); 
        
        init();
    }
    
    public void init(){
        pivotState = PivotStates.VISION;
    }

    public void periodic(){//limit switches true, then false when pressed
     //   System.out.println("CURRENT PIVOT STATE: " + pivotState);
      //  System.out.println("top limit switch: " + topLimitSwitch.get());
        // System.out.println("bottom limit switch: " + bottomLimitSwitch.get());
        if((pivotState == PivotStates.SPEAKER) && topLimitSwitch.get()){
            pivot.set(ControlMode.PercentOutput, PIVOT_SPEED);
        }else if((pivotState == PivotStates.AMP) && bottomLimitSwitch.get()){
            pivot.set(ControlMode.PercentOutput, -PIVOT_SPEED);
        }else if((pivotState == PivotStates.MANUAL_UP) && topLimitSwitch.get()){
            pivot.set(ControlMode.PercentOutput, MANUAL_SPEED);
    
        }else if((pivotState == PivotStates.MANUAL_DOWN) && bottomLimitSwitch.get()){
            pivot.set(ControlMode.PercentOutput, -MANUAL_SPEED);
        }else if(pivotState == PivotStates.VISION){
            visionAdjusting();
        }else{
            //these if statements are for the "windshield wiper" motion (back and forth)
            /*if(pivotState == PivotStates.AMP && !bottomLimitSwitch.get()){
                pivotState = PivotStates.SPEAKER;
            }
            if(pivotState == PivotStates.SPEAKER && !topLimitSwitch.get()){
                pivotState = PivotStates.AMP;
            }*/
            pivotState = PivotStates.OFF;
            pivot.set(ControlMode.PercentOutput, 0);
        }
    }

    private double getAngleTicks(double desiredDegrees){
        return desiredDegrees * PIVOT_TICKS_PER_DEGREE;
    }

   // private double currentAngle = 30; //placeholder until we can get an encoder to give us an actual angle

    public void visionAdjusting(){
        System.out.println("DISTANCE TO TAG: " + limelightSubsystem.distToTag());
        System.out.println("DESIRED SHOOTER ANGLE: " + limelightSubsystem.getDesiredShooterAngle());
        //System.out.println("CURRENT SHOOTER ANGLE: " + "insert current angle here");
        if((pivot.getSelectedSensorPosition() - getAngleTicks(limelightSubsystem.getDesiredShooterAngle())) > PIVOT_DEADBAND_TICKS && topLimitSwitch.get()){
            System.out.println("ADJUSTING NEGATIVE!");
            pivot.set(ControlMode.PercentOutput, -PIVOT_SPEED); //check direction of motors
        } else if ((pivot.getSelectedSensorPosition() - getAngleTicks(limelightSubsystem.getDesiredShooterAngle())) < -PIVOT_DEADBAND_TICKS && bottomLimitSwitch.get()){
            System.out.println("ADJUSTING POSITIVE!");
            pivot.set(ControlMode.PercentOutput, PIVOT_SPEED); //check direction of motors
        }else{
            pivot.set(ControlMode.PercentOutput, 0);
        }
    }


//add limit switch to manual
//make it so we can run manual and buttons at same time



    public void setState(PivotStates newState) {
        pivotState = newState;
    }
    public PivotStates getState(){
        return pivotState;
    }

}