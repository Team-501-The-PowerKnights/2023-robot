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

      props = PropertiesManager.getInstance().getProperties(myName);
      logger.info(props.listProperties());
      String robotName = props.getString("implementation");

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

   // "Proto"
   private static void loadImplementationClass(String myRobotName) {
      String myPkgName = RobotFactory.class.getPackage().getName();
      if (myRobotName.isEmpty()) {
         logger.warn("no class specified; go with robot stub");
         ProblemTracker.addWarning();
         myRobotName = "Stub";
      }

      String myClassName = new StringBuilder().append(myRobotName).append(myName).toString();
      String classToLoad = new StringBuilder().append(myPkgName).append(".").append(myClassName).toString();
      logger.debug("class to load: {}", classToLoad);

      logger.info("constructing {} for {} subsystem", myClassName, myName);
      try {
         @SuppressWarnings("rawtypes")
         Class myClass = Class.forName(classToLoad);
         @SuppressWarnings("deprecation")
         Object myObject = myClass.newInstance();
         ourInstance = (IRobot) myObject;
         SmartDashboard.putNumber(TelemetryNames.Misc.robotStatus, PKStatus.success.tlmValue);
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
         logger.error("failed to load class; instantiating default stub for: {}", myName);
         ProblemTracker.addError();
         ourInstance = new StubRobot();
         SmartDashboard.putNumber(TelemetryNames.Misc.robotStatus, PKStatus.degraded.tlmValue);
      }
      SmartDashboard.putString(TelemetryNames.Misc.robotImplClass, ourInstance.getClass().getSimpleName());
   }

}
