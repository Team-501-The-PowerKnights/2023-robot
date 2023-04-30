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
import frc.robot.hmi.OperatorGamepad;
import frc.robot.telemetry.ITelemetryProvider;
import frc.robot.telemetry.TelemetryNames;
import frc.robot.utils.PKStatus;

import riolog.PKLogger;

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
   private final DriverGamepad driverPad;
   // Operator gamepad
   private final OperatorGamepad operatorPad;

   private OI() {
      logger.info("constructing {}", myName);

      driverPad = new DriverGamepad();
      operatorPad = new OperatorGamepad();

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

   /*****************
    * Drive
    *****************/

   public double getDriveSpeed() {
      return driverPad.getDriveSpeed();
   }

   public double getDriveTurn() {
      return driverPad.getDriveTurn();
   }

}
