package org.firstinspires.ftc.teamcode.autonomous.Tests;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.common.Config;

import java.io.File;
@Autonomous
public class TestAudio extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Config.Sound.prepare();
        waitForStart();
        Config.Sound.player.start();
        while (!Thread.currentThread().isInterrupted()) {
            telemetry.addData("Playing Music!", "Yay!");
            telemetry.update();
        }
    }
}
