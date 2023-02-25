# Controls Layout / Interface

## Types of Inputs
- [button] - button, active high
- [analog] - analog value

## Driver
| Type     | Gamepad Control   | Action                  |
| -------- | ----------------- | ----------------------- |
| [analog] | (Left Joystick U-D) | Drivetrain Speed (forward/backward) |
| [analog] | (Right Joystick L-R) | Drivetrain Rotate (left/right) |
| [button] | (Right Bumper) | Drivetrain Crawl Enabled *{whenHeld}* |
|  ~~[button] (Left Bumper) | Drive Train Turbo Enabled *{whenHeld}*~~ |
|  ~~[button] (Back Button) | Switch Drive Direction~~ |

## Operator
### Arm
| Type     | Gamepad Control   | Action                  |
| -------- | ----------------- | ----------------------- |
| [analog] | Left Joysick F-B  | Rotate (down/up) |
| [analog] | Right Joysick F-B | Extend (out/in) |
| [button] | Yellow #? | Rotate to High Setpoint |
| [button] | Red #? | Rotate to Mid Setpoint |
| [button] | Blue #? | Rotate to Low Setpoint |
### Gripper
| Type     | Gamepad Control   | Action                  |
| -------- | ----------------- | ----------------------- |
| [analog] | Left Trigger) | Pull In |
| [analog] | Right Trigger) | Push Out |
| [button] | Blue #3) | Open  *{whenHeld}* |
