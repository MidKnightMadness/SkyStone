package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.common.Angle;

public class ChassisDriveTest extends OpMode {
    private MecanumWheels mechWheel = new MecanumWheels();
    private double speed;
    private double offset;

    @Override
    public void init() {
        mechWheel.hardwareMap = hardwareMap;
        mechWheel.init();
        speed = 0;
        offset = 0;

    }

    @Override
    public void loop() {

        speed = Math.sqrt(gamepad1.left_stick_x*gamepad1.left_stick_x + gamepad1.left_stick_y*gamepad1.left_stick_y);
        mechWheel.setPower(Angle.aTan(gamepad1.left_stick_x, gamepad1.left_stick_y).toDegrees(), speed, gamepad1.right_stick_x);



    }
}
