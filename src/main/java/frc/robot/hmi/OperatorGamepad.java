/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.hmi;

import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.armextender.ArmExtendToHighPosition;
import frc.robot.commands.armextender.ArmExtendToInPosition;
import frc.robot.commands.armextender.ArmExtendToLowPosition;
import frc.robot.commands.armextender.ArmExtendToMidPosition;
import frc.robot.commands.armrotator.ArmRotateToHighPosition;
import frc.robot.commands.armrotator.ArmRotateToLowPosition;
import frc.robot.commands.armrotator.ArmRotateToMidPosition;
import frc.robot.commands.armrotator.ArmRotateToOverPosition;

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

   private final Trigger rotateOverButton;
   private final Trigger rotateHighButton;
   private final Trigger rotateMidButton;
   private final Trigger rotateLowButton;

   public OperatorGamepad() {
      super("OperatorGamepad", 1);
      logger.info("constructing");

      rotateOverButton = cmdStick.button(rightBumper);
      rotateHighButton = cmdStick.button(yellowButton);
      rotateMidButton = cmdStick.button(redButton);
      rotateLowButton = cmdStick.button(greenButton);

      logger.info("constructed");
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
      rotateOverButton.onTrue(new ArmRotateToOverPosition()).onTrue(new ArmExtendToInPosition());
      rotateHighButton.onTrue(new ArmRotateToHighPosition()).onTrue(new ArmExtendToHighPosition());
      rotateMidButton.onTrue(new ArmRotateToMidPosition()).onTrue(new ArmExtendToMidPosition());
      rotateLowButton.onTrue(new ArmRotateToLowPosition()).onTrue(new ArmExtendToLowPosition());

      // TODO Nudge rotate when joysick is moved ...
      // Nudge rotation when joystick is moved
      // new ArmRotateToTarget(() -> deadBand(-getRightYAxis(), 0.10)).schedule();

      // TODO: Gripper

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
