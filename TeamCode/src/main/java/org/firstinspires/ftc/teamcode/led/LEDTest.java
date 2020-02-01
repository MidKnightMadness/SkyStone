package org.firstinspires.ftc.teamcode.led;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * Remove a @Disabled the on the next line or two (if present) to add this opmode to the Driver Station OpMode list,
 * or add a @Disabled annotation to prevent this OpMode from being added to the Driver Station
 */
@TeleOp

public class LEDTest extends OpMode {
    /* Declare OpMode members. */


    @Override
    public void init() {
        Log.init();
        Log.out.println("Creating LEDs");
        try {
            LEDStrip leds = hardwareMap.get(LEDStrip.class, "ledstrip");
            Log.out.println("Init");
            LED.init(leds);
            Log.out.println("Modes");
            LED.setAllModes(new LED.Modes.Static(new LED.Color(0xFF3030, 31)));
            Log.out.println("update");
            Log.out.flush();
            LED.update();
            Log.out.println("done");
            Log.out.flush();

        } catch (Exception e) {
            e.printStackTrace(Log.out);
            Log.out.close();
        }
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        //LED.update();
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        LED.ALL.setMode(new LED.Modes.Running(
                LED.Color.NAVY,
                LED.Color.NAVY,
                LED.Color.GOLD));
        LED.update();

    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        LED.update();


    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        Log.out.close();
    }
}
