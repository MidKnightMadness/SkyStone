package org.firstinspires.ftc.teamcode.clamp;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.Config;

public class LeadClamp extends Clamp {

    DcMotor motor;
    private double CLAMP_POWER = 1;
    private double CLAMP_MIN_ENC = -350000;
    private double CLAMP_MAX_ENC = 350000;
    private double pos = 0;


    @Override
    public void init() {
        motor = hardwareMap.dcMotor.get(Config.Clamp.CLAMP_MOTOR);
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

        if(gamepad1.dpad_down && pos < CLAMP_MAX_ENC){
            motor.setPower(CLAMP_POWER);
        } else if (gamepad1.dpad_up && pos > CLAMP_MIN_ENC){
            motor.setPower(-CLAMP_POWER);
        } else {
            motor.setPower(0);
        }
        telemetry.addData("ENC: ", String.valueOf(pos));
        telemetry.update();

    }

    @Override
    public void closeToHalf() {
        motor.setPower(0.2);
    }

    @Override
    public void closeToClosed() {
        motor.setPower(0.2);

    }

    @Override
    public void openToHalf() {
        motor.setPower(-0.2);

    }

    @Override
    public void openToFull() {
        motor.setPower(-0.2);

    }

    @Override
    public void stopMoving() {
        motor.setPower(0);

    }

    @Override
    public boolean isBusy(){
        return Math.abs(motor.getCurrentPosition()- motor.getTargetPosition()) > 10;
    }
}
