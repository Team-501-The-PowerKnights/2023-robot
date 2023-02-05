# Electrical / Controls Description
# PROTO-BOT


## Motors

| Subsystem      | Controller Type | Motor Type    | CAN ID | Position    | PDB ID |
| -------------- | --------------- | ------------- | ------ | ----------- | ------ |
| Drive          | Spark Max       | NEO Brushless | 11     | Left Front  | 15     |
| Drive          | Spark Max       | NEO Brushless | 12     | Left Rear   | 14     |
| Drive          | Spark Max       | NEO Brushless | 13     | Right Front | 0      |
| Drive          | Spark Max       | NEO Brushless | 14     | Right Rear  | 1      |
| Arm            | Spark Max       |               | 21     | Rotate      | ??     |
| Arm            | Spark Max       |               | 22     | Extend      | ??     |
| Gripper        | Spark Max       |               | 31     | Intake      | ??     |
| Gripper        | Spark Max       |               | 32     | Ingest      |        |

## Modules

| Mechanism      | Controller Type  | CAN ID | Position    | PDB ID |
| -------------- | ---------------- | ------ | ----------- | ------ |
| Processor      | NI RoboRIO       | 0      |             | 22     | 
| Power          | Rev Robotics PDB | 1      |             |        | 
| Pneumatic      | REV Robotics PH  | 2      |             | 20     | 
| Voltage        | CTRE VRM         |        |             | 21     | 
|                |                  |        |             |        | 
|                |                  |        |             |        | 
|                |                  |        |             |        | 

## Sensors

| Mechanism   | Sensor Type  | Port      | Position |
| ----------- | ------------ | --------- |----------|
|             |              |           |          |
|             |              |           |          |
|             |              |           |          |
|             |              |           |          |
|             |              |           |          |
|             |              |           |          |
~~| Elevator    | Ultrasonic-y | 0         | Lower    |~~

~~| Drive       | Relative Encoder | Left  | Left Front (Talon SRX)  |~~
~~| Drive       | Relative Encoder | Right | Right Front (Talon SRX) |~~
