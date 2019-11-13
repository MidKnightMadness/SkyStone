package org.firstinspires.ftc.teamcode.lift;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
public class ScissorLift extends Lift {

    DcMotor motor;
    public double SCISSOR_POWER = 0.1;
    public double SCISSOR_MIN_ENC = -10000000;
    public double SCISSOR_MAX_ENC = 10000000;
    private int pos = 0;

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
    public void init_loop() {

    }
    @Override
    public void start() {

    }

    @Override
    public void loop() {
        pos = motor.getCurrentPosition();
        if(gamepad1.left_trigger>0 && pos < SCISSOR_MAX_ENC){
            motor.setPower(SCISSOR_POWER);
        } else if (gamepad1.right_trigger>0 && pos > SCISSOR_MIN_ENC){
            motor.setPower(-SCISSOR_POWER);
        } else {
            motor.setPower(0);
        }
        telemetry.addLine(String.valueOf(pos));
        telemetry.update();


    }
}