package frc.robot.subsystems;
import frc.robot.subsystems.*;
import frc.robot.subsystems.AprilTagSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;

public class VisionSubsystem {
    public static enum VisionStates{
        DETECTAPRILTAG,
        OFF;
    }

    public VisionStates visionState = VisionStates.OFF;
    private static AprilTagSubsystem aprilTagSubsystem;
    private static LimeLightSubsystem limeLightSubsystem;

    public void setState(VisionStates state){
        visionState = state;
    }

    public void init(){ 
    }

    public void periodic() {
        if(visionState == VisionStates.DETECTAPRILTAG) {
            limeLightSubsystem.setPipeline(0.0);
            if (LimeLightSubsystem.tv == 0) {
                return;
            }
            aprilTagSubsystem.setState(AprilTagSubsystem.AprilTagSequence.DETECT);
            setState(VisionStates.OFF);
        }
    }
}
