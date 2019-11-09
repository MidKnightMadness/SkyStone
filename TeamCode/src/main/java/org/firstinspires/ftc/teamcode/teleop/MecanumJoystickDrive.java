/*
 * TODO: 1. Determine which directions motors are facing (configuration)
 *       2. Adjust values to fit the configuration
 */

package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class MecanumJoystickDrive extends JoystickDrive {

    private DcMotor motor1;
    private DcMotor motor2;
    private DcMotor motor3;
    private DcMotor motor4;

    public MecanumJoystickDrive() {
    }

    @Override
    public void init() {
        motor1 = hardwareMap.dcMotor.get("fl");
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motor2 = hardwareMap.dcMotor.get("fr");
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motor3 = hardwareMap.dcMotor.get("bl");
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motor4 = hardwareMap.dcMotor.get("br");
        motor4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
            slow = true;
        } else {
            slow = false;
        }

        telemetry.addData("X", abs[0][0]);
        telemetry.addData("Y", abs[1][0]);
        telemetry.addData("R", abs[2][0]);
        telemetry.addData("M", max_abs);
        telemetry.addData("S", slow);
        telemetry.update();


        if (slow && max_abs != -1){
            abs[max_abs][0] = (abs[max_abs][0])*(abs[max_abs][0]);
        }

        //-1 = not moving
        //0 = horizontal movement
        //1 = moving horizontally
        //2 = rotating

        if (max_abs == -1){
            motor1.setPower(0);
            motor2.setPower(0);
            motor3.setPower(0);
            motor4.setPower(0);
        }
        else if(max_abs == 0){
            motor1.setPower(gamepad1.left_stick_x);
            motor2.setPower(gamepad1.left_stick_x);
            motor3.setPower(-gamepad1.left_stick_x);
            motor4.setPower(-gamepad1.left_stick_x);
        }
        else if (max_abs == 1){
            motor1.setPower(-gamepad1.left_stick_y);
            motor2.setPower(gamepad1.left_stick_y);
            motor3.setPower(-gamepad1.left_stick_y);
            motor4.setPower(gamepad1.left_stick_y);
        }
        else if (max_abs == 2){
            motor1.setPower(-gamepad1.right_stick_x);
            motor2.setPower(-gamepad1.right_stick_x);
            motor3.setPower(-gamepad1.right_stick_x);
            motor4.setPower(-gamepad1.right_stick_x);
        }

    }
}
