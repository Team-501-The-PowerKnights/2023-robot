/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.telemetry.PIDTelemetry;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * DOCS
 */
public abstract class PIDSubsystem extends BaseSubsystem implements IPIDSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(BaseSubsystem.class.getName());

   /** Standard telemetry for PID */
   protected PIDTelemetry tlmPID = new PIDTelemetry();

   // The error at the time of the most recent call to calculate()
   private double m_positionError;

   // The error that is considered at setpoint.
   private double m_positionTolerance = 0.05;

   private double m_setpoint;
   private double m_measurement;

   private boolean m_haveMeasurement;
   private boolean m_haveSetpoint;

   public PIDSubsystem(String name) {
      super(name);
      logger.info("constructing");

      logger.info("constructed");
   }

   protected void setTlmPIDEnabled(boolean enabled) {
      tlmPID.PIDEnabled = enabled;
   }

   protected void setTlmPIDTarget(double target) {
      tlmPID.PIDTarget = target;

      setSetpoint(target);
   }

   protected double getTlmPIDTarget() {
      return tlmPID.PIDTarget;
   }

   protected void setTlmPIDCurrent(double current) {
      tlmPID.PIDCurrent = current;

      newMeasurement(current);
      tlmPID.PIDAtTarget = atSetpoint();
   }

   protected void updateTelemetry(String enabledName, String targetName, String currentName, String atTargetName) {
      SmartDashboard.putBoolean(enabledName, tlmPID.PIDEnabled);
      SmartDashboard.putNumber(targetName, tlmPID.PIDTarget);
      SmartDashboard.putNumber(currentName, tlmPID.PIDCurrent);
      SmartDashboard.putBoolean(atTargetName, tlmPID.PIDAtTarget);
   }

   @Override
   public void logTelemetry() {
      logger.debug("{}: PID target={} current={} atTarget={}",
            myName, tlmPID.PIDTarget, tlmPID.PIDCurrent, tlmPID.PIDAtTarget);
   }

   /**
    * Sets the setpoint for the PIDController.
    *
    * @param setpoint
    *           The desired setpoint.
    */
   public void setSetpoint(double setpoint) {
      m_setpoint = setpoint;
      m_haveSetpoint = true;

      m_positionError = m_setpoint - m_measurement;
   }

   /**
    * Returns the current setpoint of the PIDController.
    *
    * @return The current setpoint.
    */
   public double getSetpoint() {
      return m_setpoint;
   }

   /**
    * Sets the position error which is considered tolerable for use with
    * atSetpoint().
    *
    * @param tolerance
    *           - Position error which is tolerable.
    */
   public void setTolerance(double tolerance) {
      m_positionTolerance = tolerance;
   }

   /**
    * Returns the position tolerance of this controller.
    *
    * @return the position tolerance of the controller.
    */
   public double getTolerance() {
      return m_positionTolerance;
   }

   public void newMeasurement(double measurement) {
      m_measurement = measurement;
      m_haveMeasurement = true;

      m_positionError = m_setpoint - m_measurement;
   }

   /**
    * Returns true if the error is within the tolerance of the setpoint.
    *
    * This will return false until at least one input value has been computed.
    * 
    * @return Whether the error is within the acceptable bounds.
    */
   public boolean atSetpoint() {
      return m_haveMeasurement
            && m_haveSetpoint
            && Math.abs(m_positionError) < m_positionTolerance;
   }

   /**
    * 
    */
   public void reset() {
      m_positionError = 0;
      m_haveMeasurement = false;
      m_haveSetpoint = false;
   }

}
