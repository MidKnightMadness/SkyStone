package org.firstinspires.ftc.teamcode.visual;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.AssemblyManager;

//@TeleOp
public class ColorPicker extends OpMode {
    private Visual v;

    @Override
    public void init() {
        Visual.DEBUG = true;
        telemetry.setAutoClear(false);
        v = AssemblyManager.newInstance(Visual.class, hardwareMap, telemetry);
    }

    private boolean left_pressed = false;
    private boolean right_pressed = false;
    private boolean up_pressed = false;
    private boolean down_pressed = false;
    private int print_x = 0;
    private int print_y = 0;

    @Override
    public void loop() {

        /*if (gamepad1.x) {
            minYellow[0] += (gamepad1.dpad_left ? 1 : (gamepad1.dpad_right ? -1 : 0)) * 0.5;
            minYellow[1] += (gamepad1.left_trigger > 0.2 ? 1 : (gamepad1.right_trigger > 0.2 ? -1 : 0)) * 0.005;
            minYellow[2] += (gamepad1.left_bumper ? 1 : (gamepad1.right_bumper ? -1 : 0)) * 0.005;
        } else {
            maxYellow[0] += (gamepad1.dpad_left ? 1 : (gamepad1.dpad_right ? -1 : 0)) * 0.5;
            maxYellow[1] += (gamepad1.left_trigger > 0.2 ? 1 : (gamepad1.right_trigger > 0.2 ? -1 : 0)) * 0.005;
            maxYellow[2] += (gamepad1.left_bumper ? 1 : (gamepad1.right_bumper ? -1 : 0)) * 0.005;
        }
        try {
            findGoldMineral(false);
        } catch (Exception e) {}
        telemetry.addData("Min Yellow", "H: %3f, S: %3f, V: %3f", minYellow[0], minYellow[1], minYellow[2]);
        telemetry.addData("Max Yellow", "H: %3f, S: %3f, V: %3f", maxYellow[0], maxYellow[1], maxYellow[2]);
        telemetry.update();*/

        if (gamepad1.dpad_left && !left_pressed) {
            print_x = print_x > 0 ? print_x - 1 : 0;
            left_pressed = true;
        } else {
            left_pressed = false;
        }

        if (gamepad1.dpad_right && !right_pressed) {
            print_x = print_x < 19 ? print_x + 1 : 19;
            right_pressed = true;
        } else {
            right_pressed = false;
        }

        if (gamepad1.dpad_up && !up_pressed) {
            print_y = print_y < 11 ? print_y + 1 : 11;
            up_pressed = true;
        } else {
            up_pressed = false;
        }

        if (gamepad1.dpad_down && !down_pressed) {
            print_y = print_y > 0 ? print_y - 1 : 0;
            down_pressed = true;
        } else {
            down_pressed = false;
        }

        try {
            v.isGoldMineral(false, print_x, print_y);
        } catch (Exception e) {}

        telemetry.update();
        telemetry.clear();
    }

    @Override
    public void stop() {
        v.stop();
    }
}
