package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import frc.robot.autonomous.PDState;
// import frc.robot.autonomous.Paths;
// import frc.robot.autonomous.PDState.AutoStates;
import frc.robot.autonomous.AutonomousBasePD;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
//import frc.robot.Robot;

public class LEDSSubsystem {

    //0 is placeholder for roborio pwm port
    public AddressableLED m_led;
    private final int numLED = 150; // change if needed

    public AddressableLEDBuffer m_ledBuffer;
    AutonomousBasePD autonomousBasePD;
    LEDStates state = LEDStates.NEUTRAL;

    //getting class to draw getStateSequence
    public LEDSSubsystem(){ //AutonomousBasePD auto
        m_led = new AddressableLED(9); // port will change when actually plugged in
        m_ledBuffer = new AddressableLEDBuffer(numLED); // # of LEDS
        m_led.setLength(m_ledBuffer.getLength()); // gets length
    }

    public static enum LEDStates {
        AUTO, // different colors for each state within auto
        HAS_NOTE, // 
        NEUTRAL; 
    }

    public void init(){ //was onEnable
        //sets data
        m_led.setData(m_ledBuffer);
        m_led.start();
    }

    public void periodic(){
        if (state == LEDStates.AUTO){
            if(autonomousBasePD.getStateSequence()[autonomousBasePD.getStateIndex()].name == PDState.AutoStates.DRIVE){
                m_ledBuffer.setRGB(m_ledBuffer.getLength(), 217, 43, 127);//pink
            }
            else if(autonomousBasePD.getStateSequence()[autonomousBasePD.getStateIndex()].name == PDState.AutoStates.STOP){
                m_ledBuffer.setRGB(m_ledBuffer.getLength(), 255,0,0);//red
            }
            else if(autonomousBasePD.getStateSequence()[autonomousBasePD.getStateIndex()].name == PDState.AutoStates.FIRST){
                m_ledBuffer.setRGB(m_ledBuffer.getLength(), 255,235,0);//yellow
            }
            else if(autonomousBasePD.getStateSequence()[autonomousBasePD.getStateIndex()].name == PDState.AutoStates.INTAKING){
                m_ledBuffer.setRGB(m_ledBuffer.getLength(), 0,119,255);//blue
            }
            else if(autonomousBasePD.getStateSequence()[autonomousBasePD.getStateIndex()].name == PDState.AutoStates.OUTTAKING){
                m_ledBuffer.setRGB(m_ledBuffer.getLength(), 182, 64, 255);//purple
            }
        } else if (state == LEDStates.HAS_NOTE){
            m_ledBuffer.setRGB(m_ledBuffer.getLength(), 255,136,0);//orange
        } else {
            m_ledBuffer.setRGB(m_ledBuffer.getLength(), 0,255,0);//green
        }
    }  

    public void setState(LEDStates state) {
        this.state = state;
    }
   
}
