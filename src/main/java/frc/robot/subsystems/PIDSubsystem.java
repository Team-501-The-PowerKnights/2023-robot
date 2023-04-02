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
 * DOCS: Insert docs here
 */
public abstract class PIDSubsystem extends BaseSubsystem implements IPIDSubsystem {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(BaseSubsystem.class.getName());

   /** Standard telemetry for PID */
   protected PIDTelemetry tlmPID = new PIDTelemetry();

   public PIDSubsystem(String name) {
      super(name);
      logger.info("constructing");

      m_atSetPoint = new AtSetPoint();
      m_achievedSetPoint = new AchievedSetPoint();

      logger.info("constructed");
   }

   protected void setTlmPIDEnabled(boolean enabled) {
      tlmPID.PIDEnabled = enabled;

      if (!enabled) {
         reset();
      }
   }

   protected boolean isTlmPIDEnabled() {
      return tlmPID.PIDEnabled;
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

   protected double getTlmPIDCurrent() {
      return tlmPID.PIDCurrent;
   }

   protected void updateTelemetry(String enabledName, String targetName, String currentName, String atTargetName) {
      SmartDashboard.putBoolean(enabledName, tlmPID.PIDEnabled);
      SmartDashboard.putNumber(targetName, tlmPID.PIDTarget);
      SmartDashboard.putNumber(currentName, tlmPID.PIDCurrent);
      SmartDashboard.putBoolean(atTargetName, tlmPID.PIDAtTarget);
   }

   private boolean m_haveSetpoint;
   private double m_setpoint;

   private boolean m_haveMeasurement;
   private double m_measurement;

   private class AtSetPoint {
      // The error that is considered at setpoint.
      public double m_positionTolerance = 0.05;

      // The error at the time of the most recent call to calculate()
      public double m_positionError;

      public void calcPositionError() {
         m_positionError = m_setpoint - m_measurement;
      }

      public boolean atSetPoint() {
         return Math.abs(m_atSetPoint.m_positionError) < m_atSetPoint.m_positionTolerance;
      }
   }

   private AtSetPoint m_atSetPoint;

   private class AchievedSetPoint {
      // The error that is considered at setpoint.
      public double m_positionTolerance;

      public void setPositionTolerance(double setPoint) {
         m_positionTolerance = Math.abs(setPoint * 0.10);

         m_positionAtCount = 0;
      }

      public long m_positionAtCount;

      public void calcPositionError() {
         if (m_atSetPoint.m_positionError < m_positionTolerance) {
            m_positionAtCount = (m_positionAtCount < 0) ? 0 : ++m_positionAtCount;
         } else {
            --m_positionAtCount;
         }
      }

      public boolean achievedSetPoint() {
         return m_positionAtCount > 5; // 0.10 seconds
      }
   }

   private AchievedSetPoint m_achievedSetPoint;

   public void setSetpoint(double setpoint) {
      m_setpoint = setpoint;
      m_haveSetpoint = true;

      m_achievedSetPoint.setPositionTolerance(setpoint);

      m_atSetPoint.calcPositionError();
      m_achievedSetPoint.calcPositionError();
   }

   public double getSetpoint() {
      return m_setpoint;
   }

   public void setTolerance(double tolerance) {
      m_atSetPoint.m_positionTolerance = tolerance;
   }

   public double getTolerance() {
      return m_atSetPoint.m_positionTolerance;
   }

   public void newMeasurement(double measurement) {
      m_measurement = measurement;
      m_haveMeasurement = true;

      m_atSetPoint.calcPositionError();
      m_achievedSetPoint.calcPositionError();
   }

   public boolean atSetpoint() {
      return m_haveMeasurement
            && m_haveSetpoint
            && m_atSetPoint.atSetPoint();
   }

   public boolean achievedSetPoint() {
      return m_haveMeasurement
            && m_haveSetpoint
            && m_achievedSetPoint.achievedSetPoint();
   }

   public void reset() {
      m_haveSetpoint = false;
      m_haveMeasurement = false;

      m_atSetPoint.m_positionError = 0;

      m_achievedSetPoint.m_positionAtCount = 0;
   }

   public void logPID() {
      logger.debug("{}: PID enabled={}, target={} current={} atTarget={}",
            myName, tlmPID.PIDEnabled, tlmPID.PIDTarget, tlmPID.PIDCurrent, tlmPID.PIDAtTarget);
   }

}
