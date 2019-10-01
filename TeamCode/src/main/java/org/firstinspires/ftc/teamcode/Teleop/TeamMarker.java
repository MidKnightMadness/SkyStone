package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Config;

@TeleOp
public class TeamMarker extends OpMode {
    private Servo servo;

    public void init() {
        servo = hardwareMap.servo.get(Config.Marker.MARKER_SERVO);
        servo.setPosition(0);
    }

    public void loop() {

    }
}
