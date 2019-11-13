package org.firstinspires.ftc.teamcode.clamp;

import com.qualcomm.robotcore.hardware.DcMotor;

public class LeadClamp extends Clamp {

    DcMotor motor;
    private double CLAMP_POWER = 0.1;
    private double CLAMP_MIN_ENC = -10000000;
    private double CLAMP_MAX_ENC = 10000000;
    private double pos = 0;


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
        pos = motor.getCurrentPosition();

        if(gamepad1.left_trigger>0 && pos < CLAMP_MAX_ENC){
            motor.setPower(CLAMP_POWER);
        } else if (gamepad1.right_trigger>0 && pos > CLAMP_MIN_ENC){
            motor.setPower(-CLAMP_POWER);
        } else {
            motor.setPower(0);
        }
        telemetry.addLine(String.valueOf(pos));
        telemetry.update();


    }
}
