package org.firstinspires.ftc.teamcode.MainBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.Assembly;
import org.firstinspires.ftc.teamcode.delivery.Delivery;
import org.firstinspires.ftc.teamcode.delivery.Elevator;
import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.drive.NewMechanumWheels;
import org.firstinspires.ftc.teamcode.foundationMover.ActualFoundationMover;
import org.firstinspires.ftc.teamcode.foundationMover.FoundationMover;
import org.firstinspires.ftc.teamcode.grabber.Claw;
import org.firstinspires.ftc.teamcode.grabber.Grabber;
import org.firstinspires.ftc.teamcode.led.LED;
import org.firstinspires.ftc.teamcode.led.LEDColor;
import org.firstinspires.ftc.teamcode.led.LEDState;

import static org.firstinspires.ftc.teamcode.led.LED.Colors.BLUE;
import static org.firstinspires.ftc.teamcode.led.LED.Colors.GOLD;
import static org.firstinspires.ftc.teamcode.led.LED.Colors.GREEN;
import static org.firstinspires.ftc.teamcode.led.LED.Colors.ORANGE;
import static org.firstinspires.ftc.teamcode.led.LED.Colors.PINK;
import static org.firstinspires.ftc.teamcode.led.LED.Colors.RED;

@TeleOp
public class Main extends OpMode {

    private Delivery delivery = new Elevator();
    private Drive drive = new NewMechanumWheels();
    private FoundationMover foundationMover = new ActualFoundationMover();
    private Grabber grabber = new Claw();

    private double targetHeight;
    private double targetDepth;

    private boolean ypressed;
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

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        telemetry.addLine("------------------------------");
        telemetry.addLine("        |     |   -----   |");
        telemetry.addLine("        |----|      |     |");
        telemetry.addLine("        |     |   -----   .");
        telemetry.addLine("------------------------------");
        telemetry.addLine(" \"Please get better text art\"");
        telemetry.update();

        I2cDeviceSynch leds = hardwareMap.get(I2cDeviceSynch.class, "ledstrip");
        LED.init(leds);
        LED.BACK.setBrightness(0.1);
        LED.ALL.set(LED.Modes.PROGRESS, 1, BLUE, GOLD);
        LED.forceUpdate();
        Assembly.initialize(telemetry, hardwareMap, delivery, drive, foundationMover, grabber);
        LED.LOWER.set(LED.Modes.BOUNCING, BLUE, GOLD);
        //drive.setDriveMode(Drive.DRIVEMODE.RELATIVE);

        telemetry.addLine("I AM INITIALIZED!");
        telemetry.addLine(" -- Just for Chris...");
        telemetry.update();
    }

    public void init_loop() {
        LED.update();
    }

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

        delivery.setOverride(gamepad2.back);
        //delivery
        if (gamepad2.right_stick_y == 0) {
            delivery.holdDepth();
        } else {
            delivery.setDepthPower(-gamepad2.right_stick_y);
        }

        if (gamepad2.left_stick_y == 0) {
            delivery.holdHeight();
        } else {
            delivery.setHeightPower(-gamepad2.left_stick_y);
        }


        //drive
        Angle direction = Angle.aTan(gamepad1.left_stick_x, -gamepad1.left_stick_y);
        double speed = Math.hypot(gamepad1.left_stick_x, -gamepad1.left_stick_y) * (1 - gamepad1.right_trigger) * (1 - gamepad1.left_trigger) * (slow ? 0.3 : 1);
        double rotation = gamepad1.right_stick_x * (1 - gamepad1.right_trigger) * (1 - gamepad1.left_trigger) * (slow ? 0.3 : 1);
        drive.setDirection(direction, speed, rotation);
        drive.update();

        //foundation mover
        if (gamepad1.y && !ypressed) {
            ypressed = true;
        } else if (!gamepad1.y && ypressed) {
            ypressed = false;
            mode *= -1;
        }
        if (mode == 1) {
            foundationMover.reset();
        } else if (mode == -1) {
            foundationMover.grab();
        }

        if (gamepad1.a) {
            drive.resetHeading();
        }

        if (gamepad2.left_bumper) {
            delivery.setHeight(0.06);
        }

        if (gamepad2.right_bumper) {
            delivery.setHeight(0.17);
        }

        //grabber
        if (gamepad2.x && !xpressed) {
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
        } else if (!gamepad2.x && xpressed) {
            xpressed = false;
        }

        if(gamepad2.dpad_left) {
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

        if(gamepad2.dpad_right) {
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

        targetGrabberRot = grabberDefaultRotation + gamepad2.left_trigger - gamepad2.right_trigger;
        grabber.rotate(targetGrabberRot);
        grabber.update();
    }

    public void stop()
    {
        LED.ALL.set(LED.Modes.RUNNING, LED.Colors.NAVY, LED.Colors.GOLD);
        LED.forceUpdate();
    }
}
