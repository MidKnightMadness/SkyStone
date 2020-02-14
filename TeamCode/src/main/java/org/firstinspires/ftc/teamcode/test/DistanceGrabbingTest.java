package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Assembly;
import org.firstinspires.ftc.teamcode.delivery.Delivery;
import org.firstinspires.ftc.teamcode.delivery.Elevator;
import org.firstinspires.ftc.teamcode.visual.StoneAngle;

@TeleOp
public class DistanceGrabbingTest extends OpMode {
    StoneAngle stoneAngle = new StoneAngle();
    Delivery delivery = new Elevator();

    @Override
    public void init() {
        Assembly.initialize(telemetry, hardwareMap, stoneAngle, delivery);
    }

    public void start() {
        delivery.setHeightRaw(3000);
        while (!delivery.isComplete());
        //delivery.setDepthRaw(-2600);
        //while (!delivery.isComplete());
        //delivery.setHeightRaw(0);
    }

    @Override
    public void loop() {
        delivery.setDepthRaw(Math.max(-2650 - (int)(180 * stoneAngle.stonePosition()[0]), -4000));
    }
}
