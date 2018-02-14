package org.firstinspires.ftc.teamcode;

/**
 * Created by J053_Fabi0 on 16/01/18.
 */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@TeleOp(name="Vuforia", group="Pruebas")
@Autonomous(name = "Vuforia")
@Disabled
public class Vuforia extends LinearOpMode {

    // Variables to be used for later
    VuforiaLocalizer vuforiaLocalizer;
    VuforiaLocalizer.Parameters parameters;
    VuforiaTrackables visionTargets;
    VuforiaTrackable target;
    VuforiaTrackableDefaultListener listener;

    OpenGLMatrix lastKnownLocation;
    OpenGLMatrix phoneLocation;

    public static final String VUFORIA_KEY = "AcZRIf7/////AAAAmW6HxC3tdkogrFnZenJSayJFd+s4BRvCV1JsrNLyoNqkJWpaeFfTvvVKrd+mplQfV8pkZq9yyAq4wuoDmq4wfGHew750LzpgULIn9wVadHPcrxxO0VMT1dmpYzeMPTJoA3dxIz0n2jviNEfffKOdhClybebpP+sujNaSqLvvFSfRZDcaBpYlj0yUJXEMAcOhbvNMTcVeZGBnRPGNQRgQbkcU5hJu34V2TDj8RgPwGYZP2X7lgr4pCjXSJK42KlPLF+q0KWqIUTGnSAsKKKJc0008+n1ovOOipxFXI1rbzrN9rp7q1UB+GUbmGgA4dOk1MyrMBJQzD6OWTW1uE4KCClItIiUAKhilqhy35pYrPTIr";

    public void runOpMode() throws InterruptedException {

        setUpVuforia();

        // We don't know where the robot is, so set it to the origin
        // If we don't include this, it would be null, which would cause errors later on
        lastKnownLocation = createMatrix(0,0,0,0,0,0);

        waitForStart();

        // Start tracking the targets
        visionTargets.activate();

        while (opModeIsActive()) {
            // Ask the listener for the latest information on where the robot is
            OpenGLMatrix latestLocation = listener.getUpdatedRobotLocation();

            // The listener will sometimes return null, so we check for that to prevent errors
            if (latestLocation != null)
                lastKnownLocation = latestLocation;

            // Send information about whether the target is visible, and where the robot is
            telemetry.addData("Tracking" + target.getName(), listener.isVisible());
            telemetry.addData("Last known Location", formatMatrix(lastKnownLocation));

            // Send telemetry and idle to let hardware catch up
            telemetry.update();
            idle();
        }
    }

    public void setUpVuforia() {
        // Setup parameters to create localizer
        parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        // These are the vision targets that we want to use
        // The string needs to be the name of the appropriate .xml file in the assets folder
        visionTargets = vuforiaLocalizer.loadTrackablesFromAsset("FTC_2016-17");

        // Setup the target to be used
        target = visionTargets.get(0); // 0 corresponds to the wheels target
        target.setName("Wheels Target");
        target.setLocation(createMatrix(0, 500, 0, 90,0,90));

        // Set the phone location on robot
        phoneLocation = createMatrix(0,0,0,0,0,0);

        // Setup listener and inform it of phone information
        listener = (VuforiaTrackableDefaultListener) target.getListener();
        listener.setPhoneInformation(phoneLocation, parameters.cameraDirection);
    }

    // Creates a matrix for determining the locations and orientations of objects
    // Units are millimeters for x, y, and z, and degrees for u, v, and w
    public OpenGLMatrix createMatrix(float x, float y, float z, float u, float v, float w) {
        return OpenGLMatrix.translation(x, y, z).
                multiplied(Orientation.
                        getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, u, v, w));
    }

    // Formats a matrix into a readable string
    public String formatMatrix(OpenGLMatrix matrix) {
        return matrix.formatAsTransform();
    }
}
