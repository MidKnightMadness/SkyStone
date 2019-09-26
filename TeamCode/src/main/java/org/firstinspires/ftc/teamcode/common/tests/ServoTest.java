package org.firstinspires.ftc.teamcode.common.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;

//@Autonomous
public class ServoTest extends OpMode {
    CRServo servo;
    @Override
    public void init() {
        servo = hardwareMap.crservo.get("servo");
    }

    @Override
    public void start() {
        servo.setPower(1);
        telemetry.addLine("running?");
        telemetry.update();
    }

    @Override
    public void loop() {

    }
}
