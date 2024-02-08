package frc.robot.subsystems;
import static frc.robot.Constants.*;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.autonomous.AutonomousBasePD;
import frc.robot.subsystems.*;
import frc.robot.subsystems.LimeLightSubsystem;

public class VisionSubsystem {
    public static enum VisionStates{
        SPEAKER_LINE_UP,
        OFF;
    }

    public VisionStates visionState = VisionStates.OFF;
    private static LimeLightSubsystem limeLightSubsystem;
    private static AutonomousBasePD autonomousBasePD;
    private static DrivetrainSubsystem drivetrainSubsystem;

    public void setState(VisionStates state){
        visionState = state;
    }

    public void init(){ 
        //this is empty :(
    }

    public void periodic() {
        /*if(visionState == VisionStates.DETECT_RED_APRILTAG) {
            limeLightSubsystem.setPipeline(0.0);
            if (LimeLightSubsystem.tv == 0) {
                return;
            }
            if (Math.abs(LimeLightSubsystem.tx) > VISION_TURN_DEADBAND) {
                autonomousBasePD.driveToLocation(new Pose2d(drivetrainSubsystem.getPoseX(), drivetrainSubsystem.getPoseY(), new Rotation2d(Math.toRadians(drivetrainSubsystem.getPoseDegrees() + LimeLightSubsystem.tx))));
            }
            setState(VisionStates.OFF);
        }else if(visionState == VisionStates.DETECT_BLUE_APRILTAG) {
            limeLightSubsystem.setPipeline(1.0);
            if (LimeLightSubsystem.tv == 0) {
                return;
            }
            if (Math.abs(LimeLightSubsystem.tx) > VISION_TURN_DEADBAND) {
                autonomousBasePD.driveToLocation(new Pose2d(drivetrainSubsystem.getPoseX(), drivetrainSubsystem.getPoseY(), new Rotation2d(Math.toRadians(drivetrainSubsystem.getPoseDegrees() + LimeLightSubsystem.tx))));
            }
            setState(VisionStates.OFF);
        }else{
            //comment for if testing System.out.println("not detecting"); 
        }*/
        if(visionState == VisionStates.SPEAKER_LINE_UP) {
            limeLightSubsystem.setPipeline(1.0);
            if(LimeLightSubsystem.tv == 0.0){
                return;
            } else {
                //need to figure out how to line up
                
            }
        }
    }
}
