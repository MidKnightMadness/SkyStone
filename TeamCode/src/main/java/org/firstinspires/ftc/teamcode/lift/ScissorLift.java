package org.firstinspires.ftc.teamcode.lift;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class ScissorLift extends Lift {

    DcMotor motor;
    public double SCISSOR_POWER = 0.1;

    @Override
    public void init() {
        motor = hardwareMap.dcMotor.get("scissor");
        motor.resetDeviceConfigurationForOpMode();
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setPower(0);
    }

    @Override
    public void loop() {
        if(gamepad1.left_trigger>0){
            motor.setPower(SCISSOR_POWER);
        } else if (gamepad1.right_trigger>0){
            motor.setPower(-SCISSOR_POWER);
        } else {
            motor.setPower(0);
        }
        telemetry.addLine(String.valueOf(motor.getCurrentPosition()));
        telemetry.update();


    }
}