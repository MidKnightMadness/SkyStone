package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.Distance;

@TeleOp
public class EncoderTest extends Drive {

    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;

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
    public void loop() {
        fl.setPower(gamepad1.left_trigger);
        fr.setPower(gamepad1.right_trigger);
        bl.setPower(gamepad2.left_trigger);
        br.setPower(gamepad2.right_trigger);

        telemetry.addData("FL", fl.getCurrentPosition());
        telemetry.addData("FR", fr.getCurrentPosition());
        telemetry.addData("BL", bl.getCurrentPosition());
        telemetry.addData("BR", br.getCurrentPosition());
        telemetry.update();

    }


    @Override
    public void setRunToPosition() {

    }

    @Override
    public void beginTranslation(Distance distance, double speed) {

    }

    @Override
    public void beginTranslationSide(Distance distance, int direction, double speed) {

    }

    @Override
    public void beginRotation(Angle angle, int direction, double speed) {

    }

    @Override
    public boolean isBusy() {
        return false;
    }
}
