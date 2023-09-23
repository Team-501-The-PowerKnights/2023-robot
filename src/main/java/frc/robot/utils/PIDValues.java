/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.utils;

import org.slf4j.Logger;

import riolog.PKLogger;

public class PIDValues {

   /** Our classes' logger **/
   @SuppressWarnings("unused")
   private static final Logger logger = PKLogger.getLogger(PIDValues.class.getName());

   /** PID settings */
   public static final String pid_Use = ".UsePID";
   public static final String pid_P = ".P";
   public static final String pid_I = ".I";
   public static final String pid_D = ".D";
   public static final String pid_IZone = ".IZone";
   public static final String pid_FF = ".FF";
   public static final String pid_minOutput = ".MinOutput";
   public static final String pid_maxOutput = ".MaxOutput";

   /** Smart Motion settings */
   public static final String pidsm_minVelocity = ".MinVelocity";
   public static final String pidsm_maxVelocity = ".MaxVelocity";
   public static final String pidsm_maxAccel = ".MaxAccel";
   public static final String pidsm_AllowedError = ".AllowedError";

   public boolean Use;
   public double P;
   public double I;
   public double D;
   public double IZone;
   public double FF;
   public double MinOutput;
   public double MaxOutput;

   public double MinVelocity;
   public double MaxVelocity;
   public double MaxAccel;
   public double AllowedError;

   public PIDValues() {
      this(false, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
   }

   public PIDValues(
   //@formatter:off
      boolean Use,
      double P,
      double I,
      double D,
      double IZone,
      double FF,
      double MinOutput,
      double MaxOutput)
    //@formatter:on
   {
      this(Use, P, I, D, IZone, FF, MinOutput, MaxOutput, 0, 0, 0, 0);
   }

   public PIDValues(
   //@formatter:off
      boolean Use,
      double P,
      double I,
      double D,
      double IZone,
      double FF,
      double MinOutput,
      double MaxOutput,
      double MinVelocity,
      double MaxVelocity,
      double MaxAccel,
      double AllowedError)
    //@formatter:on
   {
      this.Use = Use;
      this.P = P;
      this.I = I;
      this.D = D;
      this.IZone = IZone;
      this.FF = FF;
      this.MinOutput = MinOutput;
      this.MaxOutput = MaxOutput;
      this.MinVelocity = MinVelocity;
      this.MaxVelocity = MaxVelocity;
      this.MaxAccel = MaxAccel;
      this.AllowedError = AllowedError;
   }

   public boolean outputsSet() {
      return ((MinOutput != 0) && (MaxOutput != 0));
   }

   public boolean smartMotionSet() {
      return ((MinVelocity != 0) || (MaxVelocity != 0) || (MaxAccel != 0) || (AllowedError != 0));
   }

   @Override
   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("PID:");
      buf.append(" Use=").append(Use);
      buf.append(",P=").append(P);
      buf.append(",I=").append(I);
      buf.append(",D=").append(D);
      buf.append(",IZone=").append(IZone);
      buf.append(",FF=").append(FF);
      buf.append(",MinOutput=").append(MinOutput);
      buf.append(",MaxOutput=").append(MaxOutput);
      // if (smartMotionSet()) {
      buf.append(",MinVelocity=").append(MinVelocity);
      buf.append(",MaxVelocity=").append(MaxVelocity);
      buf.append(",MaxAccel=").append(MaxAccel);
      buf.append(",AllowedError=").append(AllowedError);
      // }
      return buf.toString();
   }

}
