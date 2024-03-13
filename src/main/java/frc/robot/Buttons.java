package frc.robot;

import frc.robot.subsystems.PivotSubsystem.PivotStates;
import frc.robot.subsystems.Mechanisms;
import frc.robot.subsystems.PivotSubsystem;
import frc.robot.subsystems.Mechanisms.MechanismStates;

import com.ctre.phoenix6.mechanisms.MechanismState;

import frc.robot.subsystems.DrivetrainSubsystem;

public class Buttons {
    
  private DrivetrainSubsystem m_drivetrainSubsystem = Robot.m_drivetrainSubsystem;
  private Mechanisms m_mechanismSubsystem = Robot.m_mechanismSubsystem;
  
  public void buttonsPeriodic(){
    //DRIVER
      if (OI.driver.getLeftBumper()){
         m_drivetrainSubsystem.stopDrive(); 
      }

      if(OI.driver.getStartButton()){
        System.out.println("=======START BUTTON====REMAKING DRIVETRAIN=======");
        m_drivetrainSubsystem.resetOffsets();
        m_drivetrainSubsystem.onEnable();
      }

      if(OI.driver.getRightBumperPressed()){
        if(m_drivetrainSubsystem.getSlowDrive()){
          m_drivetrainSubsystem.setSlowDrive(false);
        }else{
          m_drivetrainSubsystem.setSlowDrive(true);
        }
      }
      

    //CODRIVER
      if (OI.codriver.getXButton()){ //manual
        m_mechanismSubsystem.pivotSubsystem.setState(PivotStates.SPEAKER);
        System.out.println("=======X BUTTON====SPEAKER PIVOT=======");
      }

      if (OI.codriver.getYButton()){ //all off mech
        m_mechanismSubsystem.setState(MechanismStates.OFF);
        //m_mechanismSubsystem.pivotSubsystem.setState(PivotStates.AMP);
        System.out.println("=======Y BUTTON====AMP PIVOT=======");
      }

      if (OI.codriver.getAButton()){  //amp/speaker shooting
         if(m_mechanismSubsystem.getMechanismState() == MechanismStates.AMP_HOLDING){
          m_mechanismSubsystem.setState(MechanismStates.SHOOTING_AMP);
          System.out.println("=====A BUTTON=====SHOOTING IN AMP!!");
        }else if(m_mechanismSubsystem.getMechanismState() == MechanismStates.SPEAKER_HOLDING){
          m_mechanismSubsystem.setState(MechanismStates.SHOOTING_SPEAKER);
          System.out.println("=====A BUTTON=====SHOOTING IN SPEAKER!!");
        }else{
          System.out.println("=====A BUTTON=====ERROR NOT IN HOLDING CANNOT SHOOT !!!!!!!!!!!!!!!!!");
        }

        //m_mechanismSubsystem.pivotSubsystem.setState(PivotStates.MANUAL);
      }
      
      if (OI.codriver.getBButtonPressed()){ //intaking toggle
       // m_mechanismSubsystem.setState(MechanismStates.INTAKING);
        // m_mechanismSubsystem.setState(MechanismStates.SPEAKER_HOLDING);
        if(m_mechanismSubsystem.getMechanismState() == MechanismStates.INTAKING){
          m_mechanismSubsystem.setState(MechanismStates.OFF);
          System.out.println("=======B BUTTON====INTAKING OFF=======");
        } else {
          m_mechanismSubsystem.setState(MechanismStates.INTAKING);
          System.out.println("=======B BUTTON====INTAKING ON=======");
        }
        
      }
      if(OI.codriver.getLeftBumper()){
        m_mechanismSubsystem.setState(MechanismStates.SPEAKER_HOLDING);
        System.out.println("=======LEFT BUMPER====SPEAKER HOLDING=======");
      }
      
      if(OI.codriver.getRightBumper()){
        m_mechanismSubsystem.setState(MechanismStates.AMP_HOLDING);
        System.out.println("=======RIGHT BUMPER====AMP HOLDING=======");
      }

      if(OI.codriver.getStartButtonPressed()){
        System.out.println("start button pressed");
        if(m_mechanismSubsystem.getMechanismState() == MechanismStates.MANUAL){
          m_mechanismSubsystem.setState(MechanismStates.OFF);
        } else {
          System.out.println("in manual pivot!");
          m_mechanismSubsystem.setState(MechanismStates.MANUAL);
        }
      }

      if(OI.codriver.getPOV() > 45 && OI.codriver.getPOV() < 135){
        // if statement is just in case so that we only switch to vomiting if we are intaking
        // TODO might need to change this
        if(m_mechanismSubsystem.getMechanismState() == MechanismStates.INTAKING){ 
          System.out.println("=====VOMITING NOTE=====");
          m_mechanismSubsystem.setState(MechanismStates.VOMITING);
        }
      }

      if(OI.codriver.getPOV() > 225 && OI.codriver.getPOV() < 315){
        System.out.println("========SWALLOWING NOTE BACK IN========");
        m_mechanismSubsystem.setState(MechanismStates.SWALLOWING);
      }
  }
}