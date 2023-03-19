# Electrical / Controls Description
# PROTO-BOT


## Motors

| Subsystem      | Controller Type | Motor Type    | CAN ID | Position    | PDB ID |
| -------------- | --------------- | ------------- | ------ | ----------- | ------ |
| Drive          | Spark Max       | NEO Brushless | 11     | Left Front  | 15     |
| Drive          | Spark Max       | NEO Brushless | 12     | Left Rear   | 14     |
| Drive          | Spark Max       | NEO Brushless | 13     | Right Front | 0      |
| Drive          | Spark Max       | NEO Brushless | 14     | Right Rear  | 1      |
| Arm Rotator    | Spark Max       | NEO Brushless | 21     | --          | 10     |
| Arm Extender   | Spark Max       | NEO Brushless | 22     | --          | 11     |
| Gripper        | Spark Max       | REV HD Hex    | 31     | Left        | 2      |
| Gripper        | Spark Max       | REV HD Hex    | 32     | Right       | 17     |
| Wrist          | Spark Max       | NEO Brushless | 41     | --          | 16     |

## Modules

| Module         | Module Type      | CAN ID | Position    | PDB ID |
| -------------- | ---------------- | ------ | ----------- | ------ |
| Power          | Rev Robotics PDB | 1      |             |        | 
| Voltage        | CTRE VRM         |        |             | 23     | 
| Processor      | NI RoboRIO       | 0      |             | 21     | 
|                |                  |        |             |        | 
|                |                  |        |             |        | 
|                |                  |        |             |        | 

## Sensors

| Subsystem      | Mechanism Type   | Sensor Type        | Port      | Position    |
| -------------- | ---------------- | ------------------ | --------- | ----------- |
| Drive          | Gyro             | NavX2-MXP          | 0         |             |
| Drive          |                  |                    |           | Left Front  |
| Drive          |                  |                    |           | Right Front |
| Arm Rotator    | Relative Encoder |                    | --        | --          |
| Arm Extender   | Relative Encoder |                    | --        | --          |
|                |                  |                    |           |             |
|                |                  |                    |           |             |

