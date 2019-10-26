package org.firstinspires.ftc.teamcode.visual;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.common.AssemblyManager.Implementation;

/**
 * Created by Gregory on 9/14/18.
 */

@Implementation(VuforiaImpl.class)
public abstract class Visual extends OpMode {
    public enum SkystonePosition {
        LEFT,
        CENTER,
        RIGHT,
        UNKNOWN;

        @Override
        public String toString() {
            return this == LEFT ? "Left" : this == CENTER ? "Center" : this == RIGHT ? "Right" : "Unknown";
        }
    }
    public static boolean DEBUG = false;
    public static boolean SAVE = false;

    public abstract void init();
    public abstract int isSkystone(boolean save) throws InterruptedException;
    public abstract int isSkystone(boolean save, int print_x, int print_y) throws InterruptedException;
    public abstract SkystonePosition findSkystone() throws InterruptedException;
    public abstract void stop();
}