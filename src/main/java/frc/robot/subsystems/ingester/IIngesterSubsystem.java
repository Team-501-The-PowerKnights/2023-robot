/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.ingester;

import frc.robot.subsystems.ISubsystem;

/**
 * DOCS: Add your docs here.
 */
public interface IIngesterSubsystem extends ISubsystem {

   /**
    * Stop the ingester from any motion it may have been running under.
    */
   public void stop();

   /**
    * Pull the object into the ingester.
    */
   public void pullIn();

   /**
    * Push the object out of the ingester.
    */
   public void pushOut();

   /**
    * Pull the object into the ingester using the <code>idleSpeed</code>
    * preference.
    */
   public void idleIn();

   /**
    * Ingest the object.
    *
    * @param speed
    *           speed to rotate at ("+" is in, "-" is out)
    */
   public void ingest(double speed);

}
