package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class LauncherSubsystem {
    private DcMotorEx launcher;
    private CRServo windmillServo;

    public final int LAUNCHER_TARGET_VELOCITY = 1250;
    public final int LAUNCHER_MIN_VELOCITY = 1200;

    public LauncherSubsystem(HardwareMap hardwareMap) {
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        windmillServo = hardwareMap.get(CRServo.class, "windmill");

        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(40, 0, 0, 12.5));

        windmillServo.setPower(0);
        windmillServo.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setLaunching(boolean input, double intakePowerRef) {
        if (input) {
            launcher.setVelocity(LAUNCHER_TARGET_VELOCITY);
        } else {
            launcher.setVelocity(0);
        }

        if (input && launcher.getVelocity() > LAUNCHER_MIN_VELOCITY) {
            windmillServo.setPower(1);
        } else {
            windmillServo.setPower(0);
        }
    }

    public double getWindmillPower(boolean input) {
        if (input && launcher.getVelocity() > LAUNCHER_MIN_VELOCITY) {
            return 1.0;
        }
        return 0.0;
    }

    public double getLauncherVelocity() {
        return launcher.getVelocity();
    }
}
