/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.sensors.gyro;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.properties.PKProperties;
import frc.robot.properties.PropertiesManager;
import frc.robot.sensors.SensorNames;
import frc.robot.telemetry.TelemetryNames;
import frc.robot.utils.PKStatus;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * 
 */
public class GyroFactory {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(GyroFactory.class.getName());

   /** Singleton instance of class for all to use **/
   private static IGyroSensor ourInstance;
   /** Name of our subsystem **/
   private static final String myName = SensorNames.gyroName;

   /** Properties for subsystem */
   private static PKProperties props;

   /**
    * Constructs instance of the sensor. Assumed to be called before any usage of
    * the sensor; and verifies only called once. Allows controlled startup
    * sequencing of the robot and all it's sensors.
    **/
   public static synchronized void constructInstance() {
      SmartDashboard.putNumber(TelemetryNames.Gyro.status, PKStatus.inProgress.tlmValue);

      if (ourInstance != null) {
         throw new IllegalStateException(myName + " Already Constructed");
      }

      props = PropertiesManager.getInstance().getProperties(myName);
      logger.info(props.listProperties());
      String className = props.getString("className");

      loadImplementationClass(className);
   }

   private static void loadImplementationClass(String myClassName) {
      String myPkgName = GyroFactory.class.getPackage().getName();
      if (myClassName.isEmpty()) {
         logger.info("no class specified; go with subsystem default");
         myClassName = new StringBuilder().append(PropertiesManager.getInstance().getImpl()).append(myName)
               .append("Subsystem").toString();
      }
      String classToLoad = new StringBuilder().append(myPkgName).append(".").append(myClassName).toString();
      logger.debug("class to load {}", classToLoad);

      logger.info("constructing {} for {} sensor", myClassName, myName);
      try {
         @SuppressWarnings("rawtypes")
         Class myClass = Class.forName(classToLoad);
         @SuppressWarnings("deprecation")
         Object myObject = myClass.newInstance();
         ourInstance = (IGyroSensor) myObject;
         SmartDashboard.putNumber(TelemetryNames.Gyro.status, PKStatus.success.tlmValue);
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
         logger.error("failed to load class; instantiating default stub for {}", myName);
         ourInstance = new StubGyroSensor();
         SmartDashboard.putNumber(TelemetryNames.Gyro.status, PKStatus.degraded.tlmValue);
      }
      SmartDashboard.putString(TelemetryNames.Gyro.implClass, ourInstance.getClass().getSimpleName());
   }

   /**
    * Returns the singleton instance of the sensor in the form of the
    * <i>Interface</i> that is defined for it. If it hasn't been constructed yet,
    * throws an <code>IllegalStateException</code>.
    *
    * @return singleton instance of sensor
    **/
   public synchronized static IGyroSensor getInstance() {
      if (ourInstance == null) {
         throw new IllegalStateException(myName + " Not Constructed Yet");
      }

      return ourInstance;
   }

}
