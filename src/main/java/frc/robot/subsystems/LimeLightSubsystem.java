package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;

public class LimeLightSubsystem {
    private final double MOUNTING_ANGLE = 2.0; //degrees change
    private final double SPEAKER_HEIGHT = 2.045; //This is the middle of the speaker hole
    private final double ROBOT_HEIGHT = 20.0; //inches change
    private final double IDEAL_DISTANCE = 40.0; //inches change
    private static double tv;
    private static double ty;

    private NetworkTable networkTable = NetworkTableInstance.getDefault().getTable("limelight");
    
    public void setPipeline(double pipeline){
        networkTable.getEntry("pipeline").setNumber(pipeline);
        System.out.println("pipeline:" + networkTable.getEntry("pipeline").getDouble(0.0));
    }

    public void init(){
        tv = networkTable.getEntry("tv").getDouble(0.0);//valid targets? 0 or 1
        ty = networkTable.getEntry("ty").getDouble(0.0); //vertical offset from -20.5-20.5 degrees
    }

    public void periodic(){

    }

    // turning tx and ty into distance (in)
    public double yDistanceFromIdeal(){
        double distanceFromTarget = (SPEAKER_HEIGHT- ROBOT_HEIGHT)/Math.tan(Math.toRadians(MOUNTING_ANGLE + ty));
        System.out.println("distance from target: " + distanceFromTarget);
        double distanceFromIdeal = distanceFromTarget - IDEAL_DISTANCE;
        System.out.println("distance from ideal: " + distanceFromIdeal);
        return distanceFromIdeal;
    }


    public double getTv(){
        return networkTable.getEntry("tv").getDouble(0.0);
    }

    public double getTy(){
        return ty; //get vertical angle
    }

}