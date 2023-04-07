/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.modules.led;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

import frc.robot.Robot;
import frc.robot.utils.PKColor8Bit;

import riolog.PKLogger;
import riolog.RioLogger;

class AddressibleLEDModule extends BaseLEDModule {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(AddressibleLEDModule.class.getName());

   private final AddressableLED m_led;
   private final AddressableLEDBuffer m_ledBuffer;

   public AddressibleLEDModule(int port, int length) {
      logger.info("constructing");

      m_led = new AddressableLED(port);

      // Reuse buffer
      // Default to a length of 60, start empty output
      // Length is expensive to set, so only set it once, then just update data
      m_ledBuffer = new AddressableLEDBuffer(length);
      m_led.setLength(m_ledBuffer.getLength());

      for (int i = 0; i < m_ledBuffer.getLength(); i++) {
         m_ledBuffer.setRGB(i, 0, 0, 0);
      }
      // Set the data
      m_led.setData(m_ledBuffer);
      m_led.start();

      enable();

      logger.info("constructed");
   }

   @Override
   public void disable() {
      m_led.stop();
      setTlmEnabled(false);
   }

   @Override
   public void enable() {
      m_led.start();
      setTlmEnabled(true);
   }

   @Override
   public void setRGB(int r, int g, int b) {
      if (Robot.isFieldConnected()) {
         r *= 0.60;
         g *= 0.60;
         b *= 0.60;
      } else {
         r *= 0.10;
         g *= 0.10;
         b *= 0.10;
      }
      for (int i = 0; i < m_ledBuffer.getLength(); i++) {
         m_ledBuffer.setRGB(i, r, g, b);
      }
      m_led.setData(m_ledBuffer);
      // FIXME: How do we determine color tlm from here?
   }

   @Override
   public void setColor(PKColor8Bit color) {
      setRGB(color.red, color.green, color.blue);
      setTlmColor(color);
   }

}
