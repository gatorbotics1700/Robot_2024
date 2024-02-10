package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;

public class LimelightSubsystem {
    
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

}