package org.firstinspires.ftc.teamcode.visual.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Assembly;
import org.firstinspires.ftc.teamcode.visual.VuforiaPosition;

@TeleOp
public class SkystoneAlignmentTest extends OpMode {
    
    public SkystoneAlignmentTest()
    {
        msStuckDetectLoop = 20000;
    }
    
    VuforiaPosition position = new VuforiaPosition();
    @Override
    public void init() {
        Assembly.initialize(telemetry, hardwareMap, position);
        //position.cameraManager.startSavingImages(5);
    }

    @Override
    public void loop() {
        telemetry.addData("Skystone offset: ", position.getSkystoneOffset());
        telemetry.update();
    }

    @Override
    public void stop() {
        position.stop();
    }
}
