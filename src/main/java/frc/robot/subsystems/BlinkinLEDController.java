package frc.robot.subsystems;

import java.util.HashMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.Constants;
//code came from this chief delphi post: https://www.chiefdelphi.com/t/rev-blinkin-example-code/452871/3
public class BlinkinLEDController {
    public enum BlinkinPattern {
        RAINBOW_PARTY_PALETTE(-0.97),
        DARK_RED(+0.58), //good
        RED_ORANGE(+0.62), //good
        LIME(+0.73), //good
        DARK_BLUE(+0.85), //good
        BLACK(+0.99); //good
        public final double value;
        private BlinkinPattern(double value) {
          this.value = value;
        }
  };
  private static BlinkinLEDController m_controller = null;
    private static Spark m_blinkin;
    private static BlinkinPattern m_currentPattern;
  public BlinkinLEDController() {
    m_blinkin = new Spark(9);
  }
  public void setPattern(BlinkinPattern pattern) {
    m_currentPattern = pattern;
    m_blinkin.set(m_currentPattern.value);
  }
  public static BlinkinLEDController getInstance() {
    if (m_controller == null) m_controller = new BlinkinLEDController();
    return m_controller;
  }
     
}
