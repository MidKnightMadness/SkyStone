package org.firstinspires.ftc.teamcode.clamp;

import com.qualcomm.robotcore.hardware.DcMotor;

public class LeadClamp extends Clamp {

    DcMotor motor;
    public double CLAMP_POWER = 0.1;


    @Override
    public void init() {
        motor = hardwareMap.dcMotor.get("clamp");
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
        if(gamepad1.left_trigger>0){
            motor.setPower(CLAMP_POWER);
        } else if (gamepad1.right_trigger>0){
            motor.setPower(-CLAMP_POWER);
        } else {
            motor.setPower(0);
        }
        telemetry.addLine(String.valueOf(motor.getCurrentPosition()));
        telemetry.update();


    }
}
