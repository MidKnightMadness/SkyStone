package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.AssemblyManager;
import org.firstinspires.ftc.teamcode.common.Config;
import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.hand.Hand;
import org.firstinspires.ftc.teamcode.mineral.MineralArm;
import org.firstinspires.ftc.teamcode.pullup.PullUp;

/**
 * Main TeleOp for Tournament
 *
 * Runs many Assemblies for the Tournament
 *
 * Created by Gregory on 9/10/18.
 */

/* ************************ */
/* ***** DO NOT EDIT ****** */
/* ************************ */

@TeleOp(name = "Main Robot", group = "Main")
public class Main extends OpMode {

    private OpMode[] assemblies = new OpMode[4];                  // Increase the size of the array for the amount of Assemblies

    @Override
    public void init() {
        assemblies[0] = AssemblyManager.newInstance(Drive.class, hardwareMap, telemetry, true); // Initialize all OpModes independently
        assemblies[1] = AssemblyManager.newInstance(PullUp.class, hardwareMap, telemetry, true);
        assemblies[2] = AssemblyManager.newInstance(MineralArm.class, hardwareMap, telemetry, true);
        assemblies[3] = AssemblyManager.newInstance(Hand.class, hardwareMap, telemetry, true);
    }

    @Override
    public void init_loop() {
        for (OpMode assembly : assemblies) {                      // Start All Registered Assemblies in the Array
            assembly.init_loop();
        }
    }

    @Override
    public void start() {
        for (OpMode assembly : assemblies) {                      // Start All Registered Assemblies in the Array
            assembly.start();
        }
    }

    @Override
    public void loop() {
        for (OpMode assembly : assemblies) {                      // Loop All Registered Assemblies in the Array
            assembly.gamepad1 = gamepad1;
            assembly.gamepad2 = gamepad2;
            assembly.loop();
        }
    }

    @Override
    public void stop() {
        for (OpMode assembly : assemblies) {                      // Stop All Registered Assemblies in the Array
            assembly.stop();
        }
    }
}
