package org.photonvision.vision.pipeline;

import org.photonvision.vision.frame.Frame;
import org.photonvision.vision.frame.FrameStaticProperties;
import org.photonvision.vision.pipeline.result.CVPipelineResult;
import org.photonvision.vision.processes.PipelineManager;

public class Calibration3dPipeline extends CVPipeline<CVPipelineResult, CVPipelineSettings> {

    // TODO: Everything here

    public Calibration3dPipeline() {
        settings = new CVPipelineSettings();
        settings.pipelineIndex = PipelineManager.CAL_3D_INDEX;
    }

    @Override
    protected void setPipeParams(
            FrameStaticProperties frameStaticProperties, CVPipelineSettings settings) {}

    @Override
    protected CVPipelineResult process(Frame frame, CVPipelineSettings settings) {
        return null;
    }
}
