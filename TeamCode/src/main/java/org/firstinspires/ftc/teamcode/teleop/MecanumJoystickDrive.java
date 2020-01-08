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
    private double pwr = 0;
    private double[] examplelvl = {0,.3,.6,.999,1};
    private double[][] apwr = {{.4,.6,.8,1}, //left/right
            {.15,.3,.6,1}, //front/back
            {.2,.35,.6,1}, //rotation
            {0,0,0,0}};
    private double[][] alvl = {examplelvl, examplelvl, examplelvl,{0,0,0,0,0}};
    private double[][] amtr = {{1,1,-1,-1},{-1,1,-1,1},{1,1,1,1},{0,0,0,0}};
    private double[] usepwr = new double[4];


    @Override
    /*
     * Left joystick controls transitioning motion
     * Right joystick controls the face/rotation
     */
    public void loop() {

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
            max_abs = 3;
        }

        //-1 = not moving
        //0 = horizontal movement
        //1 = moving horizontally
        //2 = rotating
        if (max_abs == 3){
        } else if(max_abs == 0){
            pwr = gamepad1.left_stick_x;
        } else if (max_abs == 1){
            pwr = gamepad1.left_stick_y;
        } else if (max_abs == 2){
            pwr = gamepad1.right_stick_x;
        }

        if(pwr != 0) {/*
            if (pwr >= -alvl[max_abs][4] && pwr < -alvl[max_abs][3]) {
                pwr = -apwr[max_abs][3];
            } else if (pwr >= -alvl[max_abs][3] && pwr < -alvl[max_abs][2]) {
                pwr = -apwr[max_abs][2];
            } else if (pwr >= -alvl[max_abs][2] && pwr < -alvl[max_abs][1]) {
                pwr = -apwr[max_abs][1];
            } else if (pwr >= -alvl[max_abs][1] && pwr < -alvl[max_abs][0]) {
                pwr = -apwr[max_abs][0];
            } else if (pwr > alvl[max_abs][0] && pwr <= alvl[max_abs][1]) {
                pwr = apwr[max_abs][0];
            } else if (pwr > alvl[max_abs][1] && pwr <= alvl[max_abs][2]) {
                pwr = apwr[max_abs][1];
            } else if (pwr > alvl[max_abs][2] && pwr <= alvl[max_abs][3]) {
                pwr = apwr[max_abs][2];
            } else if (pwr > alvl[max_abs][3] && pwr <= alvl[max_abs][4]) {
                pwr = apwr[max_abs][3];
            }*/

            for (int i = 0; i < 8; i++) {
                if(i < 4){
                    if(pwr >= -alvl[max_abs][4-i] && pwr < -alvl[max_abs][3-i]){
                        pwr = -apwr[max_abs][3-i];
                        break;
                    }
                }else{
                    if (pwr > alvl[max_abs][i-4] && pwr <= alvl[max_abs][i-3]) {
                        pwr = apwr[max_abs][i-4];
                        break;
                    }
                }
            }
        }

        for(int i = 0; i < 4; i++){
            usepwr[i]=pwr*amtr[max_abs][i];
        }

        fl.setPower(usepwr[0]);
        fr.setPower(usepwr[1]);
        bl.setPower(usepwr[2]);
        br.setPower(usepwr[3]);

        //telemetry.addData("X", (Math.round(100*abs[0][0]))/100.0);
        //telemetry.addData("Y", (Math.round(100*abs[1][0]))/100.0);
        //telemetry.addData("R", (Math.round(100*abs[2][0]))/100.0);
        //telemetry.addData("M ", max_abs);
        //telemetry.addData("P ", pwr);
        //telemetry.addData("FL", fl.getCurrentPosition());
        //telemetry.addData("FR", fr.getCurrentPosition());
        //telemetry.addData("BL", bl.getCurrentPosition());
        //telemetry.addData("BR", br.getCurrentPosition());
        //telemetry.update();

    }
}
