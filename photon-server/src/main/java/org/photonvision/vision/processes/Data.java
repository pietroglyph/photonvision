package org.photonvision.vision.processes;

import org.photonvision.vision.opencv.Releasable;
import org.photonvision.vision.pipeline.result.CVPipelineResult;

// TODO replace with CTT's data class
public class Data implements Releasable {
    public CVPipelineResult result;

    @Override
    public void release() {
        result.release();
    }
}
