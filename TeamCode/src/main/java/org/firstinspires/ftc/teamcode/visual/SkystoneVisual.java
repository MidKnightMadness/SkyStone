package org.firstinspires.ftc.teamcode.visual;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.common.AssemblyManager.Implementation;
import org.firstinspires.ftc.teamcode.visualtests.ConceptVuforiaSkyStoneNavigation;

/**
 * Created by Gregory on 9/14/18.
 */

@Implementation(ConceptVuforiaSkyStoneNavigation.class)
public abstract class SkystoneVisual extends OpMode {
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

    public static double minYellow[] = {20, 0.5, 0.3};
    public static double maxYellow[] = {62, 1, 1};
    public static double minBlack[] = {0, 0, 0.8};
    public static double maxBlack[] = {255, 0.2, 1};

    public abstract void init();
    public abstract int isSkystone(boolean save) throws InterruptedException;
    public abstract int isSkystone(boolean save, int print_x, int print_y) throws InterruptedException;
    public abstract SkystonePosition findSkystone() throws InterruptedException;
    public abstract void stop();
}