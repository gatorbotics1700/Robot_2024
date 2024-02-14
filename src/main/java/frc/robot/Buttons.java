package frc.robot;

import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ShooterSubsystem.ShooterStates;

public class Buttons {
    
  private DrivetrainSubsystem m_drivetrainSubsystem = Robot.m_drivetrainSubsystem;
  private ShooterSubsystem m_shooterSubsystem = Robot.m_shooterSubsystem;
  
  public void buttonsPeriodic(){

      //driver
      if (OI.m_controller.getBButton()){ //emergency stop EVERYTHING
        m_drivetrainSubsystem.stopDrive(); 
      }
      if (OI.m_controller_two.getAButton()){
        //m_shooterSubsystem.setState(ShooterStates.ADJUSTING);
      }
  }
}
