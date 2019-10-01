package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Main Robot", group = "Main")
public class Main extends OpMode {

    private MecanumDrive mecanumDrive;
    private LinearLift linearLift;
    private MineralScorer mineralScorer;
    private TeamMarker teamMarker;

    public Main() {
        mecanumDrive = new MecanumDrive();
        linearLift = new LinearLift();
        mineralScorer = new MineralScorer();
        teamMarker = new TeamMarker();
    }

    @Override
    public void init() {
        mecanumDrive.hardwareMap = hardwareMap;
        mecanumDrive.telemetry = telemetry;

        linearLift.hardwareMap = hardwareMap;
        linearLift.telemetry = telemetry;

        mineralScorer.hardwareMap = hardwareMap;
        mineralScorer.telemetry = telemetry;

        teamMarker.hardwareMap = hardwareMap;
        teamMarker.telemetry = telemetry;

        mineralScorer.init();
        mecanumDrive.init();
        linearLift.init();
        teamMarker.init();
    }

    @Override
    public void start() {
        mecanumDrive.start();
        linearLift.start();
        mineralScorer.start();
        teamMarker.start();
    }

    @Override
    public void loop() {
        mecanumDrive.gamepad1 = gamepad1;
        mecanumDrive.gamepad2 = gamepad2;
        mecanumDrive.loop();

        linearLift.gamepad1 = gamepad1;
        linearLift.gamepad2 = gamepad2;
        linearLift.loop();

        mineralScorer.gamepad1 = gamepad1;
        mineralScorer.gamepad2 = gamepad2;
        mineralScorer.loop();

        teamMarker.gamepad1 = gamepad1;
        teamMarker.gamepad2 = gamepad2;
        teamMarker.loop();
    }

    @Override
    public void stop() {
        mecanumDrive.stop();
        linearLift.stop();
        mineralScorer.stop();
        teamMarker.stop();
    }
}
