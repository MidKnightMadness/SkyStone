package org.firstinspires.ftc.teamcode.autonoumous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

@Autonomous
public class VelocityTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotorEx motor = (DcMotorEx) hardwareMap.dcMotor.get("FL");

        try {
            PrintWriter printWriter = new PrintWriter("/storage/self/primary/FIRST/java/src/org/firstinspires/ftc/teamcode/somethingFL.csv");

            for (int i = 0; i < 10; i++) {
                motor.setPower(i * 0.1);
                wait(2000);

                printWriter.println((i * 0.1) + "," + motor.getVelocity());
            }

            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
