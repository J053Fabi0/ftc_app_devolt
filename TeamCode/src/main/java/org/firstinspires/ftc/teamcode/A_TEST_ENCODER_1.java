package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by linksake on 19/01/18.
 */

@Autonomous (name = "Prueba con Encoders", group = "Pruebas")
public class A_TEST_ENCODER_1 extends OpMode {

    public DcMotor left;
    public DcMotor right;
    public DcMotor elevator;

    @Override
    public void init() {
        left = hardwareMap.dcMotor.get("leftMotor");
        right = hardwareMap.dcMotor.get("rightMotor");
        elevator = hardwareMap.dcMotor.get("elevatorMotor");
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {

        left.setMode(DcMotor.RunMode.RESET_ENCODERS);
        right.setMode(DcMotor.RunMode.RESET_ENCODERS);
        elevator.setMode(DcMotor.RunMode.RESET_ENCODERS);
        move(1120);
        move(1100);
        move(1200);
        left.setPower(.25);
        right.setPower(.25);
        elevator.setPower(.25);
        while (left.isBusy() && right.isBusy()) {
            telemetry.addData("FUNCIONA:","SI");
        }
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public void move(int p) {
        left.setTargetPosition(p);
        right.setTargetPosition(p);
        elevator.setTargetPosition(p);
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
