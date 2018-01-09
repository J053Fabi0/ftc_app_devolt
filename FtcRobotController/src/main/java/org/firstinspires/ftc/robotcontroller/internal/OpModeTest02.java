package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by angel on 14/11/17.
 */

public class OpModeTest02  extends OpMode{

    public DcMotor left;
    public DcMotor right;
    public DcMotor elevator;
    public Servo claw;

    @Override
    public void init() {

        left = hardwareMap.dcMotor.get("leftMotor");
        right = hardwareMap.dcMotor.get("rightMotor");
        elevator = hardwareMap.dcMotor.get("elevatorMotor");
        claw = hardwareMap.servo.get("clawServo");

    }

    @Override
    public void loop() {

        left.setPower(gamepad1.left_stick_y);
        right.setPower(-gamepad1.left_stick_y);
        elevator.setPower(gamepad1.right_trigger);

        if (gamepad1.a) {
            claw.setPosition(0.5);
        } else {
            claw.setPosition(0.002);
        }


    }
}
