package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by angel on 14/11/17.
 */


@TeleOp (name="2Garras 1", group = "Pruebas")
public class T_TEST_CLAW_1 extends OpMode{

    public DcMotor left;
    public DcMotor right;
    public DcMotor elevator;
    public Servo claw1;
    public Servo claw2;
    public double pos1;

    @Override
    public void init() {

        left = hardwareMap.dcMotor.get("leftMotor");
        right = hardwareMap.dcMotor.get("rightMotor");
        elevator = hardwareMap.dcMotor.get("elevatorMotor");
        claw1 = hardwareMap.servo.get("1stClawServo");
        claw2 = hardwareMap.servo.get("2ndClawServo");

    }

    @Override
    public void loop() {
        left.setPower(gamepad1.left_stick_y);
        right.setPower(-gamepad1.right_stick_y);
        elevator.setPower(gamepad1.right_trigger * 0.7);
        elevator.setPower(gamepad1.left_trigger * -0.7);

        if (gamepad1.a) {
            claw1.setPosition(0.8);
            claw2.setPosition(0.2);
        }
        else {
            claw1.setPosition(0.95);
            claw2.setPosition(0.002);
        }
    }
}
