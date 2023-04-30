/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package riolog;

public class ProblemTracker {

   private static long errorCount;

   private static long warnCount;

   static {
      errorCount = 0;
      warnCount = 0;
   }

   // Prevent instantiating class
   private ProblemTracker() {
   }

   public static void addWarning() {
      warnCount++;
   }

   public static long getWarnCount() {
      return warnCount;
   }

   public static void addError() {
      errorCount++;
   }

   public static long getErrorCount() {
      return errorCount;
   }

}
