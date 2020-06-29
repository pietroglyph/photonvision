package org.photonvision.vision.frame.consumer;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.cameraserver.CameraServer;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.photonvision.common.configuration.CameraConfiguration;
import org.photonvision.vision.frame.Frame;
import org.photonvision.vision.frame.FrameConsumer;

public class MJPGFrameConsumer implements FrameConsumer {

    private final CvSource cvSource;
    private final MjpegServer mjpegServer;

    public MJPGFrameConsumer(String sourceName, int width, int height) {
        // TODO h264?
        this.cvSource = new CvSource(sourceName, VideoMode.PixelFormat.kMJPEG, width, height, 30);
        this.mjpegServer = CameraServer.getInstance().startAutomaticCapture(cvSource);
    }

    public MJPGFrameConsumer(String name) {
        this(name, 320, 240);
    }

    public void setResolution(Size size) {
        cvSource.setResolution((int) size.width, (int) size.height);
    }

    @Override
    public void accept(Frame frame) {
        cvSource.putFrame(frame.image.getMat());
    }

    public int getCurrentStreamPort() {
        return mjpegServer.getPort();
    }
}
