/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.robot;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.properties.PKProperties;
import frc.robot.properties.PropertiesManager;
import frc.robot.properties.PropertyNames;
import frc.robot.telemetry.TelemetryNames;
import frc.robot.utils.PKStatus;

import riolog.PKLogger;
import riolog.ProblemTracker;

public class RobotFactory {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(RobotFactory.class.getName());

   /** Singleton instance of class for all to use **/
   private static IRobot ourInstance;
   /** Name of our subsystem **/
   private static final String myName = PropertyNames.Robot.name;

   /** Properties for robot */
   private static PKProperties props;

   /**
    * Constructs instance of the robot. Assumed to be called before any usage
    * of the robot; and verifies only called once. Allows controlled startup
    * sequencing of the robot.
    **/
   public static synchronized void constructInstance() {
      SmartDashboard.putNumber(TelemetryNames.Misc.robotStatus, PKStatus.inProgress.tlmValue);

      if (ourInstance != null) {
         throw new IllegalStateException(myName + " Already Constructed");
      }

      props = PropertiesManager.getInstance().getProperties("Misc");
      logger.info(props.listProperties());
      String robotName = props.getString("robotName");

      loadImplementationClass(robotName);
   }

   /**
    * Returns the singleton instance of the robot in the form of the
    * <i>Interface</i> that is defined for it. If it hasn't been constructed yet,
    * throws an <code>IllegalStateException</code>.
    *
    * @return singleton instance of robot
    **/
   public static IRobot getInstance() {
      if (ourInstance == null) {
         throw new IllegalStateException(myName + " Not Constructed Yet");
      }

      return ourInstance;
   }

   private static void loadImplementationClass(String myRobotName) {
      if (myRobotName.isEmpty()) {
         logger.warn("no class specified; go with subsystem default");
         ProblemTracker.addWarning();
         myRobotName = "Stub-Bot";
      }

      logger.debug("robot to load: {}", myRobotName);
      switch (myRobotName) {

         case "Suitcase-Bot":
            ourInstance = new SuitcaseRobot();
            break;

         case "Swprog-Bot":
            ourInstance = new SwprogRobot();
            break;

         case "Proto-Bot":
            ourInstance = new ProtoRobot();
            break;

         case "Real-Bot":
            ourInstance = new RealRobot();
            break;

         case "Stub-Bot":
         default:
            ourInstance = new StubRobot();
            break;
      }
   }

}
