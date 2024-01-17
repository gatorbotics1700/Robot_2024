package frc.robot.subsystems;
import org.opencv.core.Mat;

import edu.wpi.first.apriltag.AprilTagDetection;
import edu.wpi.first.apriltag.AprilTagDetector;
import edu.wpi.first.apriltag.AprilTagPoseEstimator;
import edu.wpi.first.apriltag.AprilTagPoseEstimator.Config;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;
import frc.robot.autonomous.AutonomousBasePD;


public class AprilTagSubsystem {
    private String family = "36811";
    Mat source;
    Mat grayMat;
    AprilTagDetection[] detectedAprilTagArray = {};

    double drivetrainXPosition; 

    private static double tagSize = 6.5; 
    private static double fx; //need to add values
    private static double fy; //need to add values
    private static double cx; //need to add values
    private static double cy; //need to add values
    private static SwerveDrivePoseEstimator swerveDrivePoseEstimator = new SwerveDrivePoseEstimator(Robot.m_drivetrainSubsystem.getKinematics(), Robot.m_drivetrainSubsystem.getGyroscopeRotation(), new SwerveModulePosition[] {Robot.m_drivetrainSubsystem.getFrontLeftModule().getSwerveModulePosition(), Robot.m_drivetrainSubsystem.getFrontRightModule().getSwerveModulePosition(), Robot.m_drivetrainSubsystem.getBackLeftModule().getSwerveModulePosition(), Robot.m_drivetrainSubsystem.getBackLeftModule().getSwerveModulePosition()}, new Pose2d(0.0, 0.0, Robot.m_drivetrainSubsystem.getGyroscopeRotation()));
    private static Pose2d prePose;

    public static enum AprilTagSequence {
        DETECT,
        CORRECTPOSITION,
        OFF;
    }

    private static AprilTagSequence states = AprilTagSequence.DETECT;

    public void setState(AprilTagSequence newState){
        states = newState;
    }
    
    private static AprilTagDetector aprilTagDetector = new AprilTagDetector();
    private static LimeLightSubsystem limeLightSubsystem = new LimeLightSubsystem();
    private static AutonomousBasePD autonomousBasePD;
    private static DrivetrainSubsystem drivetrainSubsystem;

    public final AprilTagPoseEstimator.Config aprilTagPoseEstimatorConfig = new Config(tagSize, fx, fy, cx, cy);
    public AprilTagPoseEstimator aprilTagPoseEstimator = new AprilTagPoseEstimator(aprilTagPoseEstimatorConfig);

    public void init(){
        limeLightSubsystem.setPipeline(1.0);
        Robot.m_drivetrainSubsystem.resetPositionManager(new Pose2d(AprilTagLocation.scoringPoses[4].getX() - 36.5, AprilTagLocation.scoringPoses[4].getY()-12.0, new Rotation2d(Math.toRadians(0.00))));
        System.out.println("reset odometry in INIT to: " + drivetrainSubsystem.getPose());
        System.loadLibrary("opencv_java460");
        aprilTagDetector.addFamily(family, 0);
    }

    public void addVisionToOdometry(){
        Transform3d aprilTagError = aprilTagPoseEstimator.estimate(detectedAprilTag); //april tag pose estimator in Transform 3d
        Pose2d aprilTagPose2D = AprilTagLocation.aprilTagPoses [detectedAprilTag.getID()-1].toPose2d(); //pose 2d of the actual april tag
        Rotation2d robotSubtractedAngle = Rotation2d.fromDegrees(aprilTagPose2D.getRotation().getDegrees()-aprilTagError.getRotation().toRotation2d().getDegrees()); //angle needed to create pose 2d of robot position, don't know if toRotation2D converts Rotation2D properly
        Pose2d robotPose2DAprilTag = new Pose2d(aprilTagPose2D.getX()-aprilTagError.getX(), aprilTagPose2D.getY()-aprilTagError.getY(), robotSubtractedAngle);
        swerveDrivePoseEstimator.addVisionMeasurement(robotPose2DAprilTag, Timer.getFPGATimestamp());
    }

    public void periodic(){
        if(states==AprilTagSequence.DETECT){
            limeLightSubsystem.reset();
            //detectTag();
            if(LimeLightSubsystem.tv!=0){
                System.out.println("APRIL TAG DETECTED!!!!!!");
                setState(AprilTagSequence.CORRECTPOSITION);
                System.out.println("Reset odometry to this pose: " + DrivetrainSubsystem.pose);
                autonomousBasePD.preDDD(drivetrainSubsystem.getPose(), AprilTagLocation.scoringPoses[1]);
            }
        } else if(states == AprilTagSequence.CORRECTPOSITION) {
            correctPosition();
            if(autonomousBasePD.xAtSetpoint() && autonomousBasePD.yAtSetpoint() && autonomousBasePD.turnAtSetpoint()) {
                setState(AprilTagSequence.OFF);
                System.out.println("Finished correcting position!!!!!!");
            }
        }
    }

    public void detectTag(){
        if(detectedAprilTagsArray.length == 0){
            return;
        } else {
            detectedAprilTag = detectedAprilTagsArray[0];
        }

        System.out.println("Detected Apriltag: " + detectedAprilTag.getID());
    }

    private void correctPosition(){
        System.out.println("I am correcting position!!!");
        while(LimeLightSubsystem.tv!=0){
            System.out.println("I am still detecting April Tag and driving");
            autonomousBasePD.driveDesiredDistance(AprilTagLocation.scoringPoses[1]);
            Robot.m_drivetrainSubsystem.drive();
        }

        Robot.m_drivetrainSubsystem.stopDrive();
    }
}
