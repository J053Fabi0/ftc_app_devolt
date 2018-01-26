package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by linksake on 25/01/18.
 */

@TeleOp(name = "Color", group = "Pruebas")
//@Disabled
public class T_TEST_COLOR extends OpMode{

    public DcMotor left;
    public DcMotor right;
    public DcMotor elevator;
    public Servo claw1;
    public Servo claw2;
    public Servo color;
    public double pos1;
    public double pos2;
    public ColorSensor cSensor;

    @Override
    public void init() {

        //Iniciación de los componentes
        left = hardwareMap.dcMotor.get("leftMotor");
        right = hardwareMap.dcMotor.get("rightMotor");
        elevator = hardwareMap.dcMotor.get("elevatorMotor");
        claw1 = hardwareMap.servo.get("1stClawServo");
        claw2 = hardwareMap.servo.get("2ndClawServo");
        color = hardwareMap.servo.get("colorServo");
        pos1 = claw1.getPosition();
        pos2 = claw2.getPosition();
        claw1.setPosition(0.02);
        claw2.setPosition(1);
        cSensor = hardwareMap.colorSensor.get("colorSensor");
        color.setPosition(1);

    }

    @Override
    public void loop() {

        //Movimiento del robot
        left.setPower(gamepad1.left_stick_y);
        right.setPower(-gamepad1.right_stick_y);

        //Movimiento del elevador
        elevator.setPower(gamepad2.right_stick_y);
        elevator.setPower(-gamepad2.right_stick_y);
        if (gamepad2.right_stick_y > 0){
            telemetry.addData("ELEVADOR","Ahrre (?");
        }

        //Agarre de garras
        if (gamepad2.a) {
            claw1.setPosition(0.02);
            claw2.setPosition(1);
        }
        else if (gamepad2.b) {
            claw1.setPosition(.7);
            claw2.setPosition(.7);
        }

        //Prueba de funcionamiento del sensor de color
        if (gamepad2.y) {
            cSensor.enableLed(true);
            color.setPosition(.7);
            telemetry.addData("COLOR",cSensor.argb());
        }


    }
}
