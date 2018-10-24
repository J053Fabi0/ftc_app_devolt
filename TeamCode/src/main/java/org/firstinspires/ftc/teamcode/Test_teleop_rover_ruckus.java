package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by angel on 14/11/17.
 */


@TeleOp (name="TeleOP", group = "Control")
public class Test_teleop_rover_ruckus extends OpMode{

    public DcMotor left = null;
    public DcMotor right = null;
    public DcMotor elevatorLeft = null;
    public DcMotor elevatorRight = null;
    public Servo claw = null; // Garra 2
    //public Servo claw3; // Motor del sensor de color
    //public Servo claw4; // Garra de reliquia

    @Override
    public void init() {

        left = hardwareMap.dcMotor.get("leftMotor");
        right = hardwareMap.dcMotor.get("rightMotor");
        elevatorLeft = hardwareMap.dcMotor.get("elevatorleftMotor");
       elevatorRight = hardwareMap.dcMotor.get ("elevatorrightMotor");


        claw = hardwareMap.servo.get("clawServo");

        // Poner las garras en su posicion
        claw.setPosition(1);

    }

    @Override
    public void loop() {

        telemetry.addData("¡Funcionando!", "Si");

        left.setPower(gamepad1.left_stick_y);
        right.setPower(-gamepad1.right_stick_y);

        elevatorLeft.setPower(gamepad2.right_trigger);
        telemetry.addData("Right Trigger power: ", gamepad2.right_trigger);

        elevatorRight.setPower(-gamepad2.left_trigger);
        telemetry.addData("Left Trigger power: ", gamepad2.left_trigger);

        if (gamepad2.a) {
            claw.setPosition(0.02);


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
