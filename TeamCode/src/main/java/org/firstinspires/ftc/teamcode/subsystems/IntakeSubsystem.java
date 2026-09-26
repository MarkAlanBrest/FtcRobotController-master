package org.firstinspires.ftc.teamcode.subsystems;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeSubsystem {
    private DcMotor intake;
    private CRServo leftIntakeServo;
    private CRServo rightIntakeServo;

    public IntakeSubsystem(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotor.class, "intake");
        leftIntakeServo = hardwareMap.get(CRServo.class, "left_intake_servo");
        rightIntakeServo = hardwareMap.get(CRServo.class, "right_intake_servo");

        intake.setZeroPowerBehavior(BRAKE);

        leftIntakeServo.setPower(0);
        rightIntakeServo.setPower(0);

        rightIntakeServo.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setPower(double power) {
        intake.setPower(power);
    }

    public void setServos(double power) {
        leftIntakeServo.setPower(power);
        rightIntakeServo.setPower(power);
    }
}
