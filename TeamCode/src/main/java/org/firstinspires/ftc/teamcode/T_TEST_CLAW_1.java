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
    public double pos2;

    @Override
    public void init() {

        left = hardwareMap.dcMotor.get("leftMotor");
        right = hardwareMap.dcMotor.get("rightMotor");
        elevator = hardwareMap.dcMotor.get("elevatorMotor");
        claw1 = hardwareMap.servo.get("1stClawServo");
        claw2 = hardwareMap.servo.get("2ndClawServo");
        pos1 = claw1.getPosition();
        pos2 = claw2.getPosition();
        claw1.setPosition(0.02);
        claw2.setPosition(1);

    }

    @Override
    public void loop() {

        telemetry.addData("¡Funcionando!", "Si");

        left.setPower(gamepad1.left_stick_y);
        right.setPower(-gamepad1.right_stick_y);
        elevator.setPower(gamepad2.right_stick_y);
        elevator.setPower(-gamepad2.right_stick_y);
        if (gamepad2.right_stick_y > 0){
            telemetry.addData("ELEVADOR","Ahrre (?");
        }
        
        if (gamepad2.a) {
            claw1.setPosition(0.02);
            claw2.setPosition(1);
        }
        else if (gamepad2.b) {
            claw1.setPosition(.7);
            claw2.setPosition(.7);
        }

    }
}
