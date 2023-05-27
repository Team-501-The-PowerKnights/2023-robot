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

   public static final String pid_P = ".P";
   public static final String pid_I = ".I";
   public static final String pid_D = ".D";
   public static final String pid_IZone = ".IZone";
   public static final String pid_FF = ".FF";
   public static final String pid_minOutput = ".MinOutput";
   public static final String pid_maxOutput = ".MaxOutput";
   public static final String pid_maxVel = ".MaxVel";
   public static final String pid_minVel = ".MinVel";
   public static final String pid_maxAcc = ".MaxAcc";
   public static final String pid_maxErr = ".MaxErr";

   public double P;
   public double I;
   public double D;
   public double IZone;
   public double FF;
   public double MinOutput;
   public double MaxOutput;
   public double MaxVel;
   public double MinVel;
   public double MaxAcc;
   public double MaxErr;

   public PIDValues() {
      this(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
   }

   public PIDValues(
   //@formatter:off
      double P,
      double I,
      double D,
      double IZone,
      double FF,
      double MinOutput,
      double MaxOutput,
      double MaxVel,
      double MinVel,
      double MaxAcc,
      double MaxErr
      )
    //@formatter:on
   {
      this.P = P;
      this.I = I;
      this.D = D;
      this.IZone = IZone;
      this.FF = FF;
      this.MinOutput = MinOutput;
      this.MaxOutput = MaxOutput;
      this.MaxVel = MaxVel;
      this.MinVel = MinVel;
      this.MaxAcc = MaxAcc;
      this.MaxErr = MaxErr;
   }

   @Override
   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("PID:");
      buf.append(" P=").append(P);
      buf.append(",I=").append(I);
      buf.append(",D=").append(D);
      buf.append(",IZone=").append(IZone);
      buf.append(",FF=").append(FF);
      buf.append(",MinOutput=").append(MinOutput);
      buf.append(",MaxOutput=").append(MaxOutput);
      buf.append(",MaxVel=").append(MaxVel);
      buf.append(",MinVel=").append(MinVel);
      buf.append(",MaxAcc=").append(MaxAcc);
      buf.append(",MaxErr=").append(MaxErr);
      return buf.toString();
   }

}
