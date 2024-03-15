package frc.robot;

import frc.robot.subsystems.PivotSubsystem.PivotStates;
import frc.robot.subsystems.Mechanisms;
import frc.robot.subsystems.PivotSubsystem;
import frc.robot.subsystems.Mechanisms.MechanismStates;
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
      //if (OI.codriver.getXButton()){}

      if (OI.codriver.getYButton()){ //all off mech
        m_mechanismSubsystem.setState(MechanismStates.OFF);
        System.out.println("=======Y BUTTON====ALL OFF MECH=======");
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
      }
      
      if (OI.codriver.getBButtonPressed()){ //intaking toggle
        // if(m_mechanismSubsystem.getMechanismState() == MechanismStates.INTAKING){
        //   m_mechanismSubsystem.setState(MechanismStates.OFF);
        //   System.out.println("=======B BUTTON====INTAKING OFF=======");
        // } else {
        //   m_mechanismSubsystem.setState(MechanismStates.INTAKING);
        //   System.out.println("=======B BUTTON====INTAKING ON=======");
        // }
        System.out.println("====BACK BUTTON PRESSED====PIVOT STAGE====");
        m_mechanismSubsystem.pivotSubsystem.setState(PivotStates.STAGE);
      }

      if(OI.codriver.getLeftBumper()){
        //m_mechanismSubsystem.setState(MechanismStates.SPEAKER_HOLDING);
        //System.out.println("=======LEFT BUMPER====SPEAKER HOLDING=======");
        m_mechanismSubsystem.pivotSubsystem.setState(PivotStates.SPEAKER);
        System.out.println("====LEFT BUMPER====PIVOT SPEAKER====");
      }
      
      if(OI.codriver.getRightBumper()){
        //m_mechanismSubsystem.setState(MechanismStates.AMP_HOLDING);
        //System.out.println("=======RIGHT BUMPER====AMP HOLDING=======");
        m_mechanismSubsystem.pivotSubsystem.setState(PivotStates.AMP);
        System.out.println("====RIGHT BUMPER====PIVOT AMP====");
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

      // if(OI.codriver.getBackButtonPressed()){//NEW ADDITION! set to stage
      //   System.out.println("====BACK BUTTON PRESSED====PIVOT STAGE====");
      //   m_mechanismSubsystem.setState(MechanismStates.STAGE);
      // }
  }
}