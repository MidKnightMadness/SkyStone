package org.firstinspires.ftc.teamcode.visual.webcam;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

@TeleOp
public class WebcamTest extends OpMode {

    private WebcamManager webcamManager = new WebcamManager();

    @Override
    public void init() {
        webcamManager.startCapture(hardwareMap.get(WebcamName.class, "Webcam 1"));

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
