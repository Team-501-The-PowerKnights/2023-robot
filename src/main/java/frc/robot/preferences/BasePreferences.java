/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.preferences;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.Preferences;

import riolog.PKLogger;

/**
 * DOCS: Insert docs here
 */
abstract public class BasePreferences implements IPreferences {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(BasePreferences.class.getName());

   protected final String name;

   protected BasePreferences(String name) {
      logger.info("constructing");

      this.name = name;

      logger.info("constructed");
   }

   public void logPreferences(Logger logger) {
      StringBuilder buf = new StringBuilder();
      buf.append(" preferences:");
      for (String key : Preferences.getKeys().stream().filter(k -> k.contains(name)).sorted()
            .collect(Collectors.toCollection(ArrayList::new))) {
         buf.append("\n..."); // logger gobbles up leading spaces
         buf.append(key).append(" = ").append(Preferences.getDouble(key, 3171960.));
      }
      logger.info(buf.toString());
   }

}
