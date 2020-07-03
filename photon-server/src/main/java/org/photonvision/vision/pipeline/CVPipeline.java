package org.photonvision.vision.pipeline;

import java.util.List;
import org.photonvision.common.util.math.MathUtils;
import org.photonvision.vision.frame.Frame;
import org.photonvision.vision.frame.FrameStaticProperties;
import org.photonvision.vision.pipeline.result.CVPipelineResult;

public abstract class CVPipeline<R extends CVPipelineResult, S extends CVPipelineSettings> {
    protected S settings;

    protected abstract void setPipeParams(FrameStaticProperties frameStaticProperties, S settings);

    protected abstract R process(Frame frame, S settings);

    public S getSettings() {
        return settings;
    }

    // TODO (BANKS) ACTUALLY SET THE CAMERA RESOLUTION
    public R run(Frame frame) {
        long pipelineStartNanos = System.nanoTime();

        if (settings == null) {
            throw new RuntimeException("No settings provided for pipeline!");
        }
        setPipeParams(frame.frameStaticProperties, settings);

        if (frame.image.getMat().empty()) {
            return (R) new CVPipelineResult(0, List.of(), frame);
        }
        R result = process(frame, settings);

        result.setLatencyMillis(MathUtils.nanosToMillis(System.nanoTime() - pipelineStartNanos));

        return result;
    }
}
