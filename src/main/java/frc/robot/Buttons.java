package frc.robot;

import frc.robot.subsystems.DrivetrainSubsystem;
import edu.wpi.first.math.kinematics.ChassisSpeeds;

public class Buttons {
    
  private DrivetrainSubsystem m_drivetrainSubsystem = Robot.m_drivetrainSubsystem;
  private ChassisSpeeds xSpeed;
  private ChassisSpeeds ySpeed;
  public void buttonsPeriodic(){
    xSpeed = new ChassisSpeeds(0.2,0.0,0.0);
    ySpeed = new ChassisSpeeds(0.0,0.2,0.0);

      //driver
      if (OI.m_controller.getBButton()){ //emergency stop EVERYTHING
        m_drivetrainSubsystem.stopDrive(); 
      }

      if (OI.m_controller.getXButton()){
        m_drivetrainSubsystem.setSpeed(xSpeed);
      }

      if (OI.m_controller.getYButton()){
        m_drivetrainSubsystem.setSpeed(ySpeed);
      }
      if (OI.m_controller.getAButton()){
        if (m_drivetrainSubsystem.isSlow == false){
          m_drivetrainSubsystem.isSlow = true;
          m_drivetrainSubsystem.slowDriveTeleop();
          System.out.println("Slowly Driving!");
        }
        else {
          m_drivetrainSubsystem.driveTeleop();
          System.out.println("Normal Driving!");
        }
      }

}
}
