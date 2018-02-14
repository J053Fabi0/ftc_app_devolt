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
    public Servo claw1;
    public Servo claw2;
    @Override
    public void init() {
        left = hardwareMap.dcMotor.get("leftMotor");
        right = hardwareMap.dcMotor.get("rightMotor");
        elevator = hardwareMap.dcMotor.get("elevatorMotor");
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        claw1 = hardwareMap.servo.get("1stClawServo");
        claw2 = hardwareMap.servo.get("2ndClawServo");
    }

    @Override
    public void loop() {

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mover(3120,3120,0);
        mover(3120,-3120,0);
        mover(3120,3120,0);
        mover(-3120,3120,0);
        mover(3120,3120,0);
        mover( 0,0,0);
        abrir();
        left.setPower(.25);
        right.setPower(.25);
        elevator.setPower(.25);
        while (left.isBusy() && right.isBusy()) {
            telemetry.addData("Movimiento:","SI");
        }
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        stop();

    }

    public void mover(int pl, int pr, int pe) {
        left.setTargetPosition(pl);
        right.setTargetPosition(pr);
        elevator.setTargetPosition(pe);
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void stop() {
        left.setPower(0);
        right.setPower(0);

    }
    public void abrir(){
        claw1.setPosition(.002);
        claw2.setPosition(1);
    }
    public void cerrar(){
        claw1.setPosition(.2);
        claw2.setPosition(.8);
    }
}
