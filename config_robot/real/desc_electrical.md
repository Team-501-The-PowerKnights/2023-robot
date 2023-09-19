# Electrical / Controls Description
# REAL-BOT


## Motors

| Subsystem      | Controller Type | Motor Type    | CAN ID | Position    | PDB ID |
| -------------- | --------------- | ------------- | ------ | ----------- | ------ |
| Drive          | Spark Max       | NEO Brushless | 11     | Left Front  | 8      |
| Drive          | Spark Max       | NEO Brushless | 12     | Left Rear   | 9      |
| Drive          | Spark Max       | NEO Brushless | 13     | Right Front | 1      |
| Drive          | Spark Max       | NEO Brushless | 14     | Right Rear  | 0      |
| Lift           | Spark Max       | NEO Brushless | 21     | --          | 12     |
| Arm            | Spark Max       | NEO Brushless | 22     | --          | 13     |
| Gripper        | Spark Max       | REV HD Hex    | 31     | Left        | 10     |
| Gripper        | Spark Max       | REV HD Hex    | 32     | Right       | 11     |
| Turret         | Spark Max       | NEO Brushless | 51     | --          | 4      |

## Modules

| Module         | Module Type      | CAN ID | Position    | PDB ID |
| -------------- | ---------------- | ------ | ----------- | ------ |
| Power          | Rev Robotics PDB | 1      |             |        | 
| Voltage        | CTRE VRM         |        |             | ??     | 
| Processor      | NI RoboRIO V2    | 0      |             | 20     | 
| Radio Power    |                  |        |             | 22     | 
|                |                  |        |             |        | 
|                |                  |        |             |        | 

## Sensors

| Subsystem      | Mechanism Type   | Sensor Type        | Port      | Position    |
| -------------- | ---------------- | ------------------ | --------- | ----------- |
| Drive          | Gyro             | NavX2-MXP          | 0         |             |
| Drive          |                  |                    |           | Left Front  |
| Drive          |                  |                    |           | Right Front |
| Lift           | Relative Encoder |                    |           |             |
| Arm            | Relative Encoder |                    |           |             |
| Gripper        | Relative Encoder |                    |           |             |
| ???            | Vision           | Limelight          | 22        |             |
|                |                  |                    |           |             |
