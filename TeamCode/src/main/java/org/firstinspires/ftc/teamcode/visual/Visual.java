package org.firstinspires.ftc.teamcode.visual;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.common.AssemblyManager.Implementation;

/**
 * Created by Gregory on 9/14/18.
 */

@Implementation(NewVisualImpl.class)
public abstract class Visual extends OpMode {
    public enum MineralPosition {
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

    public static double minYellow[] = {20, 0.5, 0.3};
    public static double maxYellow[] = {62, 1, 1};
    public static double minWhite[] = {0, 0, 0.8};
    public static double maxWhite[] = {255, 0.2, 1};



    public abstract void init();
    // 1 = Gold, 0 = Silver, -1 = Unknown
    public abstract void startTfod();
    public abstract int isGoldMineral(boolean save) throws InterruptedException;
    public abstract int isGoldMineral(boolean save, int print_x, int print_y) throws InterruptedException;
    public abstract MineralPosition findGoldMineral() throws InterruptedException;
    public abstract void stop();
}
