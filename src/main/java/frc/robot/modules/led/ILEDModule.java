/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.modules.led;

import frc.robot.modules.IModule;
import frc.robot.utils.PKColor8Bit;

/**
 * Add your docs here.
 */
public interface ILEDModule extends IModule {

   /**
    * 
    */
   public void enable();

   /**
    * Sets all the LEDs to the single color specified.
    *
    * @param r
    * @param g
    * @param b
    */
   public void setRGB(int r, int g, int b);

   /**
    * Sets all the LEDs to the single color specified.
    *
    * @param color
    */
   public void setColor(PKColor8Bit color);

}
