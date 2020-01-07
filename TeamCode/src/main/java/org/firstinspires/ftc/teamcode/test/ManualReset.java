package org.firstinspires.ftc.teamcode.test;

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
public class ManualReset extends OpMode {

    private Delivery delivery = new Elevator();
    private Drive drive = new NewMechanumWheels();
    private FoundationMover foundationMover = new ActualFoundationMover();
    private Grabber grabber = new Claw();
    
    private double targetHeight;
    private double targetDepth;

    private boolean ypressed;
    private int mode;

    private boolean xpressed;
    private boolean isGrabbed;
    private double targetGrabberRot;
    
      @Override
    public void init() {
        Assembly.initialize(telemetry, hardwareMap, delivery, drive, foundationMover, grabber);
    }

    @Override
    public void loop() {
        //delivery
        targetDepth += gamepad1.dpad_right ? 0.001 : 0;
        targetDepth -= gamepad1.dpad_left ? 0.001 : 0;
        targetHeight += gamepad1.dpad_up ? 0.01 : 0;
        targetHeight -= gamepad1.dpad_down ? 0.01 : 0;
        delivery.setDepth(targetDepth);
        delivery.setHeight(targetHeight);
        telemetry.addData("targetHeight", targetHeight);
        telemetry.addData("targetDepth", targetDepth);

        //drive
        Angle direction = Angle.aTan(gamepad1.left_stick_x, -gamepad1.left_stick_y);
        double speed = Math.hypot(gamepad1.left_stick_x, -gamepad1.left_stick_y) * (gamepad1.b ? 0.2 : 1);
        double rotation = gamepad1.right_stick_x;
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
        targetGrabberRot = gamepad1.right_trigger - gamepad1.left_trigger;
        grabber.rotate(((targetGrabberRot / 1.4) + 1) / 2);
        grabber.update();
    }
}