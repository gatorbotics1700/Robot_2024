package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
//import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
//import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.Mechanisms;
import frc.robot.subsystems.Mechanisms.MechanismStates;
import frc.robot.subsystems.PivotSubsystem;
import frc.robot.subsystems.PivotSubsystem.PivotStates;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ShooterSubsystem.ShooterStates;
import frc.robot.subsystems.PivotSubsystem;

public class Buttons {
    
  //private DrivetrainSubsystem m_drivetrainSubsystem = Robot.m_drivetrainSubsystem;
  private Mechanisms m_mechanismSubsystem = Robot.m_mechanismSubsystem;
  private PivotSubsystem m_pivotSubsystem = Robot.m_pivotSubsystem;
  //private IntakeSubsystem m_intakeSubsystem = Robot.m_mechanismSubsystem.intakeSubsystem;
  //private ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  public double leftTrigger;
  public double rightTrigger;
  
  public void buttonsPeriodic(){
      //driver
      /*if (OI.m_controller.getBButton()){ //emergency stop EVERYTHING
        m_drivetrainSubsystem.stopDrive(); 
      }*/
      
      // if (OI.m_controller.getLeftBumper()){ //emergency stop EVERYTHING
      //   m_drivetrainSubsystem.stopDrive(); 
      // } 
      System.out.println("in buttons periodic");

      if (OI.m_controller_two.getAButton()){ 
        System.out.println("A BUTTON: SPEAKER");
        m_pivotSubsystem.setState(PivotStates.SPEAKER); 
      }

      if (OI.m_controller_two.getXButton()){ 
        System.out.println("X BUTTON: OFF");
        m_pivotSubsystem.setState(PivotStates.OFF); 
      }

      if (OI.m_controller_two.getYButton()){ 
        System.out.println("Y BUTTON AMP");
        m_pivotSubsystem.setState(PivotStates.AMP); 
      }

      //manual
      //from patricia: maybe use dpad instead
             // TODO: when we know the max rotation of the pivot motor we need to intergrate that here 
      if(OI.getTwoRightAxis() > 0.2) {
        m_pivotSubsystem.setState(PivotStates.MANUAL_UP);    
      } else if(OI.getTwoRightAxis() < - 0.2) {
        m_pivotSubsystem.setState(PivotStates.MANUAL_DOWN); 
      }else if(m_pivotSubsystem.getState()== PivotStates.MANUAL_UP || m_pivotSubsystem.getState()== PivotStates.MANUAL_DOWN){
        m_pivotSubsystem.setState(PivotStates.OFF);
      }

      
      //if (OI.m_controller_two.getAButton()){ 
       // m_mechanismSubsystem.setState(MechanismStates.SHOOTING_AMP);
       // System.out.println("=====A BUTTON=====SHOOTING IN AMP!!");
      //}
      // if (OI.m_controller_two.getXButton()){ 
      //   m_mechanismSubsystem.setState(MechanismStates.SHOOTING_SPEAKER);
      //   System.out.println("=======X BUTTON======SHOOTING IN SPEAKER!!");
      // }
      // if (OI.m_controller_two.getYButton()){ 
      //   m_mechanismSubsystem.setState(MechanismStates.OFF);
      //   System.out.println("=======Y BUTTON====MECHANISMS STOP=======");
      // }
      // if (OI.m_controller_two.getBButton()){
      //   if(m_mechanismSubsystem.getMechanismState() == MechanismStates.INTAKING){
      //     m_mechanismSubsystem.setState(MechanismStates.OFF);
      //   } else {
      //     m_mechanismSubsystem.setState(MechanismStates.INTAKING);
      //   }
      //   // TODO: for transition, we can use this button to turn both on and off at same time
      // }
      // if(OI.m_controller_two.getLeftBumper()){
      //   m_mechanismSubsystem.setState(MechanismStates.AMP_HOLDING);
      // }
      // if(OI.m_controller_two.getRightBumper()){
      //   m_mechanismSubsystem.setState(MechanismStates.SPEAKER_HOLDING);
      // }
  }

}