/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.preferences;

import org.slf4j.Logger;

/**
 * DOCS: Insert docs here
 */
public interface IPreferences {

   /**
    * Initialize the preferences for this <i>Unit</i> if they don't exist.
    */
   public void initialize();

   /**
    * Log the preferences managed / contained by this unit.
    *
    * @param logger
    */
   public void logPreferences(Logger logger);

}
