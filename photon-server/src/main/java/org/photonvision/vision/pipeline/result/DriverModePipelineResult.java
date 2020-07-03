package org.photonvision.vision.pipeline.result;

import java.util.List;
import org.photonvision.vision.frame.Frame;

public class DriverModePipelineResult extends CVPipelineResult {
    public DriverModePipelineResult(double latencyMillis, Frame outputFrame) {
        super(latencyMillis, List.of(), outputFrame);
    }
}
