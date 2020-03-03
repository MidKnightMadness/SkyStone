package org.firstinspires.ftc.teamcode.MainBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.Assembly;
import org.firstinspires.ftc.teamcode.delivery.Delivery;
import org.firstinspires.ftc.teamcode.delivery.Elevator;
import org.firstinspires.ftc.teamcode.foundationMover.ActualFoundationMover;
import org.firstinspires.ftc.teamcode.foundationMover.FoundationMover;
import org.firstinspires.ftc.teamcode.grabber.Claw;
import org.firstinspires.ftc.teamcode.grabber.Grabber;
import org.firstinspires.ftc.teamcode.led.LED;
import org.firstinspires.ftc.teamcode.led.LEDState;

import static org.firstinspires.ftc.teamcode.led.LED.Colors.BLUE;
import static org.firstinspires.ftc.teamcode.led.LED.Colors.GOLD;
import static org.firstinspires.ftc.teamcode.led.LED.Colors.GREEN;

@TeleOp
public class Judging extends OpMode {

    private Delivery delivery = new Elevator();
    private FoundationMover foundationMover = new ActualFoundationMover();
    private Grabber grabber = new Claw();

    private double targetHeight;
    private double targetDepth;

    private boolean apressed;
    private int mode = 1;

    private boolean xpressed;
    private boolean isGrabbed;
    private double targetGrabberRot;
    private int grabberDefaultRotation;
    private boolean dlpressed;
    private boolean drpressed;
    private boolean ddpressed;
    private boolean dupressed;
    private boolean slow;

    private int doGrab;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        telemetry.addLine("------------------------------");
        telemetry.addLine("        |     |   -----   |");
        telemetry.addLine("        |----|      |     |");
        telemetry.addLine("        |     |   -----   .");
        telemetry.addLine("------------------------------");
        telemetry.addLine(" \"Please get better text art\"");

        LED.ALL.set(LED.Modes.PROGRESS, 1, BLUE, GOLD);
        LED.forceUpdate();
        I2cDeviceSynch leds = hardwareMap.get(I2cDeviceSynch.class, "ledstrip");
        LED.init(leds);
        LED.BACK.setBrightness(0.1);
        Assembly.initialize(telemetry, hardwareMap, delivery, foundationMover, grabber);
        LED.LOWER.set(LED.Modes.BOUNCING, BLUE, GOLD);
        //drive.setDriveMode(Drive.DRIVEMODE.RELATIVE);

        telemetry.addLine("I AM INITIALIZED!");
        telemetry.addLine(" -- Just for Chris...");
        telemetry.update();
    }

    //public void init_loop() {
    //    LED.update();
    //}

    public void start()
    {
        LED.ALL.set(LED.Modes.STATIC, GREEN);
        runtime.reset();
    }

    @Override
    public void loop() {
        //LEDs
        LEDState.update(slow,isGrabbed,runtime.seconds());
        LED.update();

        if (gamepad1.dpad_down && !ddpressed) {
            ddpressed = true;
        } else if (!gamepad1.dpad_down && ddpressed) {
            ddpressed = false;
            slow = !slow;
        }

        delivery.setOverride(gamepad1.back);
        //delivery
        if (gamepad1.right_stick_y == 0) {
            delivery.holdDepth();
        } else {
            delivery.setDepthPower(-gamepad1.right_stick_y);
        }

        if(doGrab == 0)
            if (gamepad1.left_stick_y == 0) {
                delivery.holdHeight();
            } else {
                delivery.setHeightPower(-gamepad1.left_stick_y);
            }

        //foundation mover
        if (gamepad1.a && !apressed) {
            apressed = true;
        } else if (!gamepad1.a && apressed) {
            apressed = false;
            mode *= -1;
        }
        if (mode == 1) {
            foundationMover.reset();
        } else if (mode == -1) {
            foundationMover.grab();
        }

        if (gamepad1.left_bumper) {
            delivery.setHeight(0.0480);
        }

        if (gamepad1.right_bumper) {
            delivery.setHeight(0.0335);
        }

        //grabber
        if (gamepad1.x && !xpressed) {
            xpressed = true;
            if(isGrabbed)
            {
                grabber.release();
                isGrabbed = false;
            }
            else
            {
                grabber.grab();
                isGrabbed = true;
            }
        } else if (!gamepad1.x && xpressed) {
            xpressed = false;
        }

        //y button grab
        if(gamepad1.y)
            doGrab = 3;
        if(!gamepad1.atRest())
            doGrab = 0;
        switch (doGrab)
        {
            case 3:
                delivery.setHeightPower(-1);

                if(delivery.getHeight() < 1000)
                    grabber.grab();

                if(delivery.isDown())
                {
                    doGrab = 2;
                    delivery.holdHeight();
                }
                break;
            case 2:
                grabber.grab();
                isGrabbed = true;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) { }
                delivery.setHeight(0.0480);
                doGrab = 1;
                break;
            case 1:
                if(delivery.isComplete())
                    doGrab = 0;
        }
        telemetry.addData("doGrab", doGrab);

        if(gamepad1.dpad_left) {
            if (!dlpressed)
            {
                dlpressed = true;
                if (grabberDefaultRotation == -1)
                    grabberDefaultRotation = 0;
                else
                    grabberDefaultRotation = -1;
            }
        } else
            dlpressed = false;

        if(gamepad1.dpad_right) {
            if (!drpressed)
            {
                drpressed = true;
                if (grabberDefaultRotation == 1)
                    grabberDefaultRotation = 0;
                else
                    grabberDefaultRotation = 1;
            }
        } else
            drpressed = false;

        targetGrabberRot = grabberDefaultRotation + gamepad1.left_trigger - gamepad1.right_trigger;
        grabber.rotate(targetGrabberRot);
        telemetry.addData("targetrotateposition", targetGrabberRot);
        grabber.update();
    }

    public void stop()
    {
        LED.ALL.set(LED.Modes.RUNNING, LED.Colors.NAVY, LED.Colors.GOLD);
        LED.forceUpdate();
    }
}
