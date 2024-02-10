package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;

public class LimeLightSubsystem {
    private final double MOUNTING_ANGLE = 0.0; //degrees change TODO: ask build about mounting angle of shooter
    private final double SPEAKER_HEIGHT = 2.045; //This is the middle of the speaker hole
    private final double ROBOT_HEIGHT = 0.0; //inches change TODO: ask build about robo height

    private NetworkTable networkTable = NetworkTableInstance.getDefault().getTable("limelight");
    
    public void setPipeline(double pipeline){
        networkTable.getEntry("pipeline").setNumber(pipeline);
        System.out.println("pipeline:" + networkTable.getEntry("pipeline").getDouble(0.0));
    }

    public double getTv(){
        return networkTable.getEntry("tv").getDouble(0.0);
    }

    public double getTy(){
        return networkTable.getEntry("ty").getDouble(0.0); //get vertical angle
    }

}