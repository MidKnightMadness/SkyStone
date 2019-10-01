package org.firstinspires.ftc.teamcode.autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class Tester extends BaseAutonomous {
    public void runOpMode() {

        initialize();
        waitForStart();
        armMotor.setPower(1);
        double waitUntil = time + 1.5;
        while (time < waitUntil) {}
    }
}