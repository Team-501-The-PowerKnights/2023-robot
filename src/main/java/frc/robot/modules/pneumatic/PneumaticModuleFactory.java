/*-----------------------------------------------------------------------*/
/* Copyright (c) Team 501 - The PowerKnights. All Rights Reserved.       */
/* Open Source Software - may be modified and shared by other FRC teams  */
/* under the terms of the Team501 license. The code must be accompanied  */
/* by the Team 501 - The PowerKnights license file in the root directory */
/* of this project.                                                      */
/*-----------------------------------------------------------------------*/

package frc.robot.modules.pneumatic;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.modules.ModuleNames;
import frc.robot.properties.PKProperties;
import frc.robot.properties.PropertiesManager;
import frc.robot.telemetry.TelemetryNames;
import frc.robot.utils.PKStatus;

import riolog.PKLogger;
import riolog.RioLogger;


/**
 * 
 */
public class PneumaticModuleFactory {

    /** Our classes' logger **/
    private static final PKLogger logger = RioLogger.getLogger(PneumaticModuleFactory.class.getName());

    /** Singleton instance of class for all to use **/
    private static IPneumaticModule ourInstance;
    /** Name of our module **/
    private static final String myName = ModuleNames.pneumaticName;

    /** Properties for subsystem */
    private static PKProperties props;

    /**
     * Constructs instance of the module. Assumed to be called before any usage of
     * the module; and verifies only called once. Allows controlled startup
     * sequencing of the robot and all it's modules.
     **/
    public static synchronized void constructInstance() {
        SmartDashboard.putNumber(TelemetryNames.Pneumatic.status, PKStatus.inProgress.tlmValue);

        if (ourInstance != null) {
            throw new IllegalStateException(myName + " Already Constructed");
        }

        props = PropertiesManager.getInstance().getProperties(myName);
        logger.info(props.listProperties());
        String className = props.getString("className");

        loadImplementationClass(className);
    }

    private static void loadImplementationClass(String myClassName) {
        String myPkgName = PneumaticModuleFactory.class.getPackage().getName();
        if (myClassName.isEmpty()) {
            logger.info("no class specified; go with subsystem default");
            myClassName = new StringBuilder().append(PropertiesManager.getInstance().getImpl()).append(myName)
                    .append("Subsystem").toString();
        }
        String classToLoad = new StringBuilder().append(myPkgName).append(".").append(myClassName).toString();
        logger.debug("class to load: {}", classToLoad);

        logger.info("constructing {} for {} module", myClassName, myName);
        try {
            @SuppressWarnings("rawtypes")
            Class myClass = Class.forName(classToLoad);
            @SuppressWarnings("deprecation")
            Object myObject = myClass.newInstance();
            ourInstance = (IPneumaticModule) myObject;
            SmartDashboard.putNumber(TelemetryNames.Pneumatic.status, PKStatus.success.tlmValue);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            logger.error("failed to load class; instantiating default stub for: {}", myName);
            ourInstance = new StubPneumaticModule();
            SmartDashboard.putNumber(TelemetryNames.Pneumatic.status, PKStatus.degraded.tlmValue);
        }
        SmartDashboard.putString(TelemetryNames.Pneumatic.implClass, ourInstance.getClass().getSimpleName());
    }

    /**
     * Returns the singleton instance of the module in the form of the
     * <i>Interface</i> that is defined for it. If it hasn't been constructed yet,
     * throws an <code>IllegalStateException</code>.
     *
     * @return singleton instance of module
     **/
    public static IPneumaticModule getInstance() {
        if (ourInstance == null) {
            throw new IllegalStateException(myName + " Not Constructed Yet");
        }

        return ourInstance;
    }

}
