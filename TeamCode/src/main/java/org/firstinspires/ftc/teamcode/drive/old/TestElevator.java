package org.firstinspires.ftc.teamcode.drive.old;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

//@TeleOp
public class TestElevator extends OpMode {
    private DcMotor motor;
    @Override
    public void init() {
        motor = hardwareMap.dcMotor.get("testmotor");
        motor.resetDeviceConfigurationForOpMode();
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        motor.setPower(-gamepad1.left_stick_y);
        telemetry.addData("Encoder", motor.getCurrentPosition());

    }
}
