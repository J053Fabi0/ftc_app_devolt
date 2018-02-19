
package org.firstinspires.ftc.teamcode;

/**
 * Created by Marylotus~ on 16/02/2018.
 */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name = "Vuforia")
public class Vuforia extends LinearOpMode {
VuforiaLocalizer vuforialocalizer;
VuforiaLocalizer.Parameters parameters;
VuforiaTrackables visionTargets;
VuforiaTrackable Target;
VuforiaTrackableDefaultListener listener;
OpenGLMatrix LastknownLocation;
OpenGLMatrix phoneLocation;
public static final String VUFORIA_KEY = "AUUUBeX/////AAAAmSUGvaUq2kWOu2xcX6kAVQ1hodp/FRCLkfKt706PralxoLescxwinKdhrH2bzwEppyrFSMM5qZUz3UicEdIqaDnnpaE31yhe2Y/7utckvKVjne///W0IGy+dQhpNoaPutzOghDTrCY/lVVBW4GyubwVUS5S1VXls6vCFCLLbdh5+KAVl3mWIVtIZQEGt2Dhtc7xRCtSt8zXlFzRaqgR8Ao1ZFsJjgV1dZofT73RXwaIuoLse8r3HRXEg06zrL5ew4PoqRhKsbaWZBY7gLkbLGIoOgh7F63CR2XxK62IcsJgvhUQPQZS5NWNZb2UwKGkTpsFXzBeNY+D+/17+p57qc8UlVFnG6rtG0Qu4Vr8BXsHz";
public void runOpMode() throws InterruptedException {
    setupVuforia();
    LastknownLocation = createMatrix(0,0,0,0,0,0);
    waitForStart();
    visionTargets.activate();
    while (opModeIsActive()) {
OpenGLMatrix latestLocation = listener.getUpdatedRobotLocation();
if (latestLocation != null)
    LastknownLocation = latestLocation;
telemetry.addData("Tracking" + Target.getName(), listener.isVisible());
telemetry.addData("Last Known Location", formatMatrix(LastknownLocation));

        telemetry.update();
        idle();
    }
}
public void setupVuforia() {
    parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
    parameters.vuforiaLicenseKey = VUFORIA_KEY;
    parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
    vuforialocalizer = ClassFactory.createVuforiaLocalizer(parameters);

    visionTargets = vuforialocalizer.loadTrackablesFromAsset("FTC_2016-17");
    Target = visionTargets.get(0);
    Target.setName("Wheels Target");
    Target.setLocation(createMatrix(0,500,0,90,0,90));
    phoneLocation = createMatrix(0,225,0,90,0,0);
    listener = (VuforiaTrackableDefaultListener) Target.getListener();
    listener.setPhoneInformation(phoneLocation, parameters.cameraDirection);
}
public OpenGLMatrix createMatrix(float x, float y, float z, float u, float v, float w)
{
    return OpenGLMatrix.translation(x, y,z).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, u,v, w));




}
public String formatMatrix(OpenGLMatrix matrix)
{
    return  matrix.formatAsTransform();

}
}