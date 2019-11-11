package org.firstinspires.ftc.teamcode.visual.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.visual.webcam.PhoneManager;

@TeleOp
@Disabled
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
