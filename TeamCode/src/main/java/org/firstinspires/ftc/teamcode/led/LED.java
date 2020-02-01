package org.firstinspires.ftc.teamcode.led;

import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

import java.util.ArrayList;
import java.util.Arrays;
import com.qualcomm.robotcore.util.ElapsedTime;



// Control all the LEDs!!!
public class LED {
    private static I2cDeviceSynch ledController;
    private static Color[] leds = new Color[30];
    private static byte currentColor = 0;

    /**** Color Translation - HEX RGB and brightness to DotStar byte field ****/
    public static class Color {
        byte[] bytes = new byte[4];
        // raw: 111(5 bit brightness) R G B
        public Color(long raw) {
            bytes[0] = (byte) (raw & 0xFF);
            bytes[1] = (byte) (( raw >> 8) & 0xFF);
            bytes[2] = (byte) (( raw >> 16) & 0xFF);
            bytes[3] = (byte) (( raw >> 24) & 0xFF);
        }

        // brightness 0-31, standard hex color (0xFF00FF)
        public Color(int color, int brightness) {
            bytes[0] = (byte) (brightness | 0xE0);
            bytes[1] = (byte) (color & 0xFF); // R
            bytes[2] = (byte) (( color >> 8) & 0xFF); // G
            bytes[3] = (byte) (( color >> 16) & 0xFF); // B
        }

        public byte[] getBytes() {
            return bytes;
        }

        private int getColor() {
            return ((int) bytes[1] & 0xFF) | (((int) bytes[2] & 0xFF) << 8) | (((int) bytes[3] & 0xFF) << 16);
        }

        private int getBrightness() {
            return ((int) bytes[0]) & 0x1F;
        }

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
            leds[j] = Color.OFF;
        }
        setAllModes(new Modes.Static(Color.OFF));
    }

    /**** Utility methods for processing the led buffer. Add more utility methods here. ****/
    public static class Utils {
        public static void fill(Color color, Section section) {
            for (int i = section.getBegin(); i < section.getEnd(); i++) {
                leds[i] = color;
            }
        }
    }

    /**** Sections. Since there are four main sections of the robot, this keeps track of the mode and location of each in the led buffer ****/
    public static class Section {
        private int begin, length; // the beginning index and the length of this section
        private static int nextLED = 0; // the next available led not claimed yet
        private static ArrayList<Section> sections = new ArrayList<>(); // the internal array for looping over all the sections to update
        private Mode mode = new Modes.Static(Color.OFF); // Which mode this section is in. Defaults to static off.
        // Construct a section from a length
        Section(int length) {
            this.begin = nextLED; // this should start at the next available led
            this.length = length; // save the length
            nextLED += length;    // and update the next available led
            sections.add(this);   // and add this to the internal array for updating
        }

        // These are made specifically for for loops. Ex: for (int i = getBegin(); i < getEnd(); i++) {}
        public int getBegin() { return begin; } // for looping: returns the first index
        public int getEnd() { return begin + length; } // for looping: returns the last index (exclusive)

        // Set the mode of this section
        public void setMode(Mode mode) {
            this.mode = mode;
            mode.init(this); // initialize it (update the leds with the initial conditions of this mode)
        }

        public void update() {
            mode.update(this); // update (pass it on to the mode to update the leds)
        }

        // Update all the sections!
        public static void updateAll() {
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
        public void setMode(Mode mode) {
            for (int i = 0; i < sections.length; i++) {
                sections[i].setMode(mode);
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

    public static void setAllModes(Mode mode) {
        for (int i = 0; i < Section.sections.size(); i++) {
            Section.sections.get(i).setMode(mode.copy()); // make a copy so there's a fresh instance for each section (for patterned modes)
        }
    }

    /**** Abstract Mode for controlling the leds ****/
    public static abstract class Mode {
        public abstract void init(Section section);
        public void update(Section section) {}
        private Mode copy() {return this;} // return a fresh copy. Can return itself if it doesn't matter.
    }

    /**** Preset modes for handling LEDs. Add more here. Assume fresh instances.****/
    public static class Modes {

        // A simple mode to set the entire section to a single color.
        public static class Static extends Mode {
            private Color color;
            Static(Color color) { this.color = color; }

            public void init(Section section) {
                Utils.fill(color, section);
                Log.out.println("Color!: " +  color.getColor());
                Log.out.println("Green: " + ((int) 0x00FF00));
                Log.out.println("Red: " + ((int) 0xFF0000));
                Log.out.println("Brightness: " + color.getBrightness());
            }

            // Example of copy: this mode specifically doesn't need it.
            /*
            public Mode copy() {
                return new Static(color);
            }
            */
        }

        public static class Running extends Mode {
            private Color[] colors;
            private int offset = 0;

            public Running(Color ...colors) {
                this.colors = colors;
            }

            public void init(Section section) {
                for (int j = section.getBegin(); j < section.getEnd(); j++) {
                    leds[j] = colors[j % colors.length];
                }
            }

            public void update(Section section) {
                for (int j = section.getBegin(); j < section.getEnd(); j++) {
                    leds[j] = colors[(j + offset) % colors.length];
                }
                offset = (offset + 1) % colors.length;
                Log.out.println("Updated!" + offset);
            }
        }

        public static class Bouncing extends Mode {
            // Only use on LOWER.
            private Color accent, background;
            public Bouncing(Color accent, Color background) {
                this.accent = accent;
                this.background = background;
            }

            private final int MIN = RIGHT.getBegin();
            private final int MAX = LEFT.getEnd() - 1;

            private int position = MIN;
            private int direction = 1;

            public void init(Section section) {
                for (int i = section.getBegin(); i < section.getEnd(); i++) {
                    leds[i] = i == position ? accent : background;
                }
            }

            public void update(Section section) {
                if (section == RIGHT) { // Only update on the right update so we don't jump 3 times
                    leds[position] = background;
                    position = position + direction;
                    direction = position > MAX || position < MIN ? -direction : direction;
                    leds[position] = accent;
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
    private static String setLEDs(Color[] leds) {
        String log = "";
        byte[] data = new byte[(leds.length + 2) * 4];
        byte[] bytes;
        for (int i = 0; i < leds.length; i++) { // an LED update frame starts with four bytes of zeroes followed by frames of four bytes for each led.
            bytes = leds[i].getBytes();
            data[(i*4) + 4] = bytes[0];
            data[(i*4) + 5] = bytes[1];
            data[(i*4) + 6] = bytes[2];
            data[(i*4) + 7] = bytes[3];
        }

        for ( int j = 0; j < data.length; j++) {
            log += data[j] + ", ";
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
        return log; // logging to know what was sent
    }
}
