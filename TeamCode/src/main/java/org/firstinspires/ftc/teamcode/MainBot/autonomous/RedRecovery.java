package org.firstinspires.ftc.teamcode.MainBot.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

import static org.firstinspires.ftc.teamcode.MainBot.autonomous.AutonomousController.*;

@Autonomous(name = "Red Recovery", group = "MainBot")
public class RedRecovery extends LinearOpMode {

    private static VisualController.JewelColor TEAM_COLOR = VisualController.JewelColor.RED;
    private AutonomousController a = new AutonomousController();
    private VisualController v = new VisualController();

    private static int ENC_90 = 1575;

    private double waitUntil = 0;
    private int state = 0;


    private int[][] targets = new int[][]{
           //L, C, R
            //shift
            {-100, 0, 0},
            //knock
            {250, 0, -250},
            //toCrypto
            {2500, 1800, 2700},
            //rotCrypto
            {ENC_90*3/2, ENC_90*3/2, ENC_90/2},
            //push (1 = LR, 0 = UD)
            {1, 1, 0},
            {-500, -500, -500},
            {-1100, -1100, -1100},
            {1000, 1000, 1000},
            //rotate
            {ENC_90*3/2, ENC_90*3/2, ENC_90*5/2}
    };












    @Override
    public void runOpMode() throws InterruptedException {
        a.init(telemetry, hardwareMap, v);
        v.init(telemetry, hardwareMap);

        v.saveTeamColor(0);

        telemetry.addLine("Status: Initialized and ready!");
        telemetry.update();

        waitForStart();

        a.close();
        wait(1d);

        a.lift();
        waitFor(ELEV);

        a.look();

        a.moveBot(targets[0][0]);
        waitFor(FRONT_LEFT);

        if (v.rightJewel != null) {
            state = (v.rightJewel == TEAM_COLOR ? 0 : 2);

            a.lowerJArm();
            wait(1d);

            a.rotateBot(targets[1][state], 0.7);
            waitFor(FRONT_LEFT);

            a.raiseJArm();
            wait(1d);

            a.rotateBot(-targets[1][state], 0.7);
            waitFor(FRONT_LEFT);
        }

        state = (v.pictograph == RelicRecoveryVuMark.LEFT ? 0 : (v.pictograph == RelicRecoveryVuMark.CENTER ? 1 : 2));

        a.moveBot(targets[2][state]);
        waitFor(FRONT_LEFT);

        a.rotateBot(targets[3][state], 0.7);
        waitFor(FRONT_LEFT);

        if (targets[4][state] == 0) {
            a.moveBotDiUD(targets[5][state]);
            waitFor(FRONT_LEFT);
        } else {
            a.moveBotDiLR(targets[5][state]);
            waitFor(BACK_LEFT);
        }




        a.lower();
        waitFor(ELEV);

        a.open();

        if (targets[4][state] == 0) {
            a.moveBotDiUD(targets[6][state]);
            waitFor(FRONT_LEFT);
        } else {
            a.moveBotDiLR(targets[6][state]);
            waitFor(BACK_LEFT);
        }

        if (targets[4][state] == 0) {
            a.moveBotDiUD(targets[7][state]);
            waitFor(FRONT_LEFT);
        } else {
            a.moveBotDiLR(targets[7][state]);
            waitFor(BACK_LEFT);
        }

        a.grabStop();

        a.rotateBot(targets[8][state]);
        waitFor(FRONT_LEFT);
    }

    private void waitFor(int motor) throws InterruptedException {
        while (a.motors[motor].isBusy())
            idle();
        a.reset();
    }

    private void wait(double s) throws InterruptedException {
        waitUntil = time + s;
        while (time < waitUntil)
            idle();
        a.reset();
    }
}