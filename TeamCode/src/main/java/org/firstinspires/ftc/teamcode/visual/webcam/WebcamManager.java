package org.firstinspires.ftc.teamcode.visual.webcam;

import android.graphics.Bitmap;
import android.graphics.PixelFormat;

import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.android.util.Size;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureRequest;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureSequenceId;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureSession;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraFrame;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraManager;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.internal.camera.CameraManagerInternal;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import org.firstinspires.ftc.robotcore.internal.system.Tracer;

import java.util.concurrent.TimeUnit;

public class WebcamManager implements CameraCaptureSession.StateCallback {
    private Camera camera;
    private CameraManager cameraManager;
    private final Size FRAME_SIZE = new Size(1920, 1080);
    private final int FPS = 5;

    public void startCapture(WebcamName webcam) {
        cameraManager = ClassFactory.getInstance().getCameraManager();
        camera = cameraManager.requestPermissionAndOpenCamera(new Deadline(1, TimeUnit.MINUTES), webcam, null);
        try {
            camera.createCaptureSession(Continuation.create(((CameraManagerInternal) cameraManager).getSerialThreadPool(), this));

        } catch (Exception e) {
            RobotLog.ee("ERROR CREATING CAPTURE!", e.getMessage());
        }

    }

    /**** Camera Capture Session ****/

    private volatile Bitmap currentFrame = Bitmap.createBitmap(FRAME_SIZE.getWidth(), FRAME_SIZE.getHeight(), Bitmap.Config.RGB_565);

    public Bitmap getCurrentFrame() {
        return currentFrame.copy(Bitmap.Config.RGB_565, false);
    }

    @Override
    public void onConfigured(CameraCaptureSession session) {
        try {

            CameraCaptureRequest captureRequest = camera.createCaptureRequest(PixelFormat.RGB_565, FRAME_SIZE, FPS);
            session.startCapture(captureRequest, new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onNewFrame(CameraCaptureSession session, CameraCaptureRequest request, CameraFrame cameraFrame) {
                    cameraFrame.copyToBitmap(currentFrame);
                }
            }, Continuation.create(((CameraManagerInternal) cameraManager).getSerialThreadPool(), new CameraCaptureSession.StatusCallback()
            {
                @Override public void onCaptureSequenceCompleted(CameraCaptureSession session, CameraCaptureSequenceId cameraCaptureSequenceId, long lastFrameNumber)
                {
                    Tracer.create("WebcamManager", true).trace("capture sequence %s reports completed: lastFrame=%d", cameraCaptureSequenceId, lastFrameNumber);
                }
            }));
        } catch (Exception e) {
            RobotLog.ee("ERROR CREATING CAPTURE!", e.getMessage());
        }

    }

    @Override
    public void onClosed(CameraCaptureSession session) {
        session.stopCapture();
    }
}
