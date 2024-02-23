package frc.robot.subsystems;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import frc.robot.Constants;


public class LimelightSubsystem {

    public static final double HEIGHT_TO_TAG = 57.1225; //height from limelight to center of aprilTag, TODO: subtract limelight height
    public static final double HEIGHT_TO_SPEAKER = 79.3725; //height from shooter hinge to speaker, TODO: subtract shooter height
    public static final double LIMELIGHT_SHOOTER_DIST = 31; //lateral distance between limelight and shooter hinge, TODO: placeholder value, ask build for actual number
    public static final double SPEAKER_DEPTH = 10; //distance from back of speaker to where we want to shoot, TODO: placeholder value, find actual number

    private NetworkTable networkTable = NetworkTableInstance.getDefault().getTable("limelight");
    
    public void setPipeline(double pipeline){
        networkTable.getEntry("pipeline").setNumber(pipeline);
        System.out.println("pipeline:" + networkTable.getEntry("pipeline").getDouble(0.0));
    }

    public double getTv(){
        return networkTable.getEntry("tv").getDouble(0.0); //0.0 or 1.0 depending on whether it sees the target
    }

    public double getTy(){
        return networkTable.getEntry("ty").getDouble(0.0); //get vertical angle (crosshair to target)
    }

    public double getDesiredShooterAngle(){
        return Math.toDegrees(Math.atan(HEIGHT_TO_SPEAKER/(distToTag() + LIMELIGHT_SHOOTER_DIST - SPEAKER_DEPTH)));
    }

    public double distToTag(){
        System.out.println("TY IN RADIANS: " + Math.toRadians(getTy()));
        return HEIGHT_TO_TAG/Math.tan(Math.toRadians(getTy()));
    }
    
}