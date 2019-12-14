package org.firstinspires.ftc.teamcode.led;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

import org.firstinspires.ftc.teamcode.common.Assembly;


// Control all the LEDs!!!
public class LED {
    private static I2cDeviceSynch ledController;
    private static byte currentColor = 0;

    public static void init(I2cDeviceSynch ledController) {
        LED.ledController = ledController;
        ledController.setI2cAddress(I2cAddr.create8bit(0x50));
        ledController.write8(0xF4, 0b00001101);
        ledController.write8(0xF7, 0b11110011);
    }

    public class LEDColor extends Color {

    }

    private void setLEDs(short[] leds) {
        byte[] data = new byte[(leds.length + 2) * 4];
        data[0] = data[1] = data[2] = data[3] = 0;
        for (int i = 0; i < leds.length; i++) {
            data[(i*4) + 4] = (byte) (leds[i] & 0x000000FF);
            data[(i*4) + 5] = (byte) (leds[i] & 0x0000FF00);
            data[(i*4) + 6] = (byte) (leds[i] & 0x00FF0000);
            data[(i*4) + 7] = (byte) (leds[i] & 0xFF000000);
        }
    }

    public class Modee {
        private abstract class Generic {
             protected abstract void init();
             protected abstract void update();
        }

        public class Solid extends Generic {
            @Override
            protected void init() {

            }

            @Override
            protected void update() {

            }
        }
    }



    public enum Mode {
        SOLID,
    }

    /*public static void setStaticColor(StaticColor color) {
        if (ledController != null) {
            ledController.write8(0xF0, color.getValue());
        }
    }*/

    // Static color patterns...
}
