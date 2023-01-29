# Electrical / Controls Description
# PROTO-BOT


## Motors

| Subsystem      | Controller Type | Motor Type    | CAN ID | Position    | PDB ID |
| -------------- | --------------- | ------------- | ------ | ----------- | ------ |
| Drive          | Spark Max       | NEO Brushless | 11     | Left Front  | 15     |
| Drive          | Spark Max       | NEO Brushless | 12     | Left Rear   | 14     |
| Drive          | Spark Max       | NEO Brushless | 13     | Right Front | 0      |
| Drive          | Spark Max       | NEO Brushless | 14     | Right Rear  | 1      |
| Arm            | Spark Max       | NEO Brushless | 21     | Rotate      | 10     |
| Arm            | Spark Max       | NEO Brushless | 22     | Extend      | 11     |
|                | Spark Max       |               | 61     |             | 12     |
|                | Spark Max       |               | 62     |             | 13     |

## Modules

| Mechanism      | Controller Type  | CAN ID | Position    | PDB ID |
| -------------- | ---------------- | ------ | ----------- | ------ |
| Power          | Rev Robotics PDB | 1      |             |        | 
| Radio          |                  |        |             | 20     | 
| Processor      | NI RoboRIO       |        |             | 21     | 
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
