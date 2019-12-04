package org.firstinspires.ftc.teamcode.lift;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.Config;


public class ScissorLift extends Lift {

    DcMotor motor;
    public double SCISSOR_POWER = 1;
    public double SCISSOR_MIN_ENC = -20;
    public double SCISSOR_MAX_ENC = 3900;
    private boolean overriding = false;
    private int pos = 0;
    private double tp = 0;

    @Override
    public void init() {
        motor = hardwareMap.dcMotor.get(Config.Lift.LIFT_MOTOR);
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
        tp = (double) Math.max(gamepad1.left_trigger, gamepad1.right_trigger);
        tp = SCISSOR_POWER*tp*tp;
        if(gamepad1.right_trigger>gamepad1.left_trigger) {
            tp = -tp;
        }
        if(tp == 0){
            tp = 0.01;
        }
        if(!overriding){
            if(pos > SCISSOR_MAX_ENC && tp > 0){
                tp = 0;
            }
        }
        motor.setPower(tp);

        if(gamepad1.a){
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        }
        if(gamepad1.b){
            overriding = !overriding;
        }



    }

    @Override
    public void raiseLift(int enc) {
        motor.setTargetPosition(enc);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(Math.abs(enc-motor.getCurrentPosition())>10){
            motor.setPower(0.2);
        }
        motor.setPower(0);
    }

    @Override
    public void lowerLift(int enc) {
        motor.setTargetPosition(enc);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(Math.abs(enc-motor.getCurrentPosition())>10){
            motor.setPower(-0.2);
        }
        motor.setPower(0);
    }

    @Override
    public boolean isBusy(){
        return Math.abs(motor.getCurrentPosition()- motor.getTargetPosition()) > 10;
    }
}