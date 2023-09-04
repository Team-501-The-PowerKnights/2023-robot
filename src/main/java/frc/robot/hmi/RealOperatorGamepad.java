/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.hmi;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.arm.ArmJoystickControl;
import frc.robot.commands.lift.LiftJoystickControl;
import frc.robot.commands.lift.LiftNudgeTarget;
import frc.robot.commands.turret.TurretJoystickControl;
import frc.robot.commands.turret.TurretNudgeTarget;
import riolog.PKLogger;

public class RealOperatorGamepad extends BaseOperatorGamepad {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(RealOperatorGamepad.class.getName());

   private final Trigger turretNudgeJoystick;

   private final Trigger liftNudgeJoystick;

   private final Trigger armNudgeJoystick;

   public RealOperatorGamepad() {
      super("RealOperatorGamepad", 1);
      logger.info("constructing");

      turretNudgeJoystick = new Trigger(this::isTurretNudged);
      liftNudgeJoystick = new Trigger(this::isLiftNudged);
      armNudgeJoystick = new Trigger(this::isArmNudged);

      logger.info("constructed");
   }

   private double getTurretInput() {
      return getLeftXAxis();
   }

   /**
    * Determine if Left X-Axis joystick is moved from the <i>dead band</i>.
    *
    * @return whether joystick is offset from deadband (i.e., active)
    */
   private boolean isTurretNudged() {
      return (Math.abs(deadBand(getTurretInput(), 0.10)) > 0);
   }

   private double getLiftInput() {
      return getLeftYAxis();
   }

   /**
    * Determine if Left Y-Axis joystick is moved from the <i>dead band</i>.
    *
    * @return whether joystick is offset from deadband (i.e., active)
    */
   private boolean isLiftNudged() {
      return (Math.abs(deadBand(getLiftInput(), 0.10)) > 0);
   }

   private double getArmInput() {
      return getRightYAxis();
   }

   /**
    * Determine if Right Y-Axis joystick is moved from the <i>dead band</i>.
    *
    * @return whether joystick is offset from deadband (i.e., active)
    */
   private boolean isArmNudged() {
      return (Math.abs(deadBand(getArmInput(), 0.10)) > 0);
   }

   @Override
   public void updateTelemetry() {
   }

   @Override
   public void autonomousInit() {
      logger.info("initializing auto for {}", this.getClass().getSimpleName());

      // no button or other trigger for autonomous

      logger.info("initialized auto for {}", myName);
   }

   @Override
   public void teleopInit() {
      logger.info("initializing teleop for {}", myName);

      configureTeleopButtonBindings();

      logger.info("initialized teleop for {}", myName);
   }

   private void configureTeleopButtonBindings() {
      logger.info("configure");

      // Nudge turret when joystick is moved
      turretNudgeJoystick
            .whileTrue(new TurretNudgeTarget(() -> getTurretInput()));

      // Nudge lift when joystick is moved
      liftNudgeJoystick
            .whileTrue(new LiftNudgeTarget(() -> getLiftInput()));

      // Nudge arm when joystick is moved
      armNudgeJoystick
            .whileTrue(new ArmJoystickControl(() -> getArmInput()));

      logger.info("configured");
   }

}
