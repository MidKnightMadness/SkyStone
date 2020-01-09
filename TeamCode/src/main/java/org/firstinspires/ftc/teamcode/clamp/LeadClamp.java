package org.firstinspires.ftc.teamcode.clamp;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.Config;

public class LeadClamp extends Clamp {

    DcMotor motor;
    private double CLAMP_POWER = 1;
    private double CLAMP_MIN_ENC = -1850;
    private double CLAMP_MAX_ENC = 50;
    private double clamppos = 0;
    private int[] encoderpositions = {0, -1000, -1800};
    private int state = 0; // 0 = fully closed, 1 = clamping, 2 = open
    private boolean overriding = false;
    private int buttoncounter = 0;



    @Override
    public void init() {
        motor = hardwareMap.dcMotor.get(Config.Clamp.CLAMP_MOTOR);
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
    public void loop() { // closed: 0   full: -1800    lowered: can open to -450, close to -1000
        clamppos = motor.getCurrentPosition();/*

        if(gamepad1.dpad_down && scissorpos < CLAMP_MAX_ENC){
            motor.setPower(CLAMP_POWER);
        } else if (gamepad1.dpad_up && scissorpos > CLAMP_MIN_ENC){
            motor.setPower(-CLAMP_POWER);
        } else {
            motor.setPower(0);
        }*/
        if(!overriding){
            if(gamepad1.dpad_down && buttoncounter == 0){
                if(state == 1){
                    state = 0;
                    motor.setTargetPosition(encoderpositions[state]);
                    motor.setPower(CLAMP_POWER);
                }
                else if(state == 2){
                    state = 1;
                    motor.setTargetPosition(encoderpositions[state]);
                    motor.setPower(CLAMP_POWER);
                }
                buttoncounter = 60;
            }
            else if(gamepad1.dpad_up && buttoncounter == 0){
                if(state == 1){
                    state = 2;
                    motor.setTargetPosition(encoderpositions[state]);
                    motor.setPower(CLAMP_POWER);
                }
                else if(state == 0){
                    state = 1;
                    motor.setTargetPosition(encoderpositions[state]);
                    motor.setPower(CLAMP_POWER);
                }
                buttoncounter = 60;
            }
        } else {
            if(gamepad1.dpad_down){
                motor.setPower(CLAMP_POWER);
            } else if (gamepad1.dpad_up){
                motor.setPower(-CLAMP_POWER);
            } else {
                motor.setPower(0);
            }
        }




        if(gamepad1.b && buttoncounter == 0){
            overriding = !overriding;
            buttoncounter = 30;
        }
        buttoncounter = Math.max(buttoncounter-1, 0);


        telemetry.addData("ENC ", clamppos);
        telemetry.addData("OVR ", overriding);
        telemetry.addData("C ", buttoncounter);
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
