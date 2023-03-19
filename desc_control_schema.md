# Controls Layout / Interface

## Types of Inputs
- [button] - button, active high
- [analog] - analog value

## Driver
| Type     | Gamepad Control    | Action                  |
| -------- | ------------------ | ----------------------- |
| [analog] | Left Joystick F-B  | Drivetrain Speed (forward/backward) |
| [analog] | Right Joystick L-R | Drivetrain Rotate (left/right) |
| [button] | Right Bumper       | Drivetrain Crawl Enabled *{whenHeld}* |
|  ~~[button] (Left Bumper)     | Drive Train Turbo Enabled *{whenHeld}*~~ |
|  ~~[button] (Back Button)     | Switch Drive Direction~~ |

## Operator
### Arm
| Type     | Gamepad Control       | Action                                      |
| -------- | --------------------- | ------------------------------------------- |
| [button] | Right Bumper #6       | Arm Over Pose  [Rotate Over, Extend In]     |
| [button] | Yellow #4             | Arm High Pose  [Rotate High, Extend High]   |
| [button] | Red #2                | Arm Mid Pose   [Rotate Mid,  Extend Mid]    |
| [button] | Blue #1               | Arm Low Pose   [Rotate Low,  Extend Low]    |
| [analog] | Left Joystick #1 F-B  | Extend (out/in)                             |
| [analog] | Right Joystick #5 F-B | Rotate (down/up)                            |

### Gripper
| Type     | Gamepad Control       | Action                                      |
| -------- | --------------------- | ------------------------------------------- |
| [analog] | Left Trigger          | Pull In                                     |
| [analog] | Right Trigger         | Push Out                                    |
