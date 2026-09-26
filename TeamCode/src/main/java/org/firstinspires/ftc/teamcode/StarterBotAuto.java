/*
 * Copyright (c) 2026 Base 10 Assets, LLC
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of NAME nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LauncherSubsystem;

@Autonomous(name="StarterBotAuto", group="StarterBot")
public class StarterBotAuto extends OpMode {

    final double DRIVE_SPEED = 0.5;

    private ElapsedTime autoTimer = new ElapsedTime();

    private Drivetrain drivetrain;
    private LauncherSubsystem launcherSubsystem;
    private IntakeSubsystem intakeSubsystem;

    private enum AutonomousState {
        LAUNCH,
        DRIVE,
        COMPLETE
    }

    private AutonomousState autonomousState;
    double intakePower = 0;

    @Override
    public void init() {
        autonomousState = AutonomousState.LAUNCH;

        drivetrain = new Drivetrain(hardwareMap);
        launcherSubsystem = new LauncherSubsystem(hardwareMap);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);

        telemetry.addData("Status", "Initialized (Subsystems Auto)");
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        autoTimer.reset();
    }

    @Override
    public void loop() {
        switch (autonomousState) {
            case LAUNCH:
                launch(true);
                if (autoTimer.seconds() > 10) {
                    launch(false);
                    intakePower = 0;
                    autonomousState = AutonomousState.DRIVE;
                }
                break;
            case DRIVE:
                if (drivetrain.driveDistance(DRIVE_SPEED, -120, DistanceUnit.MM, 2)) {
                    autonomousState = AutonomousState.COMPLETE;
                }
                break;
            case COMPLETE:
                telemetry.addLine("Auto Complete!");
                break;
        }

        intakeSubsystem.setPower(intakePower);
        intakeSubsystem.setServos(intakePower);

        telemetry.addData("AutoState", autonomousState);
        telemetry.addData("Motor Current Positions", "left (%d), right (%d)",
                drivetrain.getLeftPosition(), drivetrain.getRightPosition());
        telemetry.update();
    }

    @Override
    public void stop() {
    }

    void launch(boolean input) {
        launcherSubsystem.setLaunching(input, intakePower);
        if (input && launcherSubsystem.getLauncherVelocity() > launcherSubsystem.LAUNCHER_MIN_VELOCITY) {
            intakePower += 0.5;
        }
    }
}
