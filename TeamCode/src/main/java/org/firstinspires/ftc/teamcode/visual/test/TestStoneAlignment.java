package org.firstinspires.ftc.teamcode.visual.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//@Autonomous
public class TestStoneAlignment extends LinearOpMode {
    


    @Override
    public void runOpMode() {
        //Visual visual = new Visual();
        

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.update();

        }
    }
}
