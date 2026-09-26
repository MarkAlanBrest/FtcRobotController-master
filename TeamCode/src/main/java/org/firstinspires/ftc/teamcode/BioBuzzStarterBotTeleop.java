/*   MIT License
 *   Copyright (c) [2026] [Base 10 Assets, LLC]
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:

 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.

 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LauncherSubsystem;

@TeleOp(name = "BioBuzz StarterBot Teleop", group = "StarterBot")
public class BioBuzzStarterbotTeleop extends OpMode {

    private Drivetrain drivetrain;
    private LauncherSubsystem launcherSubsystem;
    private IntakeSubsystem intakeSubsystem;

    double intakePower;

    @Override
    public void init() {
        drivetrain = new Drivetrain(hardwareMap);
        launcherSubsystem = new LauncherSubsystem(hardwareMap);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);

        telemetry.addData("Status", "Initialized (Mecanum Subsystems)");
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        // Mecanum drive: left stick vertical = axial (forward/backward), left stick horizontal = lateral (strafe), right stick horizontal = yaw (turn)
        double axial   = -gamepad1.left_stick_y;
        double lateral =  gamepad1.left_stick_x;
        double yaw     =  gamepad1.right_stick_x;

        drivetrain.mecanumDrive(axial, lateral, yaw);

        intakePower = gamepad1.right_trigger - gamepad1.left_trigger;

        boolean launching = gamepad1.right_bumper;
        launcherSubsystem.setLaunching(launching, intakePower);
        if (launching && launcherSubsystem.getLauncherVelocity() > launcherSubsystem.LAUNCHER_MIN_VELOCITY) {
            intakePower += 0.5;
        }

        intakeSubsystem.setPower(intakePower);
        intakeSubsystem.setServos(intakePower);

        telemetry.addData("Launcher Velocity", launcherSubsystem.getLauncherVelocity());
        telemetry.addData("Intake Power", intakePower);
        telemetry.update();
    }

    @Override
    public void stop() {
    }
}
