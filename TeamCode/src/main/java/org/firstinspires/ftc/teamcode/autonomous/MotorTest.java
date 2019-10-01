package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous
@Disabled
public class MotorTest extends BaseAutonomous {

    public void runOpMode()  {
        initialize();

        waitForStart();

        testMotors(1000, 1);
    }
}
