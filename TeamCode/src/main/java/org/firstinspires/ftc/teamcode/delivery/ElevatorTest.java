package org.firstinspires.ftc.teamcode.delivery;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//@TeleOp
public class ElevatorTest extends OpMode {

    Delivery elevator = new Elevator();

    @Override
    public void init() {
        elevator.hardwareMap = hardwareMap;
        elevator.init();
    }

    @Override
    public void loop() {
        elevator.setDepth(gamepad1.left_stick_x);
        elevator.setHeight(gamepad1.left_stick_y);
    }
}
