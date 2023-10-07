/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.hmi;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.arm.ArmNudgeTarget;
import frc.robot.commands.arm.ArmWaitAtSetPoint;
import frc.robot.commands.arm.ArmGoToHighPosition;
import frc.robot.commands.arm.ArmGoToLowPosition;
import frc.robot.commands.arm.ArmGoToMidPosition;
import frc.robot.commands.arm.ArmGoToTarget;
import frc.robot.commands.gripper.GripperGrip;
import frc.robot.commands.lift.LiftNudgeTarget;
import frc.robot.commands.lift.LiftWaitAtSetPoint;
import frc.robot.commands.lift.LiftGoToHighPosition;
import frc.robot.commands.lift.LiftGoToLowPosition;
import frc.robot.commands.lift.LiftGoToMidPosition;
import frc.robot.commands.lift.LiftGoToTarget;
import frc.robot.commands.turret.TurretGoToTarget;
import frc.robot.commands.turret.TurretNudgeTarget;
import frc.robot.commands.turret.TurretWaitAtSetPoint;
import riolog.PKLogger;

/**
 * This class implements the Operator's gamepad forthe Real-Bot.
 * <p>
 * See <code>control_mode.md</code> for documentation of how configured and
 * used.
 */
public class RealOperatorGamepad extends BaseOperatorGamepad {

   /** Our classes' logger **/
   private static final Logger logger = PKLogger.getLogger(RealOperatorGamepad.class.getName());

   private final Trigger stowPoseButton;
   private final Trigger topRowPoseButton;
   private final Trigger middleRowPoseButton;
   private final Trigger floorPickupPoseButton;

   private final Trigger turretNudgeJoystick;
   private final Trigger liftNudgeJoystick;
   private final Trigger armNudgeJoystick;

   private final Trigger gripperTrigger;

   public RealOperatorGamepad() {
      super("RealOperatorGamepad", 1);
      logger.info("constructing");

      stowPoseButton = cmdStick.button(blueButton);
      topRowPoseButton = cmdStick.button(yellowButton);
      middleRowPoseButton = cmdStick.button(redButton);
      floorPickupPoseButton = cmdStick.button(greenButton);

      turretNudgeJoystick = new Trigger(this::isTurretNudged);
      liftNudgeJoystick = new Trigger(this::isLiftNudged);
      armNudgeJoystick = new Trigger(this::isArmNudged);

      gripperTrigger = new Trigger(this::isGripperActive);

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

   /**
    * Determine if the Left and/or Right Triggers are moved from the
    * <i>dead band<i>.
    *
    * @return whether either (or both) of the triggers are active
    */
   private boolean isGripperActive() {
      return (deadBand(getLeftTrigger(), 0.01) > 0)
            || (deadBand(getRightTrigger(), 0.01) > 0);
      // return true;
   }

   private double getGripperSpeed() {
      double inSpeed = getLeftTrigger();
      double outSpeed = getRightTrigger();

      double speed;
      if (inSpeed > outSpeed) {
         speed = inSpeed;
      } else {
         speed = -outSpeed;
      }
      return speed;
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

      stowPoseButton
            .onTrue(new SequentialCommandGroup(
            // @formatter:off
               new SequentialCommandGroup(new LiftGoToTarget(43), new LiftWaitAtSetPoint()),
               new SequentialCommandGroup(new ArmGoToTarget(0), new ArmWaitAtSetPoint()),
               new SequentialCommandGroup(new TurretGoToTarget(0), new TurretWaitAtSetPoint())
            // @formatter:on
            ));
      topRowPoseButton
            .onTrue(new SequentialCommandGroup(
            // @formatter:off
               new SequentialCommandGroup(new LiftGoToHighPosition(), new LiftWaitAtSetPoint()),
               new SequentialCommandGroup(new ArmGoToHighPosition(), new ArmWaitAtSetPoint())
             // @formatter:on
            ));
      middleRowPoseButton
            .onTrue(new SequentialCommandGroup(
            // @formatter:off
               new SequentialCommandGroup(new LiftGoToMidPosition(), new LiftWaitAtSetPoint()),
               new SequentialCommandGroup(new ArmGoToMidPosition(), new ArmWaitAtSetPoint())
             // @formatter:on
            ));
      floorPickupPoseButton
            .onTrue(new SequentialCommandGroup(
            // @formatter:off
               new SequentialCommandGroup(new ArmGoToLowPosition(), new ArmWaitAtSetPoint()),
               new SequentialCommandGroup(new LiftGoToLowPosition(), new LiftWaitAtSetPoint())

             // @formatter:on
            ));

      // Nudge turret when joystick is moved
      turretNudgeJoystick
            .whileTrue(new TurretNudgeTarget(() -> getTurretInput()));
      // Nudge lift when joystick is moved
      liftNudgeJoystick
            .whileTrue(new LiftNudgeTarget(() -> getLiftInput()));
      // Nudge arm when joystick is moved
      armNudgeJoystick
            .whileTrue(new ArmNudgeTarget(() -> getArmInput()));

      // Move the gripper motors when triggers are active
      gripperTrigger
            .whileTrue(new GripperGrip(() -> getGripperSpeed()));

      logger.info("configured");
   }

}
