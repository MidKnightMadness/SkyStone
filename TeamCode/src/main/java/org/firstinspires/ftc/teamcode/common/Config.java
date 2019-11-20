package org.firstinspires.ftc.teamcode.common;

import android.preference.PreferenceManager;
import android.util.Log;


import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;

/**
 * Config: Configuration file to hold Motor Names.
 *
 * Create a new static final class for every assembly.
 *
 * Created by Gregory on 9/10/18.
 */

public final class Config {
    public static final class Drive {     // Drive Assembly Configuration
        public static final String FRONT_LEFT = "fl";   // Hub 2: 0
        public static final String FRONT_RIGHT = "fr"; // Hub 2: 1
        public static final String BACK_RIGHT = "br";   // Hub 2: 2
        public static final String BACK_LEFT = "bl";     // Hub 2: 3
        // "imu"   Hub 2: I2C Bus 0: 0
    }

    public static final class Lift {     // Drive Assembly Configuration
        public static final String LIFT_MOTOR = "l";   // Hub 1: 0
    }

    public static final class Mover {     // Drive Assembly Configuration
        public static final String MOVER_SERVO_LEFT = "sl";   // Hub 2: 1
        public static final String MOVER_SERVO_RIGHT = "sr";   // Hub 2: 0

    }

    public static final class Measurements {
        public static final double ROBOT_DEGREES_PER_TICK = 7200d/62500d;
        public static final double ENCODER_TICKS_PER_INCH = 4100d/96d;
        public static final int ENCODER_TICKS_PER_SHAFT_DEGREE = 1100;
    }


}
