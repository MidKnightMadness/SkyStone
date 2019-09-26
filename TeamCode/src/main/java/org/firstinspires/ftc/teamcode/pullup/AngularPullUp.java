package org.firstinspires.ftc.teamcode.pullup;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.Config;

//@TeleOp
public class AngularPullUp extends PullUp {
    public DcMotor pullUpMotor;
    private Servo pullUpServo;
    private Servo craterServo;
    private double POWER_TO_OPEN_PULLUP = 1;
    private final int TARGET_POSITION_TO_OPEN = 11000;



    @Override
    public void init() {
        craterServo = hardwareMap.servo.get(Config.PullUp.CRATER_SERVO);
        craterServo.setPosition(0);
        pullUpMotor = hardwareMap.dcMotor.get(Config.PullUp.PULLUP_MOTOR);
        pullUpServo = hardwareMap.servo.get(Config.PullUp.PULLUP_SERVO);
        pullUpMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        pullUpMotor.setPower(-POWER_TO_OPEN_PULLUP);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            telemetry.addLine("I DIED!");
            telemetry.update();
        }
        pullUpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pullUpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pullUpMotor.setTargetPosition(0);
        pullUpMotor.setPower(0);

    }

    public void open() throws InterruptedException{
        pullUpMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pullUpMotor.setPower(-1);
        pullUpServo.setPosition(1);
        Thread.sleep(1000);
        pullUpMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pullUpMotor.setPower(1);
        Thread.sleep(2000);
        pullUpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pullUpMotor.setTargetPosition(TARGET_POSITION_TO_OPEN);
        pullUpMotor.setPower(1);
        while (pullUpMotor.isBusy() && !Thread.currentThread().isInterrupted()){}
        pullUpMotor.setPower(0);
    }

    public void drop() throws InterruptedException {
        pullUpMotor.setTargetPosition(2000);
        pullUpMotor.setPower(1);
        while (pullUpMotor.isBusy() && !Thread.currentThread().isInterrupted()) {}
        Thread.sleep(200);
        pullUpMotor.setTargetPosition(0);
        pullUpMotor.setPower(1);
        while (pullUpMotor.isBusy() && !Thread.currentThread().isInterrupted()) {}
        pullUpMotor.setTargetPosition(2000);
        pullUpMotor.setPower(1);
        while (pullUpMotor.isBusy() && !Thread.currentThread().isInterrupted()) {}
        Thread.sleep(200);
        pullUpMotor.setTargetPosition(0);
        pullUpMotor.setPower(1);
        while (pullUpMotor.isBusy() && !Thread.currentThread().isInterrupted()) {}
        pullUpMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pullUpMotor.setPower(-0.5);
        Thread.sleep(500);
        pullUpServo.setPosition(0);
        Thread.sleep(1000);
        pullUpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pullUpMotor.setTargetPosition(0);
        while (pullUpMotor.isBusy() && !Thread.currentThread().isInterrupted()){}
        pullUpMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pullUpMotor.setPower(0);
    }

    public void close() throws InterruptedException {
        pullUpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pullUpMotor.setTargetPosition(20);
        pullUpMotor.setPower(1);
        while (pullUpMotor.isBusy() && !Thread.currentThread().isInterrupted()) {}
        pullUpMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pullUpMotor.setPower(-1);
        Thread.sleep(1000);
        pullUpServo.setPosition(0);
        Thread.sleep(1000);
        pullUpMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pullUpMotor.setPower(0);
    }

    public void reset() {
        pullUpMotor.setPower(0.8);
        pullUpMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void reachCrater() {
        craterServo.setPosition(1);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
    }

    private enum State {
        OPENED,
        OPENING,
        CLOSING,
        CLOSED,
        RESETTING;

        @Override
        public String toString() {
            return this == OPENED ? "OPENED" : this == OPENING ? "OPENING" : this == CLOSED ? "CLOSED" : "CLOSING";
        }
    }
    private State state = State.CLOSED;
    private int subState = 0;

    private ElapsedTime timer = new ElapsedTime();
    @Override
    public void loop() {
        telemetry.addData("State", state.toString());
        telemetry.addData("Sub State", subState);
        if (state == State.CLOSING) {
            if (subState == 0) {
                pullUpMotor.setTargetPosition(0);
                pullUpMotor.setPower(1);
                subState = 1;
            } else if (subState == 1 && !pullUpMotor.isBusy()) {
                pullUpMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                pullUpMotor.setPower(-1);
                timer.reset();
                subState = 2;
            } else if (subState == 2 && timer.milliseconds() < 500) {
                pullUpServo.setPosition(0);
                subState = 3;
                timer.reset();
            } else if (subState == 3 && timer.milliseconds() < 1000) {
                pullUpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                pullUpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                pullUpMotor.setTargetPosition(0);
                if (DEMO) {
                    pullUpMotor.setPower(0);
                }
                state = State.CLOSED;
            }
        } else if (state == State.OPENING) {
            if (subState == 0) {
                pullUpMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                pullUpMotor.setPower(-1);
                pullUpServo.setPosition(1);
                subState = 1;
                timer.reset();
            } else if (subState == 1 && timer.milliseconds() < 1000) {
                pullUpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                pullUpMotor.setTargetPosition(TARGET_POSITION_TO_OPEN);
                pullUpMotor.setPower(1);
                subState = 2;
            } else if (subState == 2 && !pullUpMotor.isBusy()) {
                pullUpMotor.setPower(0);
                state = State.OPENED;
            }
        } else if (state == State.RESETTING) {
            if (subState == 0) {
                pullUpMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                pullUpMotor.setPower(-0.8);
                timer.reset();
                subState = 1;
            } else if (subState == 1 && timer.milliseconds() > 2000){
                pullUpMotor.setPower(0);
                pullUpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                state = State.CLOSED;
                pullUpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                pullUpMotor.setTargetPosition(0);
                pullUpMotor.setPower(0);
            }
        } else if ((gamepad1.dpad_down || (gamepad2.dpad_down && DEMO))&& state == State.CLOSED) {
            state = State.OPENING;
            subState = 0;
        } else if ((gamepad1.dpad_up || (gamepad2.dpad_up && DEMO)) && state == State.OPENED) {
            state = State.CLOSING;
            subState = 0;
        } else if (gamepad1.x) {
            state = State.RESETTING;//?
            subState = 0;
        }
    }
}
