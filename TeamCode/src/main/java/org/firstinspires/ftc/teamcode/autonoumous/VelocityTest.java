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

        DcMotorEx motor = (DcMotorEx) hardwareMap.dcMotor.get("BL");

        try {
            PrintWriter printWriter = new PrintWriter("/storage/self/primary/FIRST/java/src/org/firstinspires/ftc/teamcode/somethingBL.csv");

            for (int i = 0; i < 10; i++) {
                motor.setPower(i * 0.1);
                Thread.sleep(2000);
                printWriter.println((i * 0.1) + "," + motor.getVelocity());
            }
            motor.setPower(0);

            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        /*motor = (DcMotorEx) hardwareMap.dcMotor.get("BR");

        try {
            PrintWriter printWriter = new PrintWriter("/storage/self/primary/FIRST/java/src/org/firstinspires/ftc/teamcode/somethingBR.csv");

            for (int i = 0; i < 10; i++) {
                motor.setPower(i * 0.1);
                Thread.sleep(2000);
                printWriter.println((i * 0.1) + "," + motor.getVelocity());
            }
            motor.setPower(0);

            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }
}
