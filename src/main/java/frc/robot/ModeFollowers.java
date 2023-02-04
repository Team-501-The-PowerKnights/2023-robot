/*-----------------------------------------------------------------------*/
/* Copyright (c) Team 501 - The PowerKnights. All Rights Reserved. */
/* Open Source Software - may be modified and shared by other FRC teams */
/* under the terms of the Team501 license. The code must be accompanied */
/* by the Team 501 - The PowerKnights license file in the root directory */
/* of this project. */
/*-----------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import riolog.PKLogger;
import riolog.RioLogger;

/**
 * A class that implements a system of track the mode transistions of
 * the robot as defined by the <code>Iterative</code> flavor we use,
 * so that <code>IModeFollowers<code> can be notified each the mode
 * changes. xxxx
 * {@link edu.wpi.first.wpilibj.IterativeRobotBase robot}.
 */
public class ModeFollowers {

   /** Our classes' logger **/
   private static final PKLogger logger = RioLogger.getLogger(ModeFollowers.class.getName());

   /** List of mode followers that have registered */
   private List<IModeFollower> followers;

   private ModeFollowers() {
      // Add all the mode followers (need to be in order of creation)
      followers = new ArrayList<>();
   }

   public static ModeFollowers getInstance() {
      return Holder.INSTANCE;
   }

   private static class Holder {
      private static final ModeFollowers INSTANCE = new ModeFollowers();
   }

   /**
    * Add a single new follower to the tracked mode followers.
    * 
    * @param follower
    *           - follower to be added
    */
   public void add(IModeFollower follower) {
      followers.add(follower);
   }

   /**
    * Add a list of new followers to the tracked mode followers.
    * 
    * @param followers
    *           - followers to be added
    */
   public void addAll(Collection<? extends IModeFollower> followers) {
      this.followers.addAll(followers);
   }

   /**
    * Notify of change (initializing disabled mode).
    */
   public void initDisabled() {
      logger.info("initializing disabled");
      for (IModeFollower f : followers) {
         f.disabledInit();
      }
   };

   /**
    * Notify of change (exiting disabled mode).
    */
   public void exitDisabled() {
      logger.info("exiting disabled");
      for (IModeFollower f : followers) {
         f.disabledExit();
      }
   };

   /**
    * Notify of change (initializing autonomous mode).
    */
   public void initAutonomous() {
      logger.info("initializing autonomous");
      for (IModeFollower f : followers) {
         f.autonomousInit();
      }
   };

   /**
    * Notify of change (exiting autonomous mode).
    */
   public void exitAutonomous() {
      logger.info("exiting autonomous");
      for (IModeFollower f : followers) {
         f.autonomousExit();
      }
   };

   /**
    * Notify of change (initializing teleop mode).
    */
   public void initTeleop() {
      logger.info("initializing teleop");
      for (IModeFollower f : followers) {
         f.teleopInit();
      }
   };

   /**
    * Notify of change (exiting teleop mode).
    */
   public void exitTeleop() {
      logger.info("exiting teleop");
      for (IModeFollower f : followers) {
         f.teleopExit();
      }
   };

   /**
    * Notify of change (initializing test mode).
    */
   public void initTest() {
      logger.info("initializing test");
      for (IModeFollower f : followers) {
         f.testInit();
      }
   };

   /**
    * Notify of change (exiting test mode).
    */
   public void exitTest() {
      logger.info("exiting test");
      for (IModeFollower f : followers) {
         f.testExit();
      }
   };

}
