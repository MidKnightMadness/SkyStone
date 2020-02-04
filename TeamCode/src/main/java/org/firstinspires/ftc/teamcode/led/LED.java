package org.firstinspires.ftc.teamcode.led;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

import java.util.ArrayList;
import java.util.Arrays;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.led.util.LEDColor;
import org.firstinspires.ftc.teamcode.led.util.LEDSection;


// Control all the LEDs!!!
public class LED {
    private static I2cDeviceSynch ledController;
    private static LEDColor[] leds = new LEDColor[30];

    private static boolean initialized = false;

    /**** LEDColor Translation - HEX RGB and brightness to DotStar byte field ****/
    public static class Colors {
        /**** LEDColor Presets. Add new colors here. ****/
        public static final LEDColor OFF = new LEDColor(0, 0);
        public static final LEDColor RED = new LEDColor(0xFF0000, 31);
        public static final LEDColor GREEN = new LEDColor(0x00FF00, 31);
        public static final LEDColor BLUE = new LEDColor(0x0000FF, 31);
        public static final LEDColor NAVY = new LEDColor(0x000090, 31);
        public static final LEDColor GOLD = new LEDColor(0xEEEE00, 31);
    }


    // Initialize the LEDs with the LED hardware.
    public static void init(I2cDeviceSynch ledStrip) {
        LED.ledController = ledStrip;//.getDevice();
        ledStrip.disengage();
        ledStrip.setI2cAddress(I2cAddr.create8bit(0x50));
        ledStrip.engage();
        for (int j = 0; j < leds.length; j++) {
            leds[j] = Colors.OFF;
        }
        initialized = true;
    }

    public static class PseudoSection {
        private LEDSection[] sections;
        private PseudoSection(LEDSection...sections) {
            this.sections = sections;
        }
        public void set(Modes mode, LEDColor...colors) {
            set(colors);
            set(mode);
        }
        public void set(Modes mode) {
            for (LEDSection section : sections) {
                section.set(mode);
            }
        }
        public void set(LEDColor...colors) {
            for (LEDSection section : sections) {
                section.set(colors);
            }
        }
    }

    /**** LED configuration. Set the lengths and make new sections as needed. Order matters. ****/
    public static final LEDSection BACK = new LEDSection(7);
    public static final LEDSection RIGHT = new LEDSection(8);
    public static final LEDSection LOWER_BACK = new LEDSection( 7);
    public static final LEDSection LEFT = new LEDSection(8);

    public static final PseudoSection ALL = new PseudoSection(BACK, RIGHT, LEFT, LOWER_BACK);
    public static final PseudoSection LOWER = new PseudoSection(RIGHT, LEFT, LOWER_BACK);

    /**** Abstract Mode for controlling the leds ****/
    public static abstract class Mode {
        protected LEDSection section;
        public void setSection(LEDSection section) {
            this.section = section;
        }

        protected LEDColor color(int i) { // prevent null unset colors
            return section.colors != null && i < section.colors.length && section.colors[i] != null ? section.colors[i] : Colors.OFF;
        }

        protected int colorsLength() {
            return section.colors.length;
        }

        public abstract void update();
    }

    /**** Preset modes for handling LEDs. Add more here. Assume fresh instances.****/
    public enum Modes {
        STATIC,
        RUNNING,
        BOUNCING;

        public Mode getNewMode() {
            switch (this) {
                case STATIC:
                default: return new Static();
                case RUNNING: return new Running();
                case BOUNCING: return new Bouncing();

            }
        }

        // A simple mode to set the entire section to a single color.
        private static class Static extends Mode {
            public void update() {
                for (int i = section.getBegin(); i < section.getEnd(); i++) {
                    leds[i] = color(0);
                }
            }
        }

        private static class Running extends Mode {
            private int offset = 0;

            public void update() {
                for (int j = section.getBegin(); j < section.getEnd(); j++) {
                    leds[j] = color((j + offset) % colorsLength());
                }
                offset = (offset + 1) % colorsLength();
                Log.out.println("Updated!" + offset);
            }
        }

        private static class Bouncing extends Mode {
            // Only use on LOWER
            private final int MIN = RIGHT.getBegin();
            private final int MAX = LEFT.getEnd() - 1;
            private final int ACCENT = 0;
            private final int BACKGROUND = 1;

            private int position = MIN;
            private int direction = 1;

            public void update() {
                position = position + direction;
                direction = position > MAX || position < MIN ? -direction : direction;

                for (int i = section.getBegin(); i < section.getEnd(); i++) {
                    leds[i] = i == position ? color(ACCENT) : color(BACKGROUND);
                }
            }
        }
    }

    private static ElapsedTime runtime = new ElapsedTime();
    public static void update() {
        if (!initialized) return;

        if (runtime.seconds() > 0.1) {
            LEDSection.updateAll();
            setLEDs(leds);
            runtime.reset();
        }

    }

    // Push the internal led buffer to the LEDs
    private static void setLEDs(LEDColor[] leds) {
        byte[] data = new byte[(leds.length + 2) * 4];
        byte[] bytes;
        for (int i = 0; i < leds.length; i++) { // an LED update frame starts with four bytes of zeroes followed by frames of four bytes for each led.
            bytes = leds[i].getBytes();
            data[(i*4) + 4] = bytes[0];
            data[(i*4) + 5] = bytes[1];
            data[(i*4) + 6] = bytes[2];
            data[(i*4) + 7] = bytes[3];
        }

        data[data.length - 1] = data[data.length - 2] = data[data.length - 3] = data[data.length - 4] = (byte)0xFF; // and ends with four bytes of 0xFF

        //Log.out.println(Arrays.toString(data));
        for (int j = 0; j < data.length; j += 99) {
            byte[] chunk = Arrays.copyOfRange(data, j, j + 99 > data.length ? data.length : j + 99);
            //Log.out.println(Arrays.toString(chunk));
            //Log.out.println(data.length);
            //Log.out.println(j);
            //Log.out.flush();
            ledController.write(0x01, chunk);
            //Log.out.println("Sent!");
            //Log.out.println(j + 99 > data.length);
            //Log.out.flush();
            try {Thread.sleep(10);} catch (Exception e) {} // delay a tiny bit. The rev hub gets angry if you send data too fast
            if (j + 99 > data.length) break;
        }

        Log.out.println("done!");
        Log.out.flush();

        //ledController.write(0x01, Arrays.copyOfRange(data, 0, 99));

        // 0x01 is the function ID for SPI write
    }
}
