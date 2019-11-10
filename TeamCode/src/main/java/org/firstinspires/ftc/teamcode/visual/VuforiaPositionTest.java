package org.firstinspires.ftc.teamcode.visual;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.teamcode.common.Position;

@TeleOp
public class VuforiaPositionTest extends OpMode {

    Visual visual = new VuforiaRobotNavigation();

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
            telemetry.addData("x", position.getX().toEncoderTicks());
            telemetry.addData("y", position.getY().toEncoderTicks());
            telemetry.addData("theta", position.getTheta().getDegrees());
        }
    }

    @Override
    public void stop() {
        visual.stop();
    }
}
