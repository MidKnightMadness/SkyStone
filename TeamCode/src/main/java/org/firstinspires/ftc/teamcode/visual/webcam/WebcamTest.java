package org.firstinspires.ftc.teamcode.visual.webcam;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

@TeleOp
public class WebcamTest extends OpMode {

    private WebcamManager webcamManager = new WebcamManager();

    public WebcamTest()
    {
        msStuckDetectStop = 20000;
    }

    @Override
    public void init() {
        webcamManager.startCaptureWithViews(hardwareMap.get(WebcamName.class, "Webcam 1"), hardwareMap.appContext);
        webcamManager.startSavingImages(6);
        //Bitmap currentFrame = webcamManager.getCurrentFrame();
    }

    @Override
    public void loop() {

    }

    @Override
    public void stop()
    {
        webcamManager.stopCapture();
    }
}
