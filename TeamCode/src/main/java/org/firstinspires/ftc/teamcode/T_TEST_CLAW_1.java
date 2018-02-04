package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorColor;

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
    public Servo claw3; // Motor del sensor de color
    public Servo claw4; // Garra de reliquia
    public OpticalDistanceSensor colorSensor;

    @Override
    public void init() {

        // Inisializar elementos
        left = hardwareMap.dcMotor.get("leftMotor");
        right = hardwareMap.dcMotor.get("rightMotor");
        elevator = hardwareMap.dcMotor.get("elevatorMotor");
        claw1 = hardwareMap.servo.get("1stClawServo");
        claw2 = hardwareMap.servo.get("2ndClawServo");
        claw3 = hardwareMap.servo.get("3rdClawServo");
        claw4 = hardwareMap.servo.get("4thClawServo");
        colorSensor = hardwareMap.opticalDistanceSensor.get("colorSensor");

        // Poner las garras en su posicion
        claw1.setPosition(0.02);
        claw2.setPosition(1);
        claw4.setPosition(0.7);


    }

    @Override
    public void loop() {

        //  Motores traseros
        left.setPower(gamepad1.left_stick_y);
        right.setPower(-gamepad1.right_stick_y);

        // Elevador
        elevator.setPower(gamepad2.right_trigger);
        elevator.setPower(-gamepad2.left_trigger);

        // Colro sensor test
        telemetry.addData("ColorSensor.status", colorSensor.status());
        telemetry.addData("ColorSensor.getLightDetected", colorSensor.getLightDetected());
        telemetry.addData("ColorSensor.getRawLightDetected", colorSensor.getRawLightDetected());
        telemetry.addData("ColorSensor.getRawLightDetectedMax", colorSensor.getRawLightDetectedMax());

        // Garra de bloques
        if (gamepad2.a) {
            claw1.setPosition(0.02);
            claw2.setPosition(1);
        }
        else if (gamepad2.b) {
            claw1.setPosition(.3);
            claw2.setPosition(.7);
        }

        // Garra de reliquia
        if (gamepad2.x) {
            claw4.setPosition(0.7);
        }else if (gamepad2.y) {
            claw4.setPosition(0.3);
        }

    }
}
