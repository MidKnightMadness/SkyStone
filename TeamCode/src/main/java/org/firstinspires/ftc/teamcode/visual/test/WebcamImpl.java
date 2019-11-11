package org.firstinspires.ftc.teamcode.visual.test;

import android.graphics.Bitmap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.common.Assembly;
import org.firstinspires.ftc.teamcode.visual.webcam.WebcamManager;

public class WebcamImpl extends Assembly {
    private WebcamManager webcamManager = new WebcamManager();
    @Override
    public void init() {
        webcamManager.startCapture(hardwareMap.get(WebcamName.class, "Webcam 1"));

        // here's the current frame:
        Bitmap thisFrame = webcamManager.getCurrentFrame();

        // have fun!
    }
}
