package org.firstinspires.ftc.teamcode.jonathanthong;

        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class JonFirstOPmode extends OpMode {
    @Override
    public void init() {
        telemetry.addLine("Init :: You Started the Program!");
    }

    @Override
    public void loop() {
        double rightstickY100 = -100*gamepad1.right_stick_y;
        telemetry.addData("Right Stick Y Output", rightstickY100);

    }
}
