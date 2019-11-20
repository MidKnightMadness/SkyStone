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
public class MecanumJoystickDrive extends JoystickDrive {

    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;

    public MecanumJoystickDrive() {
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


    private double[][] abs = new double[3][2];
    private int max_abs = 0;
    private boolean slow = false;

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


        //For translation movement, the largest magnitude of of x and y will be chosen for movement
        //If x value = y value, the program will favor in changing the y movement
        //Translational movement (left joystick)
        abs[0][0] = Math.abs(gamepad1.left_stick_x);
        abs[1][0] = Math.abs(gamepad1.left_stick_y);
        abs[2][0] = Math.abs(gamepad1.right_stick_x);
        abs[0][1] = abs[0][0]/gamepad1.left_stick_x;
        abs[1][1] = abs[1][0]/gamepad1.left_stick_y;
        abs[2][1] = abs[2][0]/gamepad1.right_stick_x;

        max_abs = 0;
        for(int i = 0; i < 3; i++){
            if(abs[i][0]<0.02){
                abs[i][0]=0.0;
            } else {
                if(abs[i][0]>abs[max_abs][0]){
                    max_abs=i;
                }
            }
        }

        if(abs[max_abs][0]==0){
            max_abs = -1;
        }
        if(gamepad1.left_stick_button){
            slow = false;
        } else {
            slow = true;
        }

        //telemetry.addData("X", abs[0][0]);
        //telemetry.addData("Y", abs[1][0]);
        //telemetry.addData("R", abs[2][0]);
        //telemetry.addData("M", max_abs);
        //telemetry.addData("S", slow);
        //telemetry.addData("FL", fl.getCurrentPosition());
        //telemetry.addData("FR", fr.getCurrentPosition());
        //telemetry.addData("BL", bl.getCurrentPosition());
        //telemetry.addData("BR", br.getCurrentPosition());
        //telemetry.update();


        if (slow && max_abs != -1){
            abs[max_abs][0] = (abs[max_abs][0])*(abs[max_abs][0]);
        }

        //-1 = not moving
        //0 = horizontal movement
        //1 = moving horizontally
        //2 = rotating
        if (max_abs == -1){
            fl.setPower(0);
            fr.setPower(0);
            bl.setPower(0);
            br.setPower(0);
        } else if(max_abs == 0){
            fl.setPower(gamepad1.left_stick_x);
            fr.setPower(gamepad1.left_stick_x);
            bl.setPower(-gamepad1.left_stick_x);
            br.setPower(-gamepad1.left_stick_x);
        } else if (max_abs == 1){
            fl.setPower(-gamepad1.left_stick_y);
            fr.setPower(gamepad1.left_stick_y);
            bl.setPower(-gamepad1.left_stick_y);
            br.setPower(gamepad1.left_stick_y);
        } else if (max_abs == 2){
            fl.setPower(gamepad1.right_stick_x);
            fr.setPower(gamepad1.right_stick_x);
            bl.setPower(gamepad1.right_stick_x);
            br.setPower(gamepad1.right_stick_x);
        }
        if(gamepad1.a){
            fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        }

    }
}
