package org.firstinspires.ftc.teamcode.delivery;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class HorizontalTest extends OpMode {

    private DcMotor motor;
    private int targetPosition;
    private int initPosition;

    @Override
    public void init() {
        motor = hardwareMap.dcMotor.get("horizontal");

        initPosition = motor.getCurrentPosition();

        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor.setTargetPosition(0);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void init_loop() {
        telemetry.addData("current position", motor.getCurrentPosition());
        telemetry.update();
    }

    @Override
    public void loop() {
        targetPosition += gamepad1.left_stick_x * 100;
        motor.setTargetPosition(targetPosition + initPosition);

        //software limit
        if (targetPosition > 6000)
            targetPosition = 6000;
        else if (targetPosition < -6000)
            targetPosition = -6000;

        //enable and disable
        if (gamepad1.b)
            motor.setPower(0);
        else if (gamepad1.a)
            motor.setPower(1);

        telemetry.addData("initial position", initPosition);
        telemetry.addData("target position", targetPosition);
        telemetry.addData("current position", motor.getCurrentPosition());
        telemetry.addData("power", motor.getPower());
        telemetry.update();
    }
}
