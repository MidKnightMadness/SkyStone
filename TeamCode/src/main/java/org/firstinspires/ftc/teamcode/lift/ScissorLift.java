package org.firstinspires.ftc.teamcode.lift;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.Config;


public class ScissorLift extends Lift {

    DcMotor motor;
    public double SCISSOR_POWER = 1;
    public double SCISSOR_MIN_ENC = -20;
    public double SCISSOR_MAX_ENC = 3900;
    private boolean overriding = false;
    public int scissorpos = 0;
    private int lastStoppedPos = 0;
    private double tp = 0;
    private boolean t = false;

    @Override
    public void init() {
        motor = hardwareMap.dcMotor.get(Config.Lift.LIFT_MOTOR);
        motor.resetDeviceConfigurationForOpMode();
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setTargetPosition(0);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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
        scissorpos = motor.getCurrentPosition();
        tp = (double) Math.max(gamepad1.left_trigger, gamepad1.right_trigger);
        tp = SCISSOR_POWER*tp*tp;
        if(gamepad1.right_trigger>gamepad1.left_trigger) {
            tp = -tp;
        }

        if(tp != 0){
            lastStoppedPos = scissorpos;
        }

        if(!overriding){
            if(scissorpos > SCISSOR_MAX_ENC && tp > 0){
                tp = 0;
            }
        }


        motor.setPower(tp);
        if(tp != 0){
            motor.setTargetPosition((int) (scissorpos +tp*200));
            motor.setPower(tp);
            t = false;
        }else{
            motor.setTargetPosition((int) (lastStoppedPos));
            if(lastStoppedPos- scissorpos >5){
                tp=0.4;
                t = true;

            }
            motor.setPower(tp);
        }

        if(gamepad1.a){
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
        if(gamepad1.b){
            overriding = !overriding;
        }
/*
        telemetry.addData("ENC: ", scissorpos);
        telemetry.addData("LSP: ", lastStoppedPos);
        telemetry.addData("PWR: ", tp);
        telemetry.addData("BLW: ", t);
        telemetry.update();*/



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