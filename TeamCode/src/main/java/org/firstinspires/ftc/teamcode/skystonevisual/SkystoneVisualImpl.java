package org.firstinspires.ftc.teamcode.skystonevisual;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.vuforia.CameraDevice;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Visual Assembly adapted from last year's code
 *
 * Too crazy to comment fully now. Go ahead and try to understand it.
 *
 * Created by Gregory on 9/14/18.
 */

@TeleOp(name="SkystoneSeer")
public class SkystoneVisualImpl extends SkystoneVisual {

    private VuforiaLocalizer vuforia;
    private ViewGroup parentView;
    private ImageView cameraView;
    private ImageView resultView;
    private final int RGB565 = 1; // For OnBotJava

    @Override
    public void init() {
        telemetry.addLine("Initializing");
        telemetry.addData("Debug", SkystoneVisual.DEBUG);
        telemetry.update();
        // Init the VuforiaLocalizer parameters object with the camera View ID
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(); // to see the view, add com.qualcomm.ftcrobotcontroller.R.id.cameraMonitorViewId as param

        // Set the Back camera active
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        // License Key obtained from Vuforia website
        params.vuforiaLicenseKey = "AW4kxuX/////AAABmWnW0/DqgEs8kRwViHNSLjQlrOE4Ex3RORgHderUeAWaZPXghelag4YUic5m3tlzcOEkQj2rPEzLVdfOODR8jfCq6Prwn4boVu2VkjTjWw8jaEWc6R/1VaO3pCaBIFBpOVpJFbXR3Pj3wyTF1W++C9xQOlrU8frWAYQPXGIwJrPy8GH2cFeOHx45F3FPw8cNX2qCldOS+PCHXXmuCfLihbUIfCI2S6+i89czmPLvzN4NTfeknNEbj6XG9/sj7SU5tYECuCToREkzEUjQU91zkPAvvCV1c1A4+CA7+TUNw7yUYyGgTlAEqg9+UiHkslxtYDr1kJMb9Fec8guAs8c4ZS472fMTTLix+CNbCdlJsDvY";

        // Display axes on top of visual matches
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        // Create Vuforia instance with params: -- takes 1-2s
        vuforia = ClassFactory.getInstance().createVuforia(params);

        // Save an rgb565 image for further processing each frame and only save current frame
        Vuforia.setFrameFormat(RGB565, true);
        vuforia.setFrameQueueCapacity(1);

        if (SkystoneVisual.DEBUG) {
            AppUtil.getInstance().synchronousRunOnUiThread(new Runnable() {
                @Override
                public void run() {
                    parentView = (ViewGroup) AppUtil.getInstance().getActivity().findViewById(hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
                    parentView.removeAllViews();
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
                    parentView.setVisibility(View.VISIBLE);
                }
            });
        }

        if (SkystoneVisual.SAVE) {
            File mineralFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Pictures/minerals/");
            File files[] = mineralFolder.listFiles();
            for (File file : files) {
                file.delete();
            }
        }

        // Flash On:
        //CameraDevice.getInstance().setFlashTorchMode(true);
        // Unable to use Vuforia Targets for minerals :(
    }

    public int isSkystone(boolean save) throws InterruptedException {
        return isSkystone(save, -1, -1);
    }

    @Override
    public int isSkystone(boolean save, int print_x, int print_y) throws InterruptedException {
        return -1;
    }

    public SkystonePosition findSkystone() throws InterruptedException {
        VuforiaLocalizer.CloseableFrame frame = vuforia.getFrameQueue().take();
        SkystonePosition pos = SkystonePosition.UNKNOWN;


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
                int WIDTH = 30;
                int HEIGHT = 20;
                //telemetry.addLine("Modified Width: " + WIDTH);
                //telemetry.addLine("Modified Height: " + HEIGHT);

                // Scale the Bitmap to a smaller, more reasonable size. (src, width, height, filter?)
                final Bitmap outBmp = Bitmap.createScaledBitmap(srcBmp, WIDTH, HEIGHT, false);



                final Bitmap resBmp = Bitmap.createBitmap(outBmp);
                resBmp.eraseColor(Color.BLACK);

                // Left, Right
                int[] yellowCount = {1, 1, 1}, whiteCount = {1, 1, 1};
                boolean[] areYellow = {false, false, false};
                double[] hsv = new double[3];
                for (int y = 0; y < HEIGHT; y++) {
                    for (int x = 0; x < WIDTH; x++) {

                        colorToHSV(outBmp.getPixel(x, y), hsv);
                        if (hsv[0] >= minYellow[0] && hsv[0] <= maxYellow[0] &&
                                hsv[1] >= minYellow[1] && hsv[1] <= maxYellow[1] &&
                                hsv[2] >= minYellow[2] && hsv[2] <= maxYellow[2]) {
                            yellowCount[(x / (WIDTH/3))]++;
                            resBmp.setPixel(x, y, Color.YELLOW);

                        } else {
                            if (x < (WIDTH / 3)) {
                                resBmp.setPixel(x, y, Color.DKGRAY);
                            } else if (x >= (2*WIDTH/3)){
                                resBmp.setPixel(x, y, Color.DKGRAY);
                            } else {
                                resBmp.setPixel(x, y, Color.GRAY);
                            }
                        }
                    }
                }

                telemetry.addData("Yellow Left", yellowCount[2]);
                telemetry.addData("Yellow Center", yellowCount[1]);
                telemetry.addData("Yellow Right", yellowCount[0]);


                Log.d("Yellow Left", yellowCount[2] + "");
                Log.d("Yellow Center", yellowCount[1] + "");
                Log.d("Yellow Right", yellowCount[0] + "");

                if(yellowCount[0]<yellowCount[1]&&yellowCount[0]<yellowCount[2]){
                    pos = SkystonePosition.RIGHT;
                } else if (yellowCount[2]<yellowCount[0]&&yellowCount[2]<yellowCount[1]){
                    pos = SkystonePosition.LEFT;
                } else {
                    pos = SkystonePosition.CENTER;
                }


                /*for (int j = 0; j < 3; j++) {
                    areYellow[j] = (yellowCount[j] / whiteCount[j] > 1);
                }

                if (areYellow[0] && areYellow[1]) {
                    scissorpos = (yellowCount[0] > yellowCount[1] ? SkystonePosition.LEFT : SkystonePosition.CENTER);
                } else {
                    scissorpos = (areYellow[0] ? SkystonePosition.LEFT : (areYellow[1] ? SkystonePosition.CENTER : ((yellowCount[0] + whiteCount[0] < 5 || yellowCount[1] + whiteCount[1] < 5) ? SkystonePosition.UNKNOWN : SkystonePosition.RIGHT)));
                }*/

                telemetry.addData("The Skystone is on the", pos.toString());
                Log.d("The Skystone is on", pos.toString());
                telemetry.update();


                if (SkystoneVisual.SAVE) {
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

                if (SkystoneVisual.DEBUG) {
                    AppUtil.getInstance().synchronousRunOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cameraView.setImageBitmap(Bitmap.createScaledBitmap(outBmp, 1000, 600, false));
                            resultView.setImageBitmap(Bitmap.createScaledBitmap(resBmp, 600, 600, false));
                        }
                    });
                }
            }
        }
        return pos;
    }

    /*private int valueTest(int color) {
        return Color.blue(color);
    }*/

    public void stop() {
        if (SkystoneVisual.DEBUG) {
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


    public void loop() {
        try {
            findSkystone();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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