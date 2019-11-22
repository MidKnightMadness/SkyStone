package org.firstinspires.ftc.teamcode.visual;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

//@TeleOp
public class StonePositionTest extends OpMode {

    private Servo clawservo;
    private Servo rotateservo;
    private boolean yPressed;
    private boolean xPressed;
    private StoneAngle stoneAngle = new StoneAngle();


    @Override

    public void init() {
        clawservo = hardwareMap.servo.get("servo");
        rotateservo = hardwareMap.servo.get("rotateservo");
        stoneAngle.hardwareMap = hardwareMap;
        stoneAngle.init();
    }

    @Override
    public void loop() {
        rotateservo.setPosition(stoneAngle.stonePosition()[1] / -180 + 0.5);


    }
}
