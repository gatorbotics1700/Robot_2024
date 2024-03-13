package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import frc.robot.autonomous.PDState;
// import frc.robot.autonomous.Paths;
// import frc.robot.autonomous.PDState.AutoStates;
import frc.robot.autonomous.AutonomousBasePD;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import frc.robot.Robot;

public class LEDSSubsystem {

    public AddressableLED m_led;
    private final int numLED = 150; // change if needed

    public AddressableLEDBuffer m_ledBuffer;
    AutonomousBasePD autonomousBasePD;
    LEDStates state = LEDStates.NEUTRAL;

    //getting class to draw getStateSequence
    public LEDSSubsystem(){ //AutonomousBasePD auto
        m_led = new AddressableLED(8); // port will change when actually plugged in
        m_ledBuffer = new AddressableLEDBuffer(numLED); // # of LEDS
        m_led.setLength(m_ledBuffer.getLength()); // gets length
    }

    public static enum LEDStates {
        AUTO, // different colors for each state within auto (drive, stop, first, intaking, outtaking)
        HAS_NOTE, // turns orange
        AT_AMP,
        NEUTRAL; // turns green
    }

    public void init(){ //was onEnable
        m_led.start();//TODO: 2/13/2024 originally was after set data, but switched to before. check if correct
        //sets data
        m_led.setData(m_ledBuffer);
    }

    public void periodic(){
        if (state == LEDStates.AUTO){
            if(autonomousBasePD.getStateSequence()[autonomousBasePD.getStateIndex()].name == PDState.AutoStates.DRIVE){
                for(int i = 0; i < m_ledBuffer.getLength(); i++){
                    m_ledBuffer.setRGB(i, 217, 43, 127);//pink
                }
            }
            else if(autonomousBasePD.getStateSequence()[autonomousBasePD.getStateIndex()].name == PDState.AutoStates.STOP){
                for(int i = 0; i < m_ledBuffer.getLength(); i++){
                    m_ledBuffer.setRGB(i, 255,0,0);//red
                }
            }
            else if(autonomousBasePD.getStateSequence()[autonomousBasePD.getStateIndex()].name == PDState.AutoStates.FIRST){
                for(int i = 0; i < m_ledBuffer.getLength(); i++){
                    m_ledBuffer.setRGB(i, 255,235,0);//yellow
                }
            }
            else if(autonomousBasePD.getStateSequence()[autonomousBasePD.getStateIndex()].name == PDState.AutoStates.INTAKING){
                for(int i = 0; i < m_ledBuffer.getLength(); i++){
                    m_ledBuffer.setRGB(i, 0,119,255);//blue
                }
            }
            else if(autonomousBasePD.getStateSequence()[autonomousBasePD.getStateIndex()].name == PDState.AutoStates.OUTTAKING){
                for(int i = 0; i < m_ledBuffer.getLength(); i++){
                    m_ledBuffer.setRGB(i, 182, 64, 255);//purple
                }
            }
        } else if (state == LEDStates.HAS_NOTE){
            for(int i = 0; i < m_ledBuffer.getLength(); i++){
                m_ledBuffer.setRGB(i, 255,136,0);//orange
            }
        }else if(state == LEDStates.AT_AMP) {
            for(int i = 0; i < m_ledBuffer.getLength(); i++){
                m_ledBuffer.setRGB(i, 128, 20, 32);//burgundy
            }
        } else {
            for(int i = 0; i < m_ledBuffer.getLength(); i++){
                m_ledBuffer.setRGB(i, 0,255,0);//green
            }
        }
    }  

    public void setState(LEDStates state) {
        this.state = state;
    }
   
}