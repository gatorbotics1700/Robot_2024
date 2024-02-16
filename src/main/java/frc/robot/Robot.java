// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.Mechanisms;
import frc.robot.subsystems.SensorSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.autonomous.AutonomousBase;
import frc.robot.autonomous.AutonomousBasePD; 
import frc.robot.autonomous.PDState;
import frc.robot.autonomous.Paths;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */

public class Robot extends TimedRobot {

    private final SendableChooser<Boolean> inverted = new SendableChooser<>();
    private final SendableChooser<Boolean> allianceChooser = new SendableChooser<>();
    private final SendableChooser<Paths.AUTO_OPTIONS> auto_chooser = new SendableChooser<>();

    
    public static final IntakeSubsystem m_intakingSubsystem = new IntakeSubsystem();
    public static final Mechanisms m_mechanismSubsystem = new Mechanisms();
    public static final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
    public static final SensorSubsystem m_sensorSubsystem = new SensorSubsystem();

    public static final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem(); //if anything breaks in the future it might be this
    public static Buttons m_buttons = new Buttons();
    private AutonomousBase m_auto; 
    double mpi = Constants.METERS_PER_INCH;
    public static Boolean isBlueAlliance = true;

  
  /**
  * This function is run when the robot is turned on and should be used for any
  * initialization code.
  */
  @Override
  public void robotInit() { //creates options for different autopaths, names are placeholders    
    System.out.println("#I'm Awake");

    auto_chooser.setDefaultOption("PD testPath", Paths.AUTO_OPTIONS.PD_TESTPATH);
    auto_chooser.addOption("noGoR!", Paths.AUTO_OPTIONS.NO_GO);
    auto_chooser.addOption("3 Piece A", Paths.AUTO_OPTIONS.THREE_PIECE_A);
    auto_chooser.addOption("3 Piece B", Paths.AUTO_OPTIONS.THREE_PIECE_B);
    auto_chooser.addOption("# Piece AMP", Paths.AUTO_OPTIONS.THREE_PAMP);
    auto_chooser.addOption("4 Piece A", Paths.AUTO_OPTIONS.FOUR_PIECE_A);
    auto_chooser.addOption("4 Piece B", Paths.AUTO_OPTIONS.FOUR_PIECE_B);
    auto_chooser.addOption("5 Piece", Paths.AUTO_OPTIONS.FIVE_PIECE);
    auto_chooser.addOption("Anaika's Dream", Paths.AUTO_OPTIONS.ANAIKAS_DREAM);
    auto_chooser.addOption("Bread", Paths.AUTO_OPTIONS.BREAD);
    auto_chooser.addOption("Fallback A", Paths.AUTO_OPTIONS.FALLBACK_A);
    auto_chooser.addOption("Fallback B", Paths.AUTO_OPTIONS.FALLBACK_B);
    auto_chooser.addOption("Fallback C", Paths.AUTO_OPTIONS.FALLBACK_C);

    SmartDashboard.putData("Auto Choices", auto_chooser); 
    
    inverted.setDefaultOption("true", true);
    inverted.addOption("false", false);
    //m_drivetrainSubsystem.autoInitCalled = false;
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("x odometry",m_drivetrainSubsystem.getPoseX()/Constants.METERS_PER_INCH);
    SmartDashboard.putNumber("y odometry",m_drivetrainSubsystem.getPoseY()/Constants.METERS_PER_INCH);
    SmartDashboard.putNumber("angle odometry",m_drivetrainSubsystem.getPoseDegrees()%360);
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
   // m_drivetrainSubsystem.autoInitCalled = false;
    Paths.AUTO_OPTIONS selectedAuto = auto_chooser.getSelected(); 
    m_auto = Paths.constructAuto(selectedAuto); 
    m_mechanismSubsystem.init();
    
    //System.out.println("starting x: " + m_auto.getStartingPoseX() + "starting y: " + m_auto.getStartingPoseY() + "starting rotation: " + m_auto.getStartingPoseRotation());
    // m_drivetrainSubsystem.init(m_auto.getStartingPoseX(), m_auto.getStartingPoseY(), m_auto.getStartingPoseRotation());

    // m_drivetrainSubsystem.autoInitCalled = true;
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    m_auto.periodic();
   m_mechanismSubsystem.periodic();
    m_drivetrainSubsystem.drive();
    //System.out.println("current pose " + m_drivetrainSubsystem.getPose());
  }



  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() { //BEFORE TESTING: MAKE SURE YOU HAVE EITHER DEPLOYED OR ADDED DRIVETRAIN INIT
    isBlueAlliance = allianceChooser.getSelected();
    m_drivetrainSubsystem.init();
    m_buttons.buttonsPeriodic();
    //m_mechanismSubsystem.init();
    //m_mechanismSubsystem.setState(Mechanisms.MechanismStates.SHOOTING_SPEAKER);

    //m_drivetrainSubsystem.resetPositionManager(); //for testing 
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() { 
   // m_buttons.buttonsPeriodic();
    //m_mechanismSubsystem.periodic();
    m_drivetrainSubsystem.driveTeleop();
    m_drivetrainSubsystem.drive(); 
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
    //m_drivetrainSubsystem.init();
    m_mechanismSubsystem.init();
    m_mechanismSubsystem.setState(Mechanisms.MechanismStates.SHOOTING_SPEAKER);
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    //OFFSETS
    //m_drivetrainSubsystem.driveTeleop();
    m_mechanismSubsystem.periodic();
    //m_drivetrainSubsystem.setSpeed(ChassisSpeeds.fromFieldRelativeSpeeds(0.5, 0, 0, m_drivetrainSubsystem.getPoseRotation()));
    //m_drivetrainSubsystem.drive();
  }
  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}