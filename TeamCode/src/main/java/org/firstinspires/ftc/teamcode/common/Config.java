package org.firstinspires.ftc.teamcode.common;

import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.qualcomm.ftccommon.SoundPlayer;

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
        public static final String FRONT_LEFT = "front left";   // Hub 2: 0
        public static final String FRONT_RIGHT = "front right"; // Hub 2: 1
        public static final String BACK_RIGHT = "back right";   // Hub 2: 2
        public static final String BACK_LEFT = "back left";     // Hub 2: 3
                                                        // "imu"   Hub 2: I2C Bus 0: 0
    }

    public static final class Hand {     // Hand Assembly Configuration
        public static final String HAND_MOTOR = "hand motor";//Hub 1: 3

    }

    public static final class PullUp {     // Pull-up Assembly Configuration
        public static final String PULLUP_MOTOR = "pull up motor"; // Hub 1: 2
        public static final String PULLUP_SERVO = "pull up servo"; // Hub 2: 0
        public static final String CRATER_SERVO = "crater servo";

    }

    public static final class MineralArm {      // Mineral Arm Assembly Configuration
        public static final String MINERAL_EXTEND_MOTOR = "mineral extending motor"; // Hub 1: 1
        public static final String MINERAL_ROTATE_MOTOR = "mineral rotating motor";  // Hub 1: 0

    }

    public static final class Measurements {
        public static final double ROBOT_DEGREES_PER_TICK = 0.060857d;
        public static final double ENCODER_TICKS_PER_INCH = 5000d/68d;
        public static final int ENCODER_TICKS_PER_SHAFT_DEGREE = 1100;
    }

    public static final class Sound {
        public static final String SOUND_FILE = "/storage/self/primary/ThemeSong.wav";
        public static final MediaPlayer player = new MediaPlayer();
        private static boolean prepared = false;
        public static void prepare() {

            if (!prepared) {
                try {
                    player.setDataSource(SOUND_FILE);
                    prepared = true;
                } catch (Exception e) {
                    Log.d("ERROR! ", e.getMessage());
                }
                player.prepareAsync();
            }
            if (isLocalSoundOn()) {
                player.setVolume(1, 1);
            } else {
                player.setVolume(0, 0);
            }
        }
    }
    public static final boolean isLocalSoundOn()
    {
        return false; //PreferenceManager.getDefaultSharedPreferences(AppUtil.getDefContext()).getBoolean(AppUtil.getDefContext().getString(com.qualcomm.ftccommon.R.string.pref_sound_on_off), true);
    }
}

