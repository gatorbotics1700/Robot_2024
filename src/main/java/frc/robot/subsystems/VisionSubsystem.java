package frc.robot.subsystems;
import static frc.robot.Constants.VISION_TURN_DEADBAND;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.autonomous.AutonomousBasePD;
import frc.robot.subsystems.*;
import frc.robot.subsystems.LimeLightSubsystem;

public class VisionSubsystem {
    public static enum VisionStates{
        DETECT_RED_APRILTAG,
        DETECT_BLUE_APRILTAG,
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
        if(visionState == VisionStates.DETECT_RED_APRILTAG) {
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
        }
    }
}
