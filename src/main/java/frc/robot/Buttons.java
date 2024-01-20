package frc.robot;

import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ShooterSubsystem.ShooterStates;

public class Buttons {
    
  private DrivetrainSubsystem m_drivetrainSubsystem = Robot.m_drivetrainSubsystem;
  private ShooterSubsystem m_shooterSubsystem = Robot.m_shooterSubsystem; //put this in robot.java
  public void buttonsPeriodic(){
      //driver
      if (OI.m_controller.getLeftBumper()){ //emergency stop EVERYTHING
        m_drivetrainSubsystem.stopDrive(); 
      }
      if (OI.m_controller.getAButton()){ 
        m_shooterSubsystem.setState(ShooterStates.AMP);
      }
      if (OI.m_controller.getYButton()){ 
        m_shooterSubsystem.setState(ShooterStates.SPEAKER_BLUE);
      }
      if (OI.m_controller.getBButton()){ 
        m_shooterSubsystem.setState(ShooterStates.SPEAKER_RED);
      }
      if (OI.m_controller.getRightBumper()){ 
        m_shooterSubsystem.setState(ShooterStates.OFF);
      }
      

  }
}
