/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.armrotator;

import frc.robot.subsystems.SubsystemNames;

import riolog.PKLogger;
import riolog.RioLogger;

public final class ArmRotatorProperties {

   /** Our classes' logger **/
   @SuppressWarnings("unused")
   private static final PKLogger logger = RioLogger.getLogger(ArmRotatorProperties.class.getName());

   private ArmRotatorProperties() {
   }

   static private final String name = SubsystemNames.armRotatorName;
   static final String className = name + ".className";
   static final String defaultCommandName = name + ".defaultCommandName";

}
