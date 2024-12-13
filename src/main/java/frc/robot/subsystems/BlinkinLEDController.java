package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.Constants;
//code came from this chief delphi post: https://www.chiefdelphi.com/t/rev-blinkin-example-code/452871/3

public class BlinkinLEDController {

  private BlinkinLEDController m_controller = null;
  private Spark m_blinkin;

  public BlinkinLEDController() {
    m_blinkin = new Spark(1);
  }

  public void setPattern(double pattern) {
    m_blinkin.set(pattern);
  }

  public BlinkinLEDController getInstance() {
    if (m_controller == null) m_controller = new BlinkinLEDController();
    return m_controller;
  }
     
}
