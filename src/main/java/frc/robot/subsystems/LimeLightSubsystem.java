package frc.robot.subsystems;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.subsystems.*;
import frc.robot.Constants; 

public class LimeLightSubsystem {
    private final double MOUNTINGANGLE = 2.0; //degrees change
    private final double LOWERHEIGHT = 22.124; //inches height of bottom to bottom of tape of shorter pole
    private final double HIGHERHEIGHT = 41.125; //inches height of bottom to bottom of tape of shorter pole
    private final double ROBOTHEIGHT = 20.0; //inches change
    private final double IDEALDISTANCE = 40.0; //inches change
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
    /*public double yDistanceFromIdeal(){
        double distanceFromTarget = (LOWERHEIGHT- ROBOTHEIGHT)/Math.tan(Math.toRadians(MOUNTINGANGLE + ty));
        System.out.println("distance from target: " + distanceFromTarget);
        double distanceFromIdeal = distanceFromTarget - IDEALDISTANCE;
        System.out.println("distance from ideal: " + distanceFromIdeal);
        return distanceFromIdeal;
    }*/


    public double getTv(){
        return networkTable.getEntry("tv").getDouble(0.0);
    }

    public double getTy(){
        return ty; //get vertical angle
    }

}