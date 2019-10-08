package org.firstinspires.ftc.teamcode.KM.ServoTestingK;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

//@TeleOp
public class ServoTestingKM extends OpMode {

    private Servo servo;
    private CRServo crservo;
    private DcMotor motor;
    private boolean yisdown;
    private boolean motorrunning;

    @Override
    public void init() {
        servo = hardwareMap.servo.get("servo");
        crservo = hardwareMap.crservo.get("crservo");
        motor = hardwareMap.dcMotor.get("motor");
    }

    @Override
    public void loop() {
        crservo.setPower(gamepad1.right_stick_y/1);
        servo.setPosition(gamepad1.left_stick_y/1);
        if (gamepad1.y) {
           if(yisdown){

           } else{
               yisdown = true;
               motorrunning = !motorrunning;
           }

        } else {
            yisdown = false;
        }

        if (motorrunning){
            motor.setPower(1);
        } else {
            motor.setPower(0);
        }







        telemetry.addData("positionleftsticky", gamepad1.left_stick_y);
        telemetry.addData("positionrightsticky", gamepad1.right_stick_y);
        telemetry.addData("positionlefttrigger", gamepad1.left_trigger);
        telemetry.addData("positionrighttrigger", gamepad1.right_trigger);
        telemetry.addData("positiony", gamepad1.y);
        telemetry.addData("power", motor.getPower());




        }


    }

