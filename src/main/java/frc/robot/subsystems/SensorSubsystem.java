package frc.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import frc.com.swervedrivespecialties.swervelib.Mk4SwerveModuleHelper;
import frc.com.swervedrivespecialties.swervelib.SdsModuleConfigurations;
import frc.com.swervedrivespecialties.swervelib.SwerveModule;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.kinematics.SwerveModulePosition;

import java.util.function.DoubleSupplier;

import frc.robot.OI;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix6.signals.NeutralModeValue;

import com.revrobotics.ColorSensorV3; 
import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.I2C; // TODO: check if this is right

import frc.robot.Constants;


public class SensorSubsystem {

    private SensorStates sensorState;
    public boolean seesNote;

    public SensorSubsystem(){
        init();
    }

    public static enum SensorStates{
        OFF,
        ON;
    }

    public void init(){
        System.out.println("sensor init");
        setState(SensorStates.OFF);
        seesNote = false;
    
    }

    public void periodic(){
        if(seesNote == false){
            setState(SensorStates.ON);
        }
        else if (seesNote){
            setState(SensorStates.OFF);
        }
        else{
            setState(SensorStates.OFF);
        }
   
    }
    
    private void setState(SensorStates sensorState){
        this.sensorState = sensorState;
    }
}
