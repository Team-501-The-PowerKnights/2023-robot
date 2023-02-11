/*------------------------------------------------------------------------*/
/*- Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/*- Open Source Software - may be modified and shared by other FRC teams  */
/*- under the terms of the Team501 license. The code must be accompanied  */
/*- by the Team 501 - The PowerKnights license file in the root directory */
/*- of this project.                                                      */
/*------------------------------------------------------------------------*/

package frc.robot.hmi;

import edu.wpi.first.wpilibj.GenericHID;

import frc.robot.IModeFollower;

import riolog.PKLogger;
import riolog.RioLogger;

abstract class BaseGamepad implements IModeFollower, IGamepad {

	/** Our classes' logger **/
	private static final PKLogger logger = RioLogger.getLogger(BaseGamepad.class.getName());

	/** Our gamepad's name **/
	protected final String myName;

	/** Our joystick */
	protected final GenericHID stick;

	protected BaseGamepad(String name, int port) {
		logger.info("constructing {} for {}", name, port);

		myName = name;

		stick = new GenericHID(port);

		logger.info("constructed");
	}

	@Override
	public void configureButtonBindings() {
		logger.error("Shouldn't be calling this from anywhere");
	}

	/**
	 * 
	 * Lifted from:
	 * https://www.chiefdelphi.com/t/how-do-i-program-a-joystick-deadband/122625
	 * 
	 * @param value
	 * @param cutOff
	 * @return
	 */
	protected final double deadBand(final double value, final double cutOff) {
		double retValue;
		if (value < cutOff && value > (cutOff * (-1))) {
			retValue = 0;
		} else {
			retValue = (value - (Math.abs(value) / value * cutOff)) / (1 - cutOff);
		}
		return retValue;
	}

}