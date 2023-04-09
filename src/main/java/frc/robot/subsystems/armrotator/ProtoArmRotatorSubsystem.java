/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems.armrotator;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.telemetry.TelemetryNames;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * DOCS: Add your docs here.
 */
public class ProtoArmRotatorSubsystem extends BaseArmRotatorSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(ProtoArmRotatorSubsystem.class.getName());

   /** */
   private final CANSparkMax motor;
   private SparkMaxPIDController pid;
   private RelativeEncoder encoder;

   private AbsoluteEncoder absEncoder;
   private final double absEncoderBaseline = 0.5912;
   private final double absEncoderScale = -1 * (5.0 * 5.0 * 5.0 * (72.0 / 36.0));

   ProtoArmRotatorSubsystem() {
      logger.info("constructing");

      motor = new CANSparkMax(21, MotorType.kBrushless);
      checkError(motor.restoreFactoryDefaults(), "restore factory defaults {}");
      checkError(motor.setIdleMode(IdleMode.kBrake), "set idle mode to brake {}");
      pid = motor.getPIDController();
      encoder = motor.getEncoder();
      checkError(motor.setClosedLoopRampRate(0), "set closed loop ramp rate to 0 {}");
      // checkError(motor.setSmartCurrentLimit(20), "set current limit to 20 {}");

      absEncoder = motor.getAbsoluteEncoder(Type.kDutyCycle);

      double encoderZero = syncEncoders();

      // Set the PID so when it wakes up it doesn't try to move
      rotateToTarget(encoderZero); // Can't use get right after set?

      // Set the starting state (front or back of the vertical plane)
      if (encoderZero >= 0) {
         onFrontSide = true;
      } else {
         onFrontSide = false;
      }
      logger.info("onFrontSide init: encoderZero = {}, onFrontSide = {}", encoderZero, onFrontSide);

      logger.info("constructed");
   }

   // TODO: Use to set a degraded error status/state on subsystem
   @SuppressWarnings("unused")
   private REVLibError lastError;

   private void checkError(REVLibError error, String message) {
      if (error != REVLibError.kOk) {
         lastError = error;
         logger.error(message, error);
      }
   }

   private boolean onFrontSide;

   // @Override
   public void periodic() {
      double current = getTlmPIDCurrent();
      if (isTlmPIDEnabled()) {
         // Only works because '0' is straight up
         if ((onFrontSide == true) && (current < 0.5)) {
            logger.debug("************************** FRONT TO BACK ***********************************************");
            logger.debug("**********  transitioning front to back: current={} onFrontSide={}",
                  current, onFrontSide);
            logger.debug("************************** FRONT TO BACK ***********************************************");
            onFrontSide = false;

            // On the back side of robot; more power in positive side
            // (so reverse min & max, but keep signs)
            logger.debug("properties of pidValues: minOuput = {}, maxOutput = {}",
                  pidValues.MinOutput, pidValues.MaxOutput);
            double minOutput = Math.abs(pidValues.MaxOutput);
            minOutput *= (pidValues.MinOutput < 0) ? -1 : 1;
            double maxOutput = Math.abs(pidValues.MinOutput);
            maxOutput *= (pidValues.MaxOutput < 0) ? -1 : 1;
            logger.debug("calculated min & max ouput: minOuput = {}, maxOutput = {}",
                  minOutput, maxOutput);
            // checkError(pid.setOutputRange(minOutput, maxOutput), "set PID_ min and max
            // output {}");
            logger.debug("switched min & max ouput from pid: minOuput = {}, maxOutput = {}",
                  pid.getOutputMin(), pid.getOutputMax());

         } else if ((onFrontSide == false) && (current > 0.5)) {
            logger.debug("************************** BACK TO FRONT ***********************************************");
            logger.debug("************  transitioning back to front: current={} onFrontSide={}",
                  current, onFrontSide);
            logger.debug("************************** BACK TO FRONT ***********************************************");
            onFrontSide = true;

            // On the front side of robot; more power in negative side (default prefs)
            logger.debug("properties of pidValues: minOuput = {}, maxOutput = {}",
                  pidValues.MinOutput, pidValues.MaxOutput);
            double minOutput = pidValues.MinOutput;
            double maxOutput = pidValues.MaxOutput;
            logger.debug("calculated min & max ouput: minOuput = {}, maxOutput = {}",
                  minOutput, maxOutput);
            // checkError(pid.setOutputRange(minOutput, maxOutput), "set PID_ min and max
            // output {}");
            logger.debug("reverted min & max ouput from pid: minOuput = {}, maxOutput = {}",
                  pid.getOutputMin(), pid.getOutputMax());
         }
      } else {
         onFrontSide = (current >= 0) ? true : false;
      }
   }

   @Override
   public void autonomousInit() {
      super.autonomousInit();

      checkError(motor.setIdleMode(IdleMode.kBrake), "set idle mode to brake {}");
   };

   @Override
   public void teleopInit() {
      super.teleopInit();

      checkError(motor.setIdleMode(IdleMode.kBrake), "set idle mode to brake {}");
   };

   @Override
   public void updateTelemetry() {
      setTlmPIDCurrent(encoder.getPosition());

      double current = absEncoder.getPosition();
      SmartDashboard.putNumber(TelemetryNames.ArmRotator.absCurrent, current);
      SmartDashboard.putNumber(TelemetryNames.ArmRotator.absCorrected,
            ((absEncoder.getPosition() - absEncoderBaseline) * absEncoderScale));

      current = motor.getOutputCurrent(); // bad I know :)
      SmartDashboard.putNumber(TelemetryNames.ArmRotator.current, current);

      super.updateTelemetry();
   }

   @Override
   public void updatePreferences() {
      super.updatePreferences();

      checkError(pid.setP(pidValues.P), "set PID_P {}");
      checkError(pid.setI(pidValues.I), "set PID_I {}");
      checkError(pid.setD(pidValues.D), "set PID_D {}");
      checkError(pid.setIZone(pidValues.IZone), "set PID_IZone {}");
      checkError(pid.setFF(pidValues.FF), "set PID_FF {}");
      checkError(pid.setOutputRange(pidValues.MinOutput, pidValues.MaxOutput), "set PID_OutputRange {}");

      checkError(motor.setClosedLoopRampRate(rampRate), "set closed loop ramp rate {}");

      ArmRotationPosition.overPosition.set(overSetPoint);
      ArmRotationPosition.highPosition.set(highSetPoint);
      ArmRotationPosition.midPosition.set(midSetPoint);
      ArmRotationPosition.lowPosition.set(lowSetPoint);
   }

   @Override
   public void disable() {
      // FIXME: Change to non-deprecated method
      checkError(pid.setReference(0, ControlType.kDutyCycle), "PID set reference to kDutyCycle,0 {}");
      setTlmPIDEnabled(false);
   }

   @Override
   public void stop() {
      // FIXME: Change to non-deprecated method
      checkError(pid.setReference(0, ControlType.kDutyCycle), "PID set reference to kDutyCycle,0 {}");
      setTlmPIDEnabled(false);
   }

   @Override
   public void rotateToPosition(ArmRotationPosition position) {
      logger.trace("position = {}", position);

      double target = position.get();
      rotateToTarget(target);
   }

   @Override
   public void rotateToTarget(double target) {
      logger.trace("set PID target = {}", target);

      // FIXME: Change to non-deprecated method
      checkError(pid.setReference(target, ControlType.kPosition), "PID set reference to kPosition {}");
      setTlmPIDEnabled(true);
      setTlmPIDTarget(target);
   }

   @Override
   public void offsetTarget(double offset) {
      logger.trace("offset PID target = {}", offset);

      double target = getTlmPIDTarget();
      // FIXME: This should be adding and do the sign on input
      target -= offset;
      rotateToTarget(target);
   }

   @Override
   public void rotate(double speed) {
      // TODO Auto-generated method stub

   }

   @Override
   public double syncEncoders() {
      logger.debug("sync encoders");

      double absEncoderCurrent = absEncoder.getPosition();
      double encoderOffset = absEncoderCurrent - absEncoderBaseline;
      logger.info("encoder sync: scale = {}, baseline={}, current={}, offset={}",
            absEncoderScale, absEncoderBaseline, absEncoderCurrent, encoderOffset);
      double encoderZero = encoderOffset * absEncoderScale;
      checkError(encoder.setPosition(encoderZero), "set encoder position based on absolute {}");

      return encoderZero;
   }

}
