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
    LEDStates state;

    //getting class to draw getStateSequence
    public LEDSSubsystem(){ //AutonomousBasePD auto
        m_led = new AddressableLED(8); // port will change when actually plugged in
        m_ledBuffer = new AddressableLEDBuffer(numLED); // # of LEDS
        m_led.setLength(m_ledBuffer.getLength()); // gets length
    }

    public static enum LEDStates {
        INTAKING, // turns red
        HOLDING, // turns orange
        AT_AMP, // turns blue
        SHOOTING, // turns purple
        NEUTRAL; // turns green
    }

    public void init(){ //was onEnable
        m_led.start();//TODO: 2/13/2024 originally was after set data, but switched to before. check if correct
        //sets data
        m_led.setData(m_ledBuffer);
        state = LEDStates.NEUTRAL;
    }

    public void periodic(){
        if (state == LEDStates.INTAKING){
            for(int i = 0; i < m_ledBuffer.getLength(); i++){
                m_ledBuffer.setRGB(i, 235, 64, 52); //red
            }
        } else if (state == LEDStates.HOLDING) {
            for(int i = 0; i < m_ledBuffer.getLength(); i++){
                m_ledBuffer.setRGB(i, 245, 133, 73); //orange
            }
        } else if (state == LEDStates.AT_AMP) {
            for(int i = 0; i < m_ledBuffer.getLength(); i++){
                m_ledBuffer.setRGB(i, 95, 140, 237); //blue
            }
        } else if (state == LEDStates.SHOOTING) {
            for(int i = 0; i < m_ledBuffer.getLength(); i++){
                m_ledBuffer.setRGB(i, 175, 95, 237); //purple
            }
        } else if (state == LEDStates.NEUTRAL) {
            for(int i = 0; i < m_ledBuffer.getLength(); i++){
                m_ledBuffer.setRGB(i, 109, 252, 116); //green
            }
        }
    }  

    public void setState(LEDStates state) {
        this.state = state;
    }
   
}