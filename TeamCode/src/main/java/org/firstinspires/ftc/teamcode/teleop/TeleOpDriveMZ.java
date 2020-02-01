/*
 * TODO: 1. Determine which directions motors are facing (configuration)
 *       2. Adjust values to fit the configuration
 */

package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class TeleOpDriveMZ extends TeleMotor {

    private DcMotor motor1;
    private DcMotor motor2;
    private DcMotor motor3;
    private DcMotor motor4;

    @Override
    public void init() {
        motor1 = hardwareMap.dcMotor.get("FL");
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motor2 = hardwareMap.dcMotor.get("FR");
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motor3 = hardwareMap.dcMotor.get("BL");
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motor4 = hardwareMap.dcMotor.get("BR");
        motor4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

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
        if (gamepad1.left_stick_y != 0 && Math.abs(gamepad1.left_stick_y) > Math.abs(gamepad1.left_stick_x)) {
            /* Forward
             * FL +
             * FR +
             * BL +
             * BR +
             *
             * Backwards
             * FL -
             * FR -
             * BL -
             * BR -
             * Works for both settings since it replaces value
             */
            motor1.setPower(gamepad1.left_stick_y);
            motor2.setPower(gamepad1.left_stick_y);
            motor3.setPower(gamepad1.left_stick_y);
            motor4.setPower(gamepad1.left_stick_y);
        } else if (gamepad1.left_stick_x > 0) {
            /* Translate Right
             * FL +
             * FR -
             * BL -
             * BR +
             */

            float x_mag_value = Math.abs(gamepad1.left_stick_x);
            motor1.setPower(x_mag_value);
            motor2.setPower(-1 * x_mag_value);
            motor3.setPower(-1 * x_mag_value);
            motor4.setPower(x_mag_value);
        } else if (gamepad1.left_stick_x < 0){
            /* Translate Left
             * FL -
             * FR +
             * BL +
             * BR -
             */

            float x_mag_value = Math.abs(gamepad1.left_stick_x);
            motor1.setPower(-1 * x_mag_value);
            motor2.setPower(x_mag_value);
            motor3.setPower(x_mag_value);
            motor4.setPower(-1 * x_mag_value);
        }

        //Rotational movement (right joystick)
        if (gamepad1.right_stick_x < 0){
            /* Rotate right
             * FL -
             * FR +
             * BL -
             * BR +
             */
            motor1.setPower(-1 * gamepad1.right_stick_x);
            motor2.setPower(gamepad1.right_stick_x);
            motor3.setPower(-1 * gamepad1.right_stick_x);
            motor4.setPower(gamepad1.right_stick_x);
        } else if (gamepad1.right_stick_x > 0){
            /* Rotate left
             * FL +
             * FR -
             * BL +
             * BR -
             */
            motor1.setPower(gamepad1.right_stick_x);
            motor2.setPower(-1 * gamepad1.right_stick_x);
            motor3.setPower(gamepad1.right_stick_x);
            motor4.setPower(-1 * gamepad1.right_stick_x);
        }
    }
}
