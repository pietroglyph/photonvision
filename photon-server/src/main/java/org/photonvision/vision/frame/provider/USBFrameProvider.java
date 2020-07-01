package org.photonvision.vision.frame.provider;

import edu.wpi.cscore.CvSink;
import org.photonvision.vision.frame.Frame;
import org.photonvision.vision.frame.FrameProvider;
import org.photonvision.vision.frame.FrameStaticProperties;
import org.photonvision.vision.opencv.CVMat;

public class USBFrameProvider implements FrameProvider {
    private final CvSink cvSink;
    private final FrameStaticProperties frameStaticProperties;
    private final CVMat mat;

    public USBFrameProvider(CvSink sink, FrameStaticProperties frameStaticProperties) {
        cvSink = sink;
        cvSink.setEnabled(true);
        this.frameStaticProperties = frameStaticProperties;
        mat = new CVMat();
    }

    @Override
    public Frame get() {
        if (mat.getMat() != null) {
            mat.release();
        }
        long time = cvSink.grabFrame(mat.getMat());
        return new Frame(mat, time, frameStaticProperties);
    }

    @Override
    public String getName() {
        return "USBFrameProvider - " + cvSink.getName();
    }
}
