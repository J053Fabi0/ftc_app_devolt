package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import android.graphics.Color;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

@Autonomous(name="Prueba con Encoders 3", group="Pruebas ")

public class A_TEST_ENCODER_3 extends LinearOpMode {

    /* Declare OpMode members. */
    A_TEST_ENCODER_3_Hardware robot = new A_TEST_ENCODER_3_Hardware() ;   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 1220 ; // Andymark N everest 60 motor
    static final double DRIVE_GEAR_REDUCTION = 0.2
            ;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 3.8 ;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.14159265358979323846);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;

    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        robot.left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Espera a que le des play
        waitForStart();

        // Baja el sensor a las pelotas ( ͡° ͜ʖ ͡°)
        robot.claw3.setPosition(1);

        // Espera por precaución
        sleep(500);

        // Actualiza el sensor y determina si la pelota es azul o roja
        robot.colors = robot.colorSensor.getNormalizedColors();

        double vel = 0.01;
        int time = 500;

        if (robot.colors.red > robot.colors.blue) {
            telemetry.addData("Ball Color", "Red");

            encoderDrive(DRIVE_SPEED,10, -10, .1);
            encoderDrive(DRIVE_SPEED,-10,10,.1);
            robot.right.setPower(vel);
            robot.left.setPower(-vel);
            sleep(time);
            robot.right.setPower(-vel);
            robot.left.setPower(vel);
            sleep(time);
            robot.right.setPower(0);
            robot.left.setPower(0);
        }else {
            telemetry.addData("Ball Color", "Blue");

            encoderDrive(DRIVE_SPEED,-10, 10, .1);
            encoderDrive(DRIVE_SPEED,10, -10, .1);
            robot.right.setPower(-vel);
            robot.left.setPower(vel);
            sleep(time);
            robot.right.setPower(vel);
            robot.left.setPower(-vel);
            sleep(time);
            robot.right.setPower(0);
            robot.left.setPower(0);
        }
        telemetry.update();

        // Espera un poco... un poquiiiito mááááás para avanzar al zooonde del safee
        sleep(1000);

        // Sube el sensor
        robot.claw3.setPosition(0.02);

        // Espera para que deje que los motores suban
        sleep(1000);

        // Cierra las garras para agarrar el bloque de enfrente
        robot.claw1.setPosition(0.3);
        robot.claw2.setPosition(0.7);

        // Espera para que deje que los motores hagan lo suyo
        sleep(5000);


    }

    public void moverElevador(double timeoutS, double speed) {
        robot.elevator.setPower(speed);
        runtime.reset();
        sleep((long) timeoutS * 1000);
        robot.elevator.setPower(0);
    }

    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.left.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.right.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            robot.left.setTargetPosition(newLeftTarget);
            robot.right.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Reset the timeout time and start motion.
            runtime.reset();
            robot.left.setPower(Math.abs(speed));
            robot.right.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.left.isBusy() && robot.right.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        robot.left.getCurrentPosition(),
                        robot.right.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.left.setPower(0);
            robot.right.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // pause after each move
        }
    }
}
