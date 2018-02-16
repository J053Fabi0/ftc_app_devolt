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
    public Servo claw1; // Garra 1
    public Servo claw2; // Garra 2
    //public Servo claw3; // Motor del sensor de color
    //public Servo claw4; // Garra de reliquia

    @Override
    public void init() {

        left = hardwareMap.dcMotor.get("leftMotor");
        right = hardwareMap.dcMotor.get("rightMotor");
        elevator = hardwareMap.dcMotor.get("elevatorMotor");
        claw1 = hardwareMap.servo.get("1stClawServo");
        claw2 = hardwareMap.servo.get("2ndClawServo");

        // Poner las garras en su posicion
        claw1.setPosition(0.02);
        claw2.setPosition(1);

    }

    @Override
    public void loop() {

        telemetry.addData("¡Funcionando!", "Si");

        left.setPower(gamepad1.left_stick_y);
        right.setPower(-gamepad1.right_stick_y);

        elevator.setPower(gamepad2.right_trigger);
        telemetry.addData("Right Trigger power: ", gamepad2.right_trigger);

        elevator.setPower(-gamepad2.left_trigger);
        telemetry.addData("Left Trigger power: ", gamepad2.left_trigger);
        
        if (gamepad2.a) {
            claw1.setPosition(0.02);
            claw2.setPosition(1);
        }
        else if (gamepad2.b) {
            claw1.setPosition(0.3);
            claw2.setPosition(0.7);
        }

        telemetry.addData("gamepad2.x: ", gamepad2.x);
        telemetry.addData("gamepad2.y: ", gamepad2.y);

        /*
        // Garra de reliquia
        if(gamepad2.x) {
            claw4.setPosition(0.7);
        }else if(gamepad2.y) {
            claw4.setPosition(0.3);
        }
        */

    }
}
