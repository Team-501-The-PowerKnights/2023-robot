/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.hmi.DriverGamepad;
import frc.robot.hmi.IDriverGamepad;
import frc.robot.hmi.IOperatorGamepad;
import frc.robot.hmi.ProtoOperatorGamepad;
import frc.robot.hmi.RealOperatorGamepad;
import frc.robot.hmi.StubOperatorGamepad;
import frc.robot.hmi.SuitcaseOperatorGamepad;
import frc.robot.hmi.SwprogOperatorGamepad;
import frc.robot.properties.PropertiesManager;
import frc.robot.telemetry.ITelemetryProvider;
import frc.robot.telemetry.TelemetryNames;
import frc.robot.utils.PKStatus;

import riolog.PKLogger;
import riolog.ProblemTracker;

/**
 * Add your docs here.
 */
public class OI implements ITelemetryProvider, IModeFollower {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(OI.class.getName());

   /** Singleton instance of class for all to use **/
   private static OI ourInstance;
   /** Name of our subsystem **/
   private final static String myName = TelemetryNames.OI.name;

   public static synchronized void constructInstance() {
      SmartDashboard.putNumber(TelemetryNames.OI.status, PKStatus.unknown.tlmValue);

      if (ourInstance != null) {
         throw new IllegalStateException(myName + " already constructed");
      }

      SmartDashboard.putNumber(TelemetryNames.OI.status, PKStatus.inProgress.tlmValue);

      ourInstance = new OI();

      SmartDashboard.putNumber(TelemetryNames.OI.status, PKStatus.success.tlmValue);
   }

   public static OI getInstance() {
      if (ourInstance == null) {
         throw new IllegalStateException(myName + " not constructed yet");
      }
      return ourInstance;
   }

   // Driver gamepad
   private final IDriverGamepad driverPad;

   public IDriverGamepad getDriverPad() {
      return driverPad;
   }

   // Operator gamepad
   private final IOperatorGamepad operatorPad;

   public IOperatorGamepad getOperatorPad() {
      return operatorPad;
   }

   private OI() {
      logger.info("constructing {}", myName);

      String config = PropertiesManager.getInstance().getImpl();
      switch (config) {
         case "Suitcase":
            driverPad = new DriverGamepad();
            operatorPad = new SuitcaseOperatorGamepad();
            break;
         case "Swprog":
            driverPad = new DriverGamepad();
            operatorPad = new SwprogOperatorGamepad();
            break;
         case "Proto":
            driverPad = new DriverGamepad();
            operatorPad = new ProtoOperatorGamepad();
            break;
         case "Real":
            driverPad = new DriverGamepad();
            operatorPad = new RealOperatorGamepad();
            break;
         default:
            driverPad = new DriverGamepad();
            operatorPad = new StubOperatorGamepad();
            logger.error("no config specified; instantiating default stub for: {}",
                  operatorPad.getClass().getSimpleName());
            ProblemTracker.addError();
            break;
      }

      logger.info("constructed");
   }

   @Override
   public void updateTelemetry() {
      driverPad.updateTelemetry();
      operatorPad.updateTelemetry();
   }

   @Override
   public void disabledInit() {
      logger.info("initializing disabled for {}", myName);

      // Disable the previous button mappings
      logger.trace("***** clearButtons()");
      CommandScheduler.getInstance().getActiveButtonLoop().clear();

      logger.info("initialized disabled for {}", myName);
   }

   @Override
   public void autonomousInit() {
      logger.info("initializing auto for {}", myName);

      // Make the button bindings for this mode
      driverPad.autonomousInit();
      operatorPad.autonomousInit();

      logger.info("initialized auto for {}", myName);
   }

   @Override
   public void teleopInit() {
      logger.info("initializing teleop for {}", myName);

      // Make the button bindings for this mode
      driverPad.teleopInit();
      operatorPad.teleopInit();

      logger.info("initialized teleop for {}", myName);
   }

   @Override
   public void testInit() {
      logger.info("initializing test for {}", myName);

      // Make the button bindings for this mode
      driverPad.testInit();
      operatorPad.testInit();

      logger.info("initialized test for {}", myName);
   }

}
