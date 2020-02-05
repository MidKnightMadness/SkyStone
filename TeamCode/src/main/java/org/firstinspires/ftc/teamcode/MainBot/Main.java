package org.firstinspires.ftc.teamcode.MainBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

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

import static org.firstinspires.ftc.teamcode.led.LED.Colors.BLUE;
import static org.firstinspires.ftc.teamcode.led.LED.Colors.GREEN;
import static org.firstinspires.ftc.teamcode.led.LED.Colors.PINK;

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

    private int progress = 3;
    private boolean y2Down;

    @Override
    public void init() {
        Assembly.initialize(telemetry, hardwareMap, delivery, drive, foundationMover, grabber);
        I2cDeviceSynch leds = hardwareMap.get(I2cDeviceSynch.class, "ledstrip");
        LED.init(leds);
        LED.LOWER.set(LED.Modes.BOUNCING, BLUE, PINK);
        telemetry.addLine("I AM INITIALIZED!");
        telemetry.addLine(" -- Just for Chris...");
        telemetry.update();
    }

    public void init_loop() {
        LED.update();
    }

    public void start()
    {
        LED.ALL.set(LED.Modes.PROGRESS, progress, BLUE, GREEN);
    }

    @Override
    public void loop() {
        LED.update();

        if (gamepad2.y && !y2Down) {
            y2Down = true;
        } else if (!gamepad2.y && y2Down) {
            y2Down = false;
            progress++;
            LED.ALL.setParam(progress);
        }
        
        delivery.setOverride(gamepad2.b);
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
        double speed = Math.hypot(gamepad1.left_stick_x, -gamepad1.left_stick_y) * (1 - gamepad1.right_trigger) * (1 - gamepad1.left_trigger);
        double rotation = gamepad1.right_stick_x * (1 - gamepad1.right_trigger) * (1 - gamepad1.left_trigger);
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
            delivery.setHeight(0.04);
        }
        
        if (gamepad2.right_bumper) {
            delivery.setHeight(0);
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
        
        targetGrabberRot = gamepad2.left_trigger - gamepad2.right_trigger;
        grabber.rotate(((targetGrabberRot / 1.4) + 1) / 2);
        grabber.update();
    }
}
