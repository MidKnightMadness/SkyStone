/*
 * TODO: 1. Determine which directions motors are facing (configuration)
 *       2. Adjust values to fit the configuration
 */

package org.firstinspires.ftc.teamcode.teleop;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.common.Config;

@TeleOp
public class SIdeDrive extends JoystickDrive {

    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;

    public SIdeDrive() {
    }

    @Override
    public void init() {
        fl = hardwareMap.dcMotor.get(Config.Drive.FRONT_LEFT);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        fr = hardwareMap.dcMotor.get(Config.Drive.FRONT_RIGHT);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        bl = hardwareMap.dcMotor.get(Config.Drive.BACK_LEFT);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        br = hardwareMap.dcMotor.get(Config.Drive.BACK_RIGHT);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RobotLog.a("STARTING!\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        Log.d("STARTING!!!", "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

    }



    private double pwr = 0.6;

    @Override
    /*
     * Left joystick controls transitioning motion
     * Right joystick controls the face/rotation
     */
    public void loop() {
        //1 Nest if else statements for each joystick
        //Rotational joystick will take priority by being placed by being placed as last if statement


        /*******************************************************************
         * All values assumes that all motor's positive powers are forward
         * *****************************************************************
         */




        if(gamepad1.right_stick_x!=0){
            fl.setPower( -pwr);
            fr.setPower( -pwr);
            bl.setPower(pwr);
            br.setPower(pwr);
        } else if (gamepad1.right_stick_y!=0){
            fl.setPower(-pwr);
            fr.setPower( pwr);
            bl.setPower(-pwr);
            br.setPower( pwr);
        } else if (gamepad1.left_stick_x!=0){
            fl.setPower(-pwr);
            fr.setPower(pwr);
            bl.setPower(pwr);
            br.setPower(-pwr);
        } else if (gamepad1.left_stick_y!=0){
            fl.setPower(pwr);
            fr.setPower(-pwr);
            bl.setPower(-pwr);
            br.setPower(pwr);
        } else if (gamepad1.dpad_up){
            fl.setPower(pwr);
            fr.setPower(-pwr);
            bl.setPower(pwr);
            br.setPower(-pwr);
        } else if (gamepad1.dpad_down){
            fl.setPower(pwr);
            fr.setPower(pwr);
            bl.setPower(-pwr);
            br.setPower(-pwr);
        } else if (gamepad1.dpad_left){
            fl.setPower(pwr);
            fr.setPower(pwr);
            bl.setPower(pwr);
            br.setPower(pwr);
        } else if (gamepad1.dpad_right){
            fl.setPower(pwr);
            fr.setPower(pwr);
            bl.setPower(pwr);
            br.setPower(pwr);
        }
        else{
            fl.setPower(0);
            fr.setPower(0);
            bl.setPower(0);
            br.setPower(0);
        }

    }
}
