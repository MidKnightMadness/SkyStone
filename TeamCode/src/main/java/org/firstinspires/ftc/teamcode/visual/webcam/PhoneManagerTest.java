package org.firstinspires.ftc.teamcode.visual.webcam;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class PhoneManagerTest extends OpMode {
    PhoneManager phoneManager = new PhoneManager();

    @Override
    public void init() {
        phoneManager.startCaptureWithViews(telemetry, hardwareMap.appContext);
    }

    @Override
    public void loop() {

    }

    @Override
    public void stop() {
        super.stop();
        phoneManager.stopCapture();
    }
}
