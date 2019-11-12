package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Angle;

@TeleOp
public class NewMechanumWheelsTest extends OpMode {
    NewMechanumWheels drive = new NewMechanumWheels();

    @Override
    public void init() {
        drive.telemetry = telemetry;
        drive.hardwareMap = hardwareMap;
        drive.init();
    }

    @Override
    public void loop() {
        Angle direction = Angle.aTan(gamepad1.left_stick_x, -gamepad1.left_stick_y);
        double speed = Math.hypot(gamepad1.left_stick_x, -gamepad1.left_stick_y);
        double rotation = gamepad1.right_stick_x;

        drive.setDirection(direction, speed, rotation);
        drive.update();

    }
}
