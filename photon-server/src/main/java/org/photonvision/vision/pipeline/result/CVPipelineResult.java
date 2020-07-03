package org.photonvision.vision.pipeline.result;

import java.util.List;
import org.photonvision.vision.frame.Frame;
import org.photonvision.vision.opencv.Releasable;
import org.photonvision.vision.target.TrackedTarget;

public class CVPipelineResult implements Releasable {
    private double latencyMillis;
    public final double processingMillis;
    public final List<TrackedTarget> targets;
    public final Frame outputFrame;

    public CVPipelineResult(double processingMillis, List<TrackedTarget> targets, Frame outputFrame) {
        this.processingMillis = processingMillis;
        this.targets = targets;

        this.outputFrame = Frame.copyFrom(outputFrame);
    }

    public boolean hasTargets() {
        return !targets.isEmpty();
    }

    public void release() {
        for (TrackedTarget tt : targets) {
            tt.release();
        }
        outputFrame.release();
    }

    public double getLatencyMillis() {
        return latencyMillis;
    }

    public void setLatencyMillis(double latencyMillis) {
        this.latencyMillis = latencyMillis;
    }
}
