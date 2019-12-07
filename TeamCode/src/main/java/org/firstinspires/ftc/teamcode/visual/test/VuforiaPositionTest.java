package org.firstinspires.ftc.teamcode.visual.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.teamcode.common.Position;
import org.firstinspires.ftc.teamcode.visual.Visual;
import org.firstinspires.ftc.teamcode.visual.VuforiaPosition;

@TeleOp
public class VuforiaPositionTest extends OpMode {

    Visual visual = new VuforiaPosition();

    public VuforiaPositionTest() {
        msStuckDetectInit = 20000;
    }

    @Override
    public void init() {
        visual.hardwareMap = hardwareMap;
        visual.telemetry = telemetry;
        visual.init();
    }

    @Override
    public void loop() {
        Position position = visual.getPosition();
        if (position == null)
            telemetry.addLine("nothing detected");
        else {
            telemetry.addData("x", position.getX().toInches());
            telemetry.addData("y", position.getY().toInches());
            telemetry.addData("theta", position.getTheta().getDegrees());
        }

        telemetry.addLine(visual.findSkystone().toString());
    }

    @Override
    public void stop() {
        visual.stop();
    }
}
