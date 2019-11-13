package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.Distance;

import java.security.Policy;

@TeleOp
public class MecanumDrive extends Drive {

    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    private double POWER_CONSTANT = 1;
    public double left = 1;
    public double right = -1;

    @Override
    public void init() {

        fl = hardwareMap.dcMotor.get("fl");
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //motor1 is front left
        fr = hardwareMap.dcMotor.get("fr");
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //motor2 is front right
        bl = hardwareMap.dcMotor.get("bl");
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //motor3 is back left
        br = hardwareMap.dcMotor.get("br");
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //motor4 is back right
    }
    @Override
    public void init_loop() {


    }
    @Override
    public void start() {

    }

    @Override
    public void loop() {
        //turn left (counter-clockwise)
        if (gamepad1.left_trigger != 0) {
            fl.setPower(POWER_CONSTANT);
            fr.setPower(POWER_CONSTANT);
            bl.setPower(POWER_CONSTANT);
            br.setPower(POWER_CONSTANT);
        }
        //turn right (clockwise)
        if (gamepad1.right_trigger != 0) {
            fl.setPower(-POWER_CONSTANT);
            fr.setPower(-POWER_CONSTANT);
            bl.setPower(-POWER_CONSTANT);
            br.setPower(-POWER_CONSTANT);
        }
        //scoot left
        if (gamepad1.dpad_left) {
            fl.setPower(POWER_CONSTANT);
            fr.setPower(POWER_CONSTANT);
            bl.setPower(-POWER_CONSTANT);
            br.setPower(-POWER_CONSTANT);
        }
        //scoot right
        if (gamepad1.dpad_right) {
            fl.setPower(-POWER_CONSTANT);
            fr.setPower(-POWER_CONSTANT);
            bl.setPower(POWER_CONSTANT);
            br.setPower(POWER_CONSTANT);
        }
        //forward
        if (gamepad1.dpad_up) {
            fl.setPower(-POWER_CONSTANT);
            fr.setPower(POWER_CONSTANT);
            bl.setPower(-POWER_CONSTANT);
            br.setPower(POWER_CONSTANT);
        }
        //backward
        if (gamepad1.dpad_down) {
            fl.setPower(POWER_CONSTANT);
            fr.setPower(-POWER_CONSTANT);
            bl.setPower(POWER_CONSTANT);
            br.setPower(-POWER_CONSTANT);
        }
        //stop
        if (gamepad1.a){
            fl.setPower(0);
            fr.setPower(0);
            bl.setPower(0);
            br.setPower(0);
        }
        telemetry.addLine(String.valueOf(fl.getPower()));
        telemetry.addLine(String.valueOf(fr.getPower()));
        telemetry.addLine(String.valueOf(bl.getPower()));
        telemetry.addLine(String.valueOf(br.getPower()));
        telemetry.update();
    }

    /**
     * Makes the bot move from the pre-set values from {@code begin} methods
     */
    @Override
    public void setRunToPosition(){
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     *
     * @param distance the distance at which the bot moves in {@code Distance} distance units
     * @param speed speed in which bot moves, it sets the power
     */
    @Override
    public void beginTranslation(Distance distance, double speed){
        fl.setTargetPosition(fl.getCurrentPosition()+distance.toEncoderTicks());
        fr.setTargetPosition(fr.getCurrentPosition()-distance.toEncoderTicks());
        bl.setTargetPosition(bl.getCurrentPosition()+distance.toEncoderTicks());
        br.setTargetPosition(br.getCurrentPosition()-distance.toEncoderTicks());
        fl.setPower(speed);
        fr.setPower(-speed);
        bl.setPower(speed);
        br.setPower(-speed);
    }

    /**
     *
     * @param distance the distance at which the bot moves in {@code Distance} distance units
     * @param direction the direction in which the bot moves sideways, {@code 1} moves to the right and {@code -1} moves to the left
     * @param speed speed in which bot moves, it sets the power
     */
    @Override
    public void beginTranslationSide(Distance distance, int direction, double speed){
        fl.setTargetPosition(fl.getCurrentPosition() + direction * distance.toEncoderTicks());
        fr.setTargetPosition(fr.getCurrentPosition() + direction * distance.toEncoderTicks());
        bl.setTargetPosition(bl.getCurrentPosition() - direction * distance.toEncoderTicks());
        br.setTargetPosition(br.getCurrentPosition() - direction * distance.toEncoderTicks());
        fl.setPower(speed);
        fr.setPower(speed);
        bl.setPower(-speed);
        br.setPower(-speed);
    }

    /**
     *
     * @param angle the angle at which the bot rotates in {@code Distance} angle units
     * @param direction
     * @param speed speed in which bot moves, it sets the power
     */
    @Override
    public void beginRotation(Angle angle, int direction, double speed) {
        fl.setTargetPosition(fl.getCurrentPosition() - direction * angle.toEncoderTicks());
        fr.setTargetPosition(fr.getCurrentPosition() - direction * angle.toEncoderTicks());
        bl.setTargetPosition(bl.getCurrentPosition() - direction * angle.toEncoderTicks());
        br.setTargetPosition(br.getCurrentPosition() - direction * angle.toEncoderTicks());
        fl.setPower(-speed);
        fr.setPower(-speed);
        bl.setPower(-speed);
        br.setPower(-speed);
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isBusy(){
        return Math.abs(fl.getCurrentPosition()- fl.getTargetPosition()) > 10;
    }




}