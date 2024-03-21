package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.subsystems.DrivetrainSubsystem;

public class Limelight {
    public NetworkTable networkTable = NetworkTableInstance.getDefault().getTable("limelight");
    private final double aprilTagHeight = 0; //speaker april tag
    private final double limelightHeight = 0;
    private final double limelightAngle = 0;
    private final double pivotToSpeakerHeight = 68.39; //inches

    private PIDController turnController;
    private static final double turnKP= 0.0; 
    private static final double turnKI= 0.0; 
    private static final double turnKD= 0.0; 

    private static final double TURN_DEADBAND = 3;

    private DrivetrainSubsystem drivetrainSubsystem;


    public double getTv(){
        return networkTable.getEntry("tv").getDouble(0);
    }

    public double getTx(){ //in degrees
        return networkTable.getEntry("tx").getDouble(0);
    }

    public double getTy(){
        return networkTable.getEntry("ty").getDouble(0);
    }

    public double getDistance(){
        return (aprilTagHeight - limelightHeight)/ Math.tan(limelightAngle + getTy());
    }

    private boolean turnAtSetpoint(){
        return Math.abs(turnController.getPositionError()) <= TURN_DEADBAND;
    }

    public void driveToLocation(double rotation){      
        turnController.setSetpoint(drivetrainSubsystem.getPoseDegrees() + rotation);
        double speedRotate = turnController.calculate(drivetrainSubsystem.getPoseDegrees(), drivetrainSubsystem.getPoseDegrees() + rotation);
        
        if(turnAtSetpoint()){
            speedRotate = 0;
            System.out.println("At rotational setpoint");
        }

        drivetrainSubsystem.setSpeed(ChassisSpeeds.fromFieldRelativeSpeeds(0, 0, speedRotate, drivetrainSubsystem.getPoseRotation()));  
    } 

    public double getDesiredPivotAngle(){
        return Math.atan(pivotToSpeakerHeight/(getDistance()+6));
    }
}
