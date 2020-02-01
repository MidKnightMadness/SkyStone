package org.firstinspires.ftc.teamcode.MainBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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

    @Override
    public void init() {
        Assembly.initialize(telemetry, hardwareMap, delivery, drive, foundationMover, grabber);
        telemetry.addLine("I AM INITIALIZED!");
        telemetry.addLine(" -- Just for Chris...");
        telemetry.update();
        
    }

    @Override
    public void loop() {
        
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
