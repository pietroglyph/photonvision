package org.photonvision.vision.frame.consumer;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.cameraserver.CameraServer;
import org.apache.commons.lang3.NotImplementedException;
import org.photonvision.common.configuration.CameraConfiguration;
import org.photonvision.vision.frame.Frame;
import org.photonvision.vision.frame.FrameConsumer;

public class MJPGFrameConsumer implements FrameConsumer {

    private final CvSource cvSource;

    public MJPGFrameConsumer(String sourceName, int width, int height) {
        this.cvSource = CameraServer.getInstance().putVideo(sourceName, width, height);
    }

    @Override
    public void accept(Frame frame) {
        cvSource.putFrame(frame.image.getMat());
    }
}
