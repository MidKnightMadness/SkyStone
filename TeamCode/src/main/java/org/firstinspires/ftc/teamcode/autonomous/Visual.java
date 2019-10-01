package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vuforia.CameraDevice;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import java.io.FileOutputStream;

public class Visual extends OpMode {
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
    public static boolean DEBUG = true;

    public static double minYellow[] = {20, 0.5, 0.3};
    public static double maxYellow[] = {62, 1, 1};
    public static double minWhite[] = {0, 0, 0.8};
    public static double maxWhite[] = {255, 0.2, 1};

    private VuforiaLocalizer vuforia;
    private ViewGroup parentView;
    private ImageView cameraView;
    private ImageView resultView;
    private final int RGB565 = 1;

    public void init() {
        telemetry.addLine("Initializing");
        telemetry.addData("Debug", DEBUG);
        telemetry.update();

        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters();

        // Set the Back camera active
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        // License Key obtained from Vuforia website
        params.vuforiaLicenseKey = "ATqSvW7/////AAAAGSbUe3emc0NmiwFnhuicf+c4388daBpHukK2NzjANrVvP6h1rJRTExnNRD8RBZJqsu5tuVVn+AuayqO2UEZbxp0+ZUbFnXPssyKkV4q8YmYB4AkxHwaJCIxCdd1cCWR9F0xuvve5OOzddkh13v/3T1DSh7vrBuFHurMZF8SLQrwQqMf5ubyit0BRHbtX5GLWwm6hCEOX8ZykrK0UbA8+kyGwSqkWbb5IjUMQrlQpItk1emrxo0S2Mj7z+LCNXBNw9wPvTs4TXnpHvcA/7W0vGFxnbXUcUArUBztNHywpD+rVHjFZYuGJwMsWfHAFKH/OfePAstqGnl3GSJjCrEJqVujQo1cqmC7NmyWd2gxPnqHK";

        // Display axes on top of visual matches
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        // Create Vuforia instance with params: -- takes 1-2s
        vuforia = ClassFactory.getInstance().createVuforia(params);

        // Save an rgb565 image for further processing each frame and only save current frame
        Vuforia.setFrameFormat(RGB565, true);
        vuforia.setFrameQueueCapacity(1);

        if (Visual.DEBUG) {
            AppUtil.getInstance().synchronousRunOnUiThread(new Runnable() {
                @Override
                public void run() {
                    parentView = (ViewGroup) AppUtil.getInstance().getActivity().findViewById(hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
                    cameraView = new ImageView(AppUtil.getInstance().getApplication().getApplicationContext());
                    Bitmap image = Bitmap.createBitmap(1000, 600, Bitmap.Config.RGB_565);
                    image.eraseColor(Color.GREEN);
                    cameraView.setImageBitmap(image);
                    parentView.addView(cameraView);
                    cameraView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    cameraView.setRotation(-90);
                    resultView = new ImageView(AppUtil.getInstance().getApplication().getApplicationContext());
                    resultView.setImageBitmap(image);
                    parentView.addView(resultView);
                    resultView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    resultView.setRotation(-90);
                }
            });
        }

        // Flash On:
        CameraDevice.getInstance().setFlashTorchMode(true);

    }

    @Override
    public void loop() {}

    public int isGoldMineral(boolean save) throws InterruptedException {
        return isGoldMineral(save, -1, -1);
    }
    public int isGoldMineral(boolean save, int print_x, int print_y) throws InterruptedException {
        VuforiaLocalizer.CloseableFrame frame = vuforia.getFrameQueue().take();
        int isYellow = -1;

        // One frame contains multiple image formats. Loop through all formats to find RGB565
        for (int i = 0; i < frame.getNumImages(); i++) {
            if (frame.getImage(i).getFormat() == RGB565) {

                // Make a Bitmap object out of the vuforia frame (vertically mirrored)
                Bitmap unflippedBmp = Bitmap.createBitmap(frame.getImage(i).getWidth(), frame.getImage(i).getHeight(), Bitmap.Config.RGB_565);
                unflippedBmp.copyPixelsFromBuffer(frame.getImage(i).getPixels());

                Matrix m = new Matrix();
                m.postScale(-1, -1);
                Bitmap srcBmp = Bitmap.createBitmap(unflippedBmp, 0, 0, unflippedBmp.getWidth(), unflippedBmp.getHeight(), m, false);
                // Output the height and width of the source image
                //telemetry.addLine("Height: " + srcBmp.getHeight());
                //telemetry.addLine("Width: " + srcBmp.getWidth());
                int WIDTH = 20;
                int HEIGHT = 12;
                //telemetry.addLine("Modified Width: " + WIDTH);
                //telemetry.addLine("Modified Height: " + HEIGHT);

                // Scale the Bitmap to a smaller, more reasonable size. (src, width, height, filter?)
                final Bitmap outBmp = Bitmap.createScaledBitmap(srcBmp, WIDTH, HEIGHT, false);



                final Bitmap resBmp = Bitmap.createBitmap(outBmp);
                resBmp.eraseColor(Color.BLACK);


                double yellowCount = 1, whiteCount = 1;
                double[] hsv = new double[3];
                for (int y = 0; y < HEIGHT; y++) {
                    for (int x = 0; x < WIDTH; x++) {

                        colorToHSV(outBmp.getPixel(x, y), hsv);
                        if (hsv[0] >= minYellow[0] && hsv[0] <= maxYellow[0] &&
                                hsv[1] >= minYellow[1] && hsv[1] <= maxYellow[1] &&
                                hsv[2] >= minYellow[2] && hsv[2] <= maxYellow[2]) {
                            yellowCount++;
                            resBmp.setPixel(x, y, Color.YELLOW);

                        } else if (hsv[0] >= minWhite[0] && hsv[0] <= maxWhite[0] &&
                                hsv[1] >= minWhite[1] && hsv[1] <= maxWhite[1] &&
                                hsv[2] >= minWhite[2] && hsv[2] <= maxWhite[2]) {
                            whiteCount++;
                            resBmp.setPixel(x, y, Color.WHITE);
                        }
                    }
                }

                if (yellowCount + whiteCount > 5) {
                    if (yellowCount / whiteCount > 1) {
                        isYellow = 1;
                    } else {
                        isYellow = 0;
                    }
                }

                if (print_x != -1 && print_y != -1) {
                    colorToHSV(outBmp.getPixel(print_x, print_y), hsv);
                    telemetry.addData("Selected", "H: %3f, S: %3f, V: %3f", hsv[0], hsv[1], hsv[2]);
                    resBmp.setPixel(print_x, print_y, Color.CYAN);
                    if (print_x > 0) {outBmp.setPixel(print_x - 1, print_y, Color.CYAN);}
                    if (print_x < 19) {outBmp.setPixel(print_x + 1, print_y, Color.CYAN);}
                    if (print_y > 0) {outBmp.setPixel(print_x, print_y - 1, Color.CYAN);}
                    if (print_y < 11) {outBmp.setPixel(print_x, print_y + 1, Color.CYAN);}
                }

                //telemetry.update();


                if (save) {
                    try {
                        // Save Src
                        String name = "Output_" + Math.random();
                        File srcFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Pictures/minerals/" + name + "Src.png");
                        FileOutputStream srcStream = new FileOutputStream(srcFile);
                        srcBmp.compress(Bitmap.CompressFormat.PNG, 100, srcStream);
                        srcStream.close();
                        // Save Out
                        File outFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Pictures/minerals/" + name + "Out.png");
                        FileOutputStream outStream = new FileOutputStream(outFile);
                        outBmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                        outStream.close();

                        File resFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Pictures/minerals/" + name + "Res.png");
                        FileOutputStream resStream = new FileOutputStream(resFile);
                        resBmp.compress(Bitmap.CompressFormat.PNG, 100, resStream);
                        resStream.close();
                    } catch (Exception e) {
                        telemetry.addLine(e.toString());
                        telemetry.update();
                    }
                }

                if (Visual.DEBUG) {
                    AppUtil.getInstance().synchronousRunOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cameraView.setImageBitmap(Bitmap.createScaledBitmap(outBmp, 1000, 600, false));
                            resultView.setImageBitmap(Bitmap.createScaledBitmap(resBmp, 1000, 600, false));
                        }
                    });
                }
            }
        }
        telemetry.addData("Is Yellow", isYellow);
        return isYellow;
    }

    /*private int valueTest(int color) {
        return Color.blue(color);
    }*/

    public void stop() {
        if (Visual.DEBUG) {
            AppUtil.getInstance().synchronousRunOnUiThread(new Runnable() {
                @Override
                public void run() {
                    parentView.removeView(cameraView);
                    parentView.removeView(resultView);
                }
            });
        }

        // Flash Off
        CameraDevice.getInstance().setFlashTorchMode(false);
    }

    private void colorToHSV(int color, double[]hsv) {
        double r = Color.red(color) / 255d;
        double b = Color.blue(color) / 255d;
        double g = Color.green(color) / 255d;
        double cmin = Math.min(r, Math.min(b, g));
        double cmax = Math.max(r, Math.max(b, g));
        double delta = cmax - cmin;

        hsv[0] = delta == 0 ? 0 :
                cmax == r ? 60 * ((g-b)/delta) :
                        cmax == g ? 60 * ((b-r)/delta + 2) :
                                60 * ((r-g)/delta + 4);
        hsv[1] = cmax == 0 ? 0 : delta / cmax;
        hsv[2] = cmax;
    }

}
