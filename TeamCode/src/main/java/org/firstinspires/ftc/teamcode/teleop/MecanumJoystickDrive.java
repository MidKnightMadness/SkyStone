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


    private double[] abs = new double[3];
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


        /********************************************
         * All values assumes that all motor's positive powers are forward
         * *******************************************
         */


        //For translation movement, the largest magnitude of of x and y will be chosen for movement
        //If x value = y value, the program will favor in chainging the y movement
        //Translational movement (left joystick)
        abs[0] = Math.abs(gamepad1.left_stick_x);
        abs[1] = Math.abs(gamepad1.left_stick_y);
        abs[2] = Math.abs(gamepad1.right_stick_x);
        max_abs = 0;
        for(int i = 0; i < 3; i++){
            if(abs[i]<0.04){
                abs[i]=0.0;
            } else {
                if(abs[i]>abs[max_abs]){
                    max_abs=i;
                }
            }
        }
        if(abs[max_abs]==0){
            max_abs = -1;
        }
        telemetry.addData("X", abs[0]);
        telemetry.addData("Y", abs[1]);
        telemetry.addData("R", abs[2]);
        telemetry.addData("M", max_abs);
        telemetry.update();


        if (slow){

        } else {

        }

        if (max_abs == -1){
            motor1.setPower(0);
            motor2.setPower(0);
            motor3.setPower(0);
            motor4.setPower(0);
        } else if(max_abs == 0){
            motor1.setPower(gamepad1.left_stick_x);
            motor2.setPower(gamepad1.left_stick_x);
            motor3.setPower(-gamepad1.left_stick_x);
            motor4.setPower(-gamepad1.left_stick_x);
        } else if (max_abs == 1){
            motor1.setPower(-gamepad1.left_stick_y);
            motor2.setPower(gamepad1.left_stick_y);
            motor3.setPower(-gamepad1.left_stick_y);
            motor4.setPower(gamepad1.left_stick_y);
        } else if (max_abs == 2){
            motor1.setPower(-gamepad1.right_stick_x);
            motor2.setPower(-gamepad1.right_stick_x);
            motor3.setPower(-gamepad1.right_stick_x);
            motor4.setPower(-gamepad1.right_stick_x);
        }
        /*
        if (gamepad1.left_stick_y != 0 && Math.abs(gamepad1.left_stick_y) > Math.abs(gamepad1.left_stick_x)) {

            motor1.setPower(-gamepad1.left_stick_y);
            motor2.setPower(gamepad1.left_stick_y);
            motor3.setPower(-gamepad1.left_stick_y);
            motor4.setPower(gamepad1.left_stick_y);
        } else if (gamepad1.left_stick_x > 0) {


            float x_mag_value = Math.abs(gamepad1.left_stick_x);
            motor1.setPower(-1 * x_mag_value);
            motor2.setPower(-1 * x_mag_value);
            motor3.setPower(x_mag_value);
            motor4.setPower(x_mag_value);
        } else if (gamepad1.left_stick_x < 0){


            float x_mag_value = Math.abs(gamepad1.left_stick_x);
            motor1.setPower(-1 * x_mag_value);
            motor2.setPower(-1 * x_mag_value);
            motor3.setPower(x_mag_value);
            motor4.setPower(x_mag_value);
        }

        //Rotational movement (right joystick)
        if (gamepad1.right_stick_x < 0){

            motor1.setPower(-1*gamepad1.right_stick_x);
            motor2.setPower(-1*gamepad1.right_stick_x);
            motor3.setPower(-1*gamepad1.right_stick_x);
            motor4.setPower(-1*gamepad1.right_stick_x);
        } else if (gamepad1.right_stick_x > 0){

            motor1.setPower(-1*gamepad1.right_stick_x);
            motor2.setPower(-1*gamepad1.right_stick_x);
            motor3.setPower(-1*gamepad1.right_stick_x);
            motor4.setPower(-1*gamepad1.right_stick_x);
        } else if (gamepad1.left_stick_x == 0 && gamepad1.left_stick_y == 0){
            motor1.setPower(0);
            motor2.setPower(0);
            motor3.setPower(0);
            motor4.setPower(0);
        }*/

    }
}
