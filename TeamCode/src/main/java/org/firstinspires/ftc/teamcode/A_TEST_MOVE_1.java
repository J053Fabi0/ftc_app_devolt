package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Ivan on 19/01/18.
 */

@Autonomous (name = "Movimiento 1", group = "Pruebas")
public class A_TEST_MOVE_1 extends OpMode {

    public DcMotor driveR;
    public DcMotor driveL;

    @Override
    public void init() {
        driveR = hardwareMap.dcMotor.get("driveR");
        driveL = hardwareMap.dcMotor.get("driveL");
    }

    @Override
    public void loop() {
        for (int time = 0; time == 100; time++) {
            driveR.setPower(.1);
            driveL.setPower(.1);
            telemetry.addData("DERECHO", "TIEMPO - ", time);
            for (int reverseTime = 0; time == 100; reverseTime++) {
                driveR.setPower(-.1);
                driveL.setPower(-.1);
                telemetry.addData("REVERSA", "TIEMPO - ", reverseTime);
            }
        }
    }
}
