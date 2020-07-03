package org.photonvision.vision.frame.consumer;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.cameraserver.CameraServer;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.photonvision.vision.frame.Frame;
import org.photonvision.vision.frame.FrameConsumer;
import org.photonvision.vision.frame.FrameDivisor;

public class MJPGFrameConsumer implements FrameConsumer {

    private final CvSource cvSource;
    private final MjpegServer mjpegServer;
    private FrameDivisor divisor = FrameDivisor.NONE;

    public MJPGFrameConsumer(String sourceName, int width, int height) {
        // TODO h264?
        this.cvSource = new CvSource(sourceName, VideoMode.PixelFormat.kMJPEG, width, height, 30);
        this.mjpegServer = CameraServer.getInstance().startAutomaticCapture(cvSource);
    }

    public MJPGFrameConsumer(String name) {
        this(name, 320, 240);
    }

    public void setFrameDivisor(FrameDivisor divisor) {
        this.divisor = divisor;
    }

    @Override
    public void accept(Frame frame) {
        if (!frame.image.getMat().empty()) {
            if (divisor != FrameDivisor.NONE) {
                var tempMat = new Mat();
                Imgproc.resize(
                        frame.image.getMat(), tempMat, getScaledSize(frame.image.getMat().size(), divisor));
                cvSource.putFrame(tempMat);
                tempMat.release();
            } else {
                cvSource.putFrame(frame.image.getMat());
            }
        }
    }

    private Size getScaledSize(Size orig, FrameDivisor divisor) {
        return new Size(orig.width / divisor.value, orig.height / divisor.value);
    }

    public int getCurrentStreamPort() {
        return mjpegServer.getPort();
    }
}
