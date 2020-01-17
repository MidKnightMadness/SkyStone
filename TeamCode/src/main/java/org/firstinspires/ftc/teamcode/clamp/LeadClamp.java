package org.firstinspires.ftc.teamcode.clamp;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.Config;
import org.firstinspires.ftc.teamcode.lift.ScissorLift;

public class LeadClamp extends Clamp {

    DcMotor clampmotor;
    private double CLAMP_POWER = 1;
    private int CLAMP_MIN_ENC = -1850;
    private int CLAMP_MAX_ENC = 50;
    private int clamppos = 0;
    private int[] encoderpositions = {1800, 800, 0};
    private int state = 2; // 0 = fully closed, 1 = clamping, 2 = open
    private boolean overriding = false;
    private int buttoncounter = 0;
    private String telstring = "";
    private int scissorenc;



    @Override
    public void init() {
        clampmotor = hardwareMap.dcMotor.get(Config.Clamp.CLAMP_MOTOR);
        clampmotor.resetDeviceConfigurationForOpMode();
        clampmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        clampmotor.setTargetPosition(0);
        clampmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        clampmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        clampmotor.setPower(0);
    }
    @Override
    public void init_loop() {

    }
    @Override
    public void start() {

    }

    @Override
    public void loop() { // closed: 0   full: -1800    lowered: can open to -450, close to -1000
        clamppos = clampmotor.getCurrentPosition();


        if(!overriding){
            if(gamepad1.dpad_down && buttoncounter == 0){/*
                if(state == 1){
                    state = 0;
                    clampmotor.setTargetPosition(encoderpositions[state]);
                    clampmotor.setPower(CLAMP_POWER);
                }*/
                if(state == 2){
                    state = 1;
                    clampmotor.setTargetPosition(encoderpositions[state]);
                    clampmotor.setPower(CLAMP_POWER);
                }
                buttoncounter = 15;
            }
            else if(gamepad1.dpad_up && buttoncounter == 0){
                if(state == 1){
                    state = 2;
                    clampmotor.setTargetPosition(encoderpositions[state]);
                    clampmotor.setPower(CLAMP_POWER);
                }/*
                else if(state == 0){
                    state = 1;
                    clampmotor.setTargetPosition(encoderpositions[state]);
                    clampmotor.setPower(CLAMP_POWER);
                }*/
                buttoncounter = 15;
            }
        }




        else {
            if(gamepad1.dpad_down){
                clampmotor.setPower(CLAMP_POWER);
            } else if (gamepad1.dpad_up){
                clampmotor.setPower(-CLAMP_POWER);
            } else {
                clampmotor.setPower(0);
            }
        }

        if(gamepad1.a && buttoncounter == 0){
            clampmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            clampmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            buttoncounter = 15;
        }


        if(gamepad1.b && buttoncounter == 0){
            overriding = !overriding;
            if(overriding){
                clampmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }else{
                clampmotor.setTargetPosition(clamppos);
                clampmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            buttoncounter = 15;
        }

        buttoncounter = Math.max(buttoncounter-1, 0);


        telemetry.addData("ENC ", clamppos);
        telemetry.addData("OVR ", overriding);
        telemetry.addData("C ", buttoncounter);
        telemetry.addData("STATE ", state);

        telemetry.update();

    }

    @Override
    public void closeToHalf() {
        clampmotor.setTargetPosition(-1000);
        clampmotor.setPower(CLAMP_POWER);

    }


    @Override
    public void openToHalf() {
        clampmotor.setTargetPosition(-1180);
        clampmotor.setPower(CLAMP_POWER);

    }

    @Override
    public void openToFull() {
        clampmotor.setTargetPosition(-1800);
        clampmotor.setPower(CLAMP_POWER);

    }


    @Override
    public boolean isBusy(){
        return Math.abs(clampmotor.getCurrentPosition()- clampmotor.getTargetPosition()) > 10;
    }
}
