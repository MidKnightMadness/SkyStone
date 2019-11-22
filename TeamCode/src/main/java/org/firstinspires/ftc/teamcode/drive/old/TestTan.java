package org.firstinspires.ftc.teamcode.drive.old;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Angle;

//@TeleOp
public class TestTan extends OpMode {
    @Override
    public void init() {

    }

    @Override
    public void loop() {
        //angle goes from 0 at top to -180 at bottom then 180 to 0;
        double angle = Angle.aTan(gamepad1.left_stick_x, -gamepad1.left_stick_y).getDegrees();

        telemetry.addData("angle: ", angle);

        //convert weird angle measures to normal circle angle measures
        //0 far right around to 360
        double theta = angle;
        if (theta >= 90 && theta <= 180){
            theta = 270 + (180-theta);
        }
        else{
            theta = 90 - theta;
        }
        telemetry.addData("theta: ", theta);

    }
}
