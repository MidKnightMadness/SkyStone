package org.firstinspires.ftc.teamcode.led;

import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

import java.util.ArrayList;
import java.util.Arrays;
import com.qualcomm.robotcore.util.ElapsedTime;



// Control all the LEDs!!!
public class LED {
    private static I2cDeviceSynch ledController;
    private static Color[] leds = new Color[30];

    /**** Color Translation - HEX RGB and brightness to DotStar byte field ****/
    public static class Color {
        byte[] bytes = new byte[4];
        // raw: 111(5 bit brightness) R G B
        /*public Color(long raw) {
            bytes[0] = (byte) (raw & 0xFF);
            bytes[1] = (byte) (( raw >> 8) & 0xFF);
            bytes[2] = (byte) (( raw >> 16) & 0xFF);
            bytes[3] = (byte) (( raw >> 24) & 0xFF);
        }*/

        // brightness 0-31, standard hex color (0xFF00FF)
        public Color(int color, int brightness) {
            bytes[0] = (byte) (brightness | 0xE0);
            bytes[1] = (byte) (color & 0xFF); // R
            bytes[2] = (byte) (( color >> 8) & 0xFF); // G
            bytes[3] = (byte) (( color >> 16) & 0xFF); // B
        }

        private byte[] getBytes() {
            return bytes;
        }

        public int getColor() {
            return ((int) bytes[1] & 0xFF) | (((int) bytes[2] & 0xFF) << 8) | (((int) bytes[3] & 0xFF) << 16);
        }

        public int getBrightness() {
            return ((int) bytes[0]) & 0x1F;
        }
    }

    public static class Colors {
        /**** Color Presets. Add new colors here. ****/
        public static final Color OFF = new Color(0, 0);
        public static final Color RED = new Color(0xFF0000, 31);
        public static final Color GREEN = new Color(0x00FF00, 31);
        public static final Color BLUE = new Color(0x0000FF, 31);
        public static final Color NAVY = new Color(0x000090, 31);
        public static final Color GOLD = new Color(0xEEEE00, 31);
    }


    // Initialize the LEDs with the LED hardware.
    public static void init(LEDStrip ledStrip) {
        LED.ledController = ledStrip.getDevice();
        for (int j = 0; j < leds.length; j++) {
            leds[j] = Colors.OFF;
        }
    }

    /**** Sections. Since there are four main sections of the robot, this keeps track of the mode and location of each in the led buffer ****/
    public static class Section {
        private int begin, length; // the beginning index and the length of this section
        private static int nextLED = 0; // the next available led not claimed yet
        private static ArrayList<Section> sections = new ArrayList<>(); // the internal array for looping over all the sections to update
        private Color[] colors;
        private Mode mode; // Which mode this section is in. Defaults to static off.
        private Mode lastMode;
        // Construct a section from a length
        Section(int length) {
            this.begin = nextLED; // this should start at the next available led
            this.length = length; // save the length
            nextLED += length;    // and update the next available led
            sections.add(this);   // and add this to the internal array for updating
            set(Modes.STATIC, Colors.OFF);
        }

        // These are made specifically for for loops. Ex: for (int i = getBegin(); i < getEnd(); i++) {}
        private int getBegin() { return begin; } // for looping: returns the first index
        private int getEnd() { return begin + length; } // for looping: returns the last index (exclusive)

        public void set(Modes mode, Color ...colors) {
            set(mode);
            set(colors);
        }

        public void set(Modes mode) {
            lastMode = this.mode;
            this.mode = mode.getNewMode();
            update();
        }

        public void set(Color ...colors) {
            this.colors = colors;
        }

        private void update() {
            mode.update(this); // update (pass it on to the mode to update the leds)
        }

        // Update all the sections!
        private static void updateAll() {
            for (int i = 0; i < sections.size(); i++) {
                sections.get(i).update();
            }
        }
    }

    public static class PseudoSection {
        private Section[] sections;
        private PseudoSection(Section ...sections) {
            this.sections = sections;
        }
        public void set(Modes mode, Color ...colors) {
            set(mode);
            set(colors);
        }
        public void set(Modes mode) {
            for (Section section : sections) {
                section.set(mode);
            }
        }
        public void set(Color ...colors) {
            for (Section section : sections) {
                section.set(colors);
            }
        }
    }

    /**** LED configuration. Set the lengths and make new sections as needed. Order matters. ****/
    public static final Section BACK = new Section(7);
    public static final Section RIGHT = new Section(8);
    public static final Section LOWER_BACK = new Section( 7);
    public static final Section LEFT = new Section(8);

    public static final PseudoSection ALL = new PseudoSection(BACK, RIGHT, LEFT, LOWER_BACK);
    public static final PseudoSection LOWER = new PseudoSection(RIGHT, LEFT, LOWER_BACK);

    /**** Abstract Mode for controlling the leds ****/
    public static abstract class Mode {
        Section section;
        private void setSection(Section section) {
            this.section = section;
        }

        protected Color color(int i) { // prevent null unset colors
            return i < section.colors.length && section.colors[i] != null ? section.colors[i] : Colors.OFF;
        }

        protected int colorsLength() {
            return section.colors.length;
        }

        public abstract void update(Section section);
    }

    /**** Preset modes for handling LEDs. Add more here. Assume fresh instances.****/
    public enum Modes {
        STATIC,
        RUNNING,
        BOUNCING;

        private Mode getNewMode() {
            switch (this) {
                case STATIC:
                default: return new Static();
                case RUNNING: return new Running();
                case BOUNCING: return new Bouncing();

            }
        }

        // A simple mode to set the entire section to a single color.
        private static class Static extends Mode {
            public void update(Section section) {
                for (int i = section.getBegin(); i < section.getEnd(); i++) {
                    leds[i] = color(i);
                }
            }
        }

        private static class Running extends Mode {
            private int offset = 0;

            public void update(Section section) {
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

            public void update(Section section) {
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
        if (runtime.seconds() > 0.1) {
            Section.updateAll();
            setLEDs(leds);
            runtime.reset();
        }

    }

    // Push the internal led buffer to the LEDs
    private static void setLEDs(Color[] leds) {
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

        Log.out.println(Arrays.toString(data));
        for (int j = 0; j < data.length; j += 99) {
            byte[] chunk = Arrays.copyOfRange(data, j, j + 99 > data.length ? data.length : j + 99);
            Log.out.println(Arrays.toString(chunk));
            Log.out.println(data.length);
            Log.out.println(j);
            Log.out.flush();
            ledController.write(0x01, chunk);
            Log.out.println("Sent!");
            Log.out.println(j + 99 > data.length);
            Log.out.flush();
            try {Thread.sleep(10);} catch (Exception e) {} // delay a tiny bit. The rev hub gets angry if you send data too fast
            if (j + 99 > data.length) break;
        }

        Log.out.println("done!");
        Log.out.flush();

        //ledController.write(0x01, Arrays.copyOfRange(data, 0, 99));

        // 0x01 is the function ID for SPI write
    }
}
