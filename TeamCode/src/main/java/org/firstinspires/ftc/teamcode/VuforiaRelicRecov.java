package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Lane Doak of Recharged Orange on 9/9/17.
 */
@Autonomous(name = "VuforiaRelic", group = "Vuforia")
@Disabled
public class VuforiaRelicRecov extends LinearOpMode
{
    OpenGLMatrix lastLocation = null; // WARNING: VERY INACCURATE, USE ONLY TO ADJUST TO FIND IMAGE AGAIN! DO NOT BASE MAJOR MOVEMENTS OFF OF THIS!!
    double tX; // X value extracted from our the offset of the traget relative to the robot.
    double tZ; // Same as above but for Z
    double tY; // Same as above but for Y
    // -----------------------------------
    double rX; // X value extracted from the rotational components of the tartget relitive to the robot
    double rY; // Same as above but for Y
    double rZ; // Same as above but for Z

    DcMotor right; // Random Motor
    DcMotor left; // Random Motor

    VuforiaLocalizer vuforia;

    public void runOpMode()
    {
        right = hardwareMap.dcMotor.get("rightMotor"); // Random Motor
        left = hardwareMap.dcMotor.get("leftMotor"); // Random Motor
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AUUUBeX/////AAAAmSUGvaUq2kWOu2xcX6kAVQ1hodp/FRCLkfKt706PralxoLescxwinKdhrH2bzwEppyrFSMM5qZUz3UicEdIqaDnnpaE31yhe2Y/7utckvKVjne///W0IGy+dQhpNoaPutzOghDTrCY/lVVBW4GyubwVUS5S1VXls6vCFCLLbdh5+KAVl3mWIVtIZQEGt2Dhtc7xRCtSt8zXlFzRaqgR8Ao1ZFsJjgV1dZofT73RXwaIuoLse8r3HRXEg06zrL5ew4PoqRhKsbaWZBY7gLkbLGIoOgh7F63CR2XxK62IcsJgvhUQPQZS5NWNZb2UwKGkTpsFXzBeNY+D+/17+p57qc8UlVFnG6rtG0Qu4Vr8BXsHz";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT; // Use FRONT Camera (Change to BACK if you want to use that one)
        parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES; // Display Axes

        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        waitForStart();

        relicTrackables.activate(); // Activate Vuforia

        while (opModeIsActive())
        {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) { // Test to see if image is visable
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose(); // Get Positional value to use later
                telemetry.addData("Pose", format(pose));
                if (pose != null)
                {
                    VectorF trans = pose.getTranslation();
                    Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                    // Extract the X, Y, and Z components of the offset of the target relative to the robot
                    tX = trans.get(0);
                    tY = trans.get(1);
                    tZ = trans.get(2);

                    // Extract the rotational components of the target relative to the robot. NOTE: VERY IMPORTANT IF BASING MOVEMENT OFF OF THE IMAGE!!!!
                    rX = rot.firstAngle;
                    rY = rot.secondAngle;
                    rZ = rot.thirdAngle;
                }
                if (vuMark == RelicRecoveryVuMark.LEFT)
                { // Test to see if Image is the "LEFT" image and display value.
                    telemetry.addData("VuMark is", "Left");
                    telemetry.addData("X =", tX);
                    telemetry.addData("Y =", tY);
                    telemetry.addData("Z =", tZ);
                } else if (vuMark == RelicRecoveryVuMark.RIGHT)
                { // Test to see if Image is the "RIGHT" image and display values.
                    telemetry.addData("VuMark is", "Right");
                    telemetry.addData("X =", tX);
                    telemetry.addData("Y =", tY);
                    telemetry.addData("Z =", tZ);
                } else if (vuMark == RelicRecoveryVuMark.CENTER)
                { // Test to see if Image is the "CENTER" image and display values.
                    telemetry.addData("VuMark is", "Center");
                    telemetry.addData("X =", tX);
                    telemetry.addData("Y =", tY);
                    telemetry.addData("Z =", tZ);

                }
            } else
            {
                telemetry.addData("VuMark", "not visible");
            }
            telemetry.update();
        }
    }
    String format(OpenGLMatrix transformationMatrix)
    {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
}
