package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class CubeSideAcross extends BaseAutonomous {
    int turn360 = 14000;
    int start;

    public void runOpMode() {

        initialize();

        holdMarker();

        waitForStart();

        start = winchMotor.getCurrentPosition();

        // Lower bot
        setHeight(start + 17500, 1);
        while (winchMotor.isBusy() && !isStopRequested()) {}

        moveEncoderVertical(800, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        // Move to minerals
        moveEncoderHorizontal(-800, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        turnBotEncoders(turn360 / -72 * 10, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        moveEncoderVertical(3500, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}
        sleep(100);

        turnBotEncoders(turn360 / 72 * 10, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        int startingEncoder = frontLeft.getCurrentPosition();
        telemetry.addData("Starting Pos", frontLeft.getCurrentPosition());
        telemetry.update();

        // Scan minerals
        boolean seeMineral = false;

        moveEncoderVertical(-5500, 1);
        while (frontLeft.isBusy()) {
            if (seesGold()) {
                seeMineral = true;
                telemetry.addData("End", frontLeft.getCurrentPosition());
                telemetry.update();
                break;
            }
        }

        int endEncoder = frontLeft.getCurrentPosition();
        moveEncoderVertical(-400, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        int dir = 0;

        if (seeMineral) {
            if (Math.abs(startingEncoder - endEncoder) <= 1000) {
                dir = 1;

                telemetry.addData("Diff:", Math.abs(startingEncoder - endEncoder));
                telemetry.addLine("Left");
                telemetry.update();

                moveEncoderVertical(1600, 1);
                while (frontLeft.isBusy() && !isStopRequested()) {
                }

                turnBotEncoders(turn360 / 16 * 3, 1);
                while (frontLeft.isBusy() && !isStopRequested()) {
                }
            } else if (Math.abs(startingEncoder - endEncoder) >= 2550) {
                dir = 3;
                telemetry.addData("Diff:", Math.abs(startingEncoder - endEncoder));
                telemetry.addLine("Right");
                telemetry.update();

                moveEncoderVertical(-500, 1);
                while (frontLeft.isBusy() && !isStopRequested()) {}

                turnBotEncoders(turn360 / 10 * 3, 1);
                while (frontLeft.isBusy() && !isStopRequested()) {
                }
            } else {
                dir = 2;
                telemetry.addData("Diff:", Math.abs(startingEncoder - endEncoder));
                telemetry.addLine("Center");
                telemetry.update();

                turnBotEncoders(turn360 / 4, 1);
                while (frontLeft.isBusy() && !isStopRequested()) {
                }
            }
        } else {
            turnBotEncoders(turn360 / 10 * 3, 1);
            while (frontLeft.isBusy() && !isStopRequested()) {};
        }

        // Go to depot and deposit
        if (dir != 3) {
            moveEncoderVertical(-7250, 1);
        } else {
            moveEncoderVertical(-7500, 1);
        }
        while (frontLeft.isBusy() && !isStopRequested()) {}

        dropMarker();
        sleep(250);

        moveEncoderVertical(600, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        if (dir == 1) {
            turnBotEncoders(turn360 / -4, 1);
            while (frontLeft.isBusy() && !isStopRequested()) {
            }

            moveEncoderVertical(1850, 1);
            while (frontLeft.isBusy() && !isStopRequested()) {
            }

            turnBotEncoders(turn360 / 72 * 17, 1);
            while (frontLeft.isBusy() && !isStopRequested()) {
            }
        } else if (dir == 2) {
            turnBotEncoders(turn360 / -18 * 4, 1);
            while (frontLeft.isBusy() && !isStopRequested()) {
            }

            moveEncoderVertical(4000, 1);
            while (frontLeft.isBusy() && !isStopRequested()) {
            }

            turnBotEncoders(turn360 / 10, 1);
            while (frontLeft.isBusy() && !isStopRequested()) {
            }
        } else {
            turnBotEncoders(turn360 / -4, 1);
            while (frontLeft.isBusy() && !isStopRequested()) {
            }

            moveEncoderVertical(5500, 1);
            while (frontLeft.isBusy() && !isStopRequested()) {
            }

            turnBotEncoders(turn360 / 72 * 6, 1);
            while (frontLeft.isBusy() && !isStopRequested()) {
            }
        }
        if (dir != 3) {
            moveEncoderVertical(7850, 1);

            armMotor.setPower(1);
            double waitUntil = time + 1.5;
            while (time < waitUntil && !isStopRequested()) {}

            extensionMotor.setPower(1);
            waitUntil = time + 1;
            while (time < waitUntil && !isStopRequested()) {}

            armMotor.setPower(0);
            extensionMotor.setPower(0);

            while (frontLeft.isBusy() && !isStopRequested()) {
            }
        } else {
            moveEncoderVertical(7700, 1);

            armMotor.setPower(1);
            double waitUntil = time + 1.5;
            while (time < waitUntil && !isStopRequested()) {}

            extensionMotor.setPower(1);
            waitUntil = time + 1;
            while (time < waitUntil && !isStopRequested()) {}

            armMotor.setPower(0);
            extensionMotor.setPower(0);

            while (frontLeft.isBusy() && !isStopRequested()) {}
        }
    }
}