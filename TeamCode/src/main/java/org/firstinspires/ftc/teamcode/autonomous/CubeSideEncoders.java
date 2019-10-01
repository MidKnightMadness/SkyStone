package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous
public class CubeSideEncoders extends BaseAutonomous {
    int turn360 = 14000;
    int start;

    public void runOpMode() {

        initialize();

        holdMarker();

        waitForStart();

        start = winchMotor.getCurrentPosition();

        setHeight(start + 17500, 1);
        while (winchMotor.isBusy() && !isStopRequested()) {}

        moveEncoderVertical(800, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        setHeight(start + 8000, 1);
        double waitUntil = time + 2;
        while (time < waitUntil && !isStopRequested()) {}

        moveEncoderVertical(-800, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        //turnBotEncoders(-turn360/4, 1);
        //while (frontLeft.isBusy() && !isStopRequested()) {}

        moveEncoderHorizontal(-4000, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}
        sleep(100);

        int startingEncoder = frontLeft.getCurrentPosition();
        telemetry.addData("Starting Pos", frontLeft.getCurrentPosition());
        telemetry.update();

        // Visual
        moveEncoderVertical(-3000, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        moveEncoderVertical(5500, 1);
        while (frontLeft.isBusy()) {
            if (seesGold()) {
                telemetry.addData("End", frontLeft.getCurrentPosition());
                telemetry.update();
                break;
            }
        }

        int endEncoder = frontLeft.getCurrentPosition();
        moveEncoderVertical(400, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        if (startingEncoder - endEncoder >= 1650) {
            telemetry.addLine("Left");
            telemetry.update();

            moveEncoderVertical(1300, 1);
            while (frontLeft.isBusy() && !isStopRequested()) {
            }

            turnBotEncoders(turn360 / 16 * 3, 1);
            while (frontLeft.isBusy() && !isStopRequested()) {
            }
        } else if (endEncoder - startingEncoder >= 1000) {
            telemetry.addLine("Right");
            telemetry.update();
            moveEncoderVertical(-1300, 1);
            while (frontLeft.isBusy() && !isStopRequested()) {}

            turnBotEncoders(turn360 / 10 * 3, 1);
            while (frontLeft.isBusy() && !isStopRequested()) {}

            moveEncoderVertical(-500, 1);
            while (frontLeft.isBusy() && !isStopRequested()) {}
        } else {
            telemetry.addLine("Center");
            telemetry.update();

            turnBotEncoders(turn360 / 4, 1);
            while (frontLeft.isBusy() && !isStopRequested()) {}
        }

        moveEncoderVertical(-7000, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}
        sleep(500);

        dropMarker();
        sleep(500);

        moveEncoderVertical(900, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}
        sleep(500);
    }
}