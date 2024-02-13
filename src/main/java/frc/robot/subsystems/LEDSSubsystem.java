package frc.robot.subsystems;
import java.awt.Color;


import edu.wpi.first.wpilibj.AddressableLED;
import frc.robot.autonomous.PDState;
import frc.robot.autonomous.Paths;
import frc.robot.autonomous.PDState.AutoStates;
import frc.robot.autonomous.AutonomousBasePD;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import frc.robot.Robot;




public class LEDSSubsystem {
   
    /*final Color DRIVE_COLOR = new Color(0, 255, 0);
    final Color STOP_COLOR = new Color(255, 0, 0);
    final Color FIRST_COLOR = new Color(255, 235, 0);
    final Color INTAKING_COLOR = new Color(0, 119, 255);
    final Color OUTTAKING_COLOR = new Color(255, 136, 0); *///THIS IS NOT VALID ANYMORE

    //0 is placeholder for roborio pwm port
    public AddressableLED m_led;
    private final int numLED = 150; // change if needed

    public AddressableLEDBuffer m_ledBuffer;
    AutonomousBasePD autonomousBasePD;
    LEDStates state = LEDStates.NEUTRAL;

    

   
    //getting class to draw getStateSequence
    public LEDSSubsystem(){ //AutonomousBasePD auto
       // mauto = auto;
        m_led = new AddressableLED(9); // port will change when actually plugged in
        m_ledBuffer = new AddressableLEDBuffer(numLED); // # of LEDS


        m_led.setLength(m_ledBuffer.getLength()); // gets length
    }


    public static enum LEDStates {
        AUTO,
        HAS_NOTE, //orange when has note
        NEUTRAL; //green
    }


    public void init(){ //was onEnable
        //sets data
        m_led.setData(m_ledBuffer);
        m_led.start();
    }

    public void periodic(){
        if (state == LEDStates.AUTO){
            if(autonomousBasePD.getStateSequence()[autonomousBasePD.getStateIndex()].name == PDState.AutoStates.DRIVE){
                m_ledBuffer.setRGB(60, 217, 43, 127);//pink
                

            }
            else if(autonomousBasePD.getStateSequence()[autonomousBasePD.getStateIndex()].name == PDState.AutoStates.STOP){
                m_ledBuffer.setRGB(60, 255,0,0);//red


            }
            else if(autonomousBasePD.getStateSequence()[autonomousBasePD.getStateIndex()].name == PDState.AutoStates.FIRST){
                m_ledBuffer.setRGB(60, 255,235,0);//yellow


            }
            else if(autonomousBasePD.getStateSequence()[autonomousBasePD.getStateIndex()].name == PDState.AutoStates.INTAKING){
                m_ledBuffer.setRGB(60, 0,119,255);//blue


            }
            else if(autonomousBasePD.getStateSequence()[autonomousBasePD.getStateIndex()].name == PDState.AutoStates.OUTTAKING){
                m_ledBuffer.setRGB(60, 182, 64, 255);//purple


            }
            // the index for all these needs to be changed to however many LED's there are
        } else if (state == LEDStates.HAS_NOTE){
            m_ledBuffer.setRGB(60, 255,136,0);//orange
        } else {
            m_ledBuffer.setRGB(60, 0,255,0);//green
        }
    }  


    public void setState(LEDStates state) {
        this.state = state;
    }
   
}
