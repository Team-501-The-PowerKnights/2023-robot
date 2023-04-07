/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.hmi;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.armextender.ArmExtendToHighPosition;
import frc.robot.commands.armextender.ArmExtendToInPosition;
import frc.robot.commands.armextender.ArmExtendToLowPosition;
import frc.robot.commands.armextender.ArmExtendToMidPosition;
import frc.robot.commands.armextender.ArmExtendToOverPosition;
import frc.robot.commands.armextender.ArmExtendWaitAtSetPoint;
import frc.robot.commands.armextender.ArmNudgeExtensionTarget;
import frc.robot.commands.armrotator.ArmNudgeRotationTarget;
import frc.robot.commands.armrotator.ArmOffsetRotationTarget;
import frc.robot.commands.armrotator.ArmRotateToHighPosition;
import frc.robot.commands.armrotator.ArmRotateToLowPosition;
import frc.robot.commands.armrotator.ArmRotateToMidPosition;
import frc.robot.commands.armrotator.ArmRotateToOverPosition;
import frc.robot.commands.armrotator.ArmRotateWaitAtSetPoint;
import frc.robot.commands.gripper.GripperEject;
import frc.robot.commands.gripper.GripperGrip;
import frc.robot.commands.gripper.GripperStop;
import frc.robot.commands.wrist.WristRotateToOverPosition;
import frc.robot.commands.wrist.WristRotateToUpPosition;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * This class implements the Operator's gamepad.
 * <p>
 * See <code>control_mode.md</code> for documentation of how configured and
 * used.
 */
public class OperatorGamepad extends F310Gamepad {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(OperatorGamepad.class.getName());

   private final Trigger armOverPoseButton;
   private final Trigger armHighPoseButton;
   private final Trigger armMidPoseButton;
   private final Trigger armLowPoseButton;

   private final Trigger armRetractButton;

   private final Trigger armRotateNudgeJoystick;
   private final Trigger armExtendNudgeJoystick;

   private final Trigger gripperTrigger;

   public OperatorGamepad() {
      super("OperatorGamepad", 1);
      logger.info("constructing");

      armOverPoseButton = cmdStick.button(rightBumper);
      armHighPoseButton = cmdStick.button(yellowButton);
      armMidPoseButton = cmdStick.button(redButton);
      armLowPoseButton = cmdStick.button(greenButton);

      armRetractButton = cmdStick.button(blueButton);

      armRotateNudgeJoystick = new Trigger(this::isArmRotationNudged);
      armExtendNudgeJoystick = new Trigger(this::isArmExtensionNudged);

      gripperTrigger = new Trigger(this::isGripperActive);

      logger.info("constructed");
   }

   /**
    * Determine if Right Y-Axis joystick is moved from the <i>dead band</i>.
    *
    * @return whether joystick is offset from deadband (i.e., active)
    */
   private boolean isArmRotationNudged() {
      return (Math.abs(deadBand(-getRightYAxis(), 0.10)) > 0);
   }

   /**
    * Determine if Left Y-Axis joystick is moved from the <i>dead band</i>.
    *
    * @return whether joystick is offset from deadband (i.e., active)
    */
   private boolean isArmExtensionNudged() {
      return (Math.abs(deadBand(-getLeftYAxis(), 0.10)) > 0);
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

      // Pose the arm when button is pressed
      armOverPoseButton
            .onTrue(new SequentialCommandGroup(
                  new SequentialCommandGroup(new ArmExtendToInPosition(), new ArmExtendWaitAtSetPoint()),
                  new SequentialCommandGroup(new ArmRotateToHighPosition(), new ArmRotateWaitAtSetPoint()),
                  new WristRotateToOverPosition(),
                  new SequentialCommandGroup(new ArmRotateToOverPosition(), new ArmRotateWaitAtSetPoint()),
                  new SequentialCommandGroup(new ArmExtendToOverPosition(), new ArmExtendWaitAtSetPoint()),
                  new SequentialCommandGroup(new ArmOffsetRotationTarget(3), new ArmRotateWaitAtSetPoint()),
                  new SequentialCommandGroup(new GripperEject(), new WaitCommand(0.3)),
                  new SequentialCommandGroup(new ArmExtendToMidPosition(), new ArmExtendWaitAtSetPoint()),
                  new GripperStop(),
                  new ArmRotateToHighPosition(),
                  new WristRotateToUpPosition(),
                  new SequentialCommandGroup(new ArmExtendToInPosition(), new ArmExtendWaitAtSetPoint())));

      armHighPoseButton
            .onTrue(new ArmRotateToHighPosition())
            .onTrue(new ArmExtendToHighPosition())
            .onTrue(new WristRotateToUpPosition());

      // This does both low cone as well as mid- and high-cube
      armMidPoseButton
            .onTrue(new SequentialCommandGroup(
                  new SequentialCommandGroup(new ArmRotateToMidPosition(), new ArmRotateWaitAtSetPoint()),
                  new SequentialCommandGroup(new ArmExtendToMidPosition(), new ArmExtendWaitAtSetPoint()),
                  new SequentialCommandGroup(new ArmOffsetRotationTarget(-4), new ArmRotateWaitAtSetPoint()),
                  new SequentialCommandGroup(new GripperEject(), new WaitCommand(0.3)),
                  new SequentialCommandGroup(new ArmExtendToInPosition(), new ArmExtendWaitAtSetPoint()),
                  new GripperStop()));

      armLowPoseButton
            .onTrue(new ArmRotateToLowPosition())
            .onTrue(new ArmExtendToLowPosition())
            .onTrue(new WristRotateToUpPosition());

      // Retract arm completely when button is pressed
      armRetractButton
            .onTrue(new ArmExtendToInPosition());

      // Nudge rotation when joystick is moved
      armRotateNudgeJoystick
            .whileTrue(new ArmNudgeRotationTarget(() -> deadBand(-getRightYAxis(), 0.10)));
      // Nudge extenion when the joysick is moved
      armExtendNudgeJoystick
            .whileTrue(new ArmNudgeExtensionTarget(() -> deadBand(-getLeftYAxis(), 0.10)));

      // Move the gripper motors when triggers are active
      gripperTrigger
            .whileTrue(new GripperGrip(() -> getGripperSpeed()));

      logger.info("configured");
   }

   @Override
   public void testInit() {
      logger.info("initializing test for {}", myName);

      configureTestButtonBindings();

      logger.info("initialized test for {}", myName);
   }

   private void configureTestButtonBindings() {
      logger.info("configure");

      // put any buttons or other triggers for testing

      logger.info("configured");
   }

}
