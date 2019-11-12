package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Angle;
@TeleOp
public class ChassisDriveTest extends OpMode {
    private MecanumWheels mechWheel = new MecanumWheels();
    private double speed;
    private double offset;

    @Override
    public void init() {
        mechWheel.hardwareMap = hardwareMap;
        mechWheel.telemetry = telemetry;
        mechWheel.init();
        speed = 0;
        offset = 0;

    }

    @Override
    public void loop() {
        double angle = Angle.aTan(gamepad1.left_stick_x, -gamepad1.left_stick_y).getDegrees();
        speed = Math.sqrt(gamepad1.left_stick_x*gamepad1.left_stick_x + gamepad1.left_stick_y*gamepad1.left_stick_y);
        mechWheel.setPower(angle, speed, gamepad1.right_stick_x);

        telemetry.addData("Angle: ", angle);
        telemetry.addData("Speed", speed);
        telemetry.addData("Rotation", gamepad1.right_stick_x);
        telemetry.addData("BL: ", mechWheel.powerBL);
        telemetry.addData("BR: ", mechWheel.powerBR);
        telemetry.addData("FL: ", mechWheel.powerFL);
        telemetry.addData("FR: ", mechWheel.powerFR);
    }
}
