package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous
@Disabled
public class SampleOpMode extends OpMode {

    // One of the only two required methods in an opMode.
    // This is run when the init button is pressed.
    // hardwareMap, telemetry, gamepad1, and gamepad2 are created right before init,
    //    so you can only access them from within a method (of which init is the first),
    //    not from a declared property.

    // eg.
    //   private DcMotor motor = hardwareMap.dcMotor.get("motor");
    // Does not work because hardwareMap is null until init is called. You should instead use
    //   private DcMotor motor;
    // and set motor = hardwareMap.dcMotor.get("motor"); in the init method

    @Override
    public void init() {

    }


    // A lot less comments on this one. Note that this is an optional method and therefore you have to call
    //  super.init_loop();   (There's actually a much more detailed explanation, but this will suffice for now)
    // This will be called on loop after init() finishes but before the start button is pressed. It is rarely used.
    @Override
    public void init_loop() {
        super.init_loop();
    }

    // This is also optional (note super), and runs immediately after the start button is pressed.
    @Override
    public void start() {
        super.start();
    }

    // The only other required method. This runs on loop after start() finishes and is the main body of code for
    //   the OpMode.
    @Override
    public void loop() {

    }

    // Optional method that runs when the stop button is pressed. Good for tearing down anything that needs
    //   deinitialization. Not much that we'll look at needs this.
    @Override
    public void stop() {
        super.stop();
    }
}
