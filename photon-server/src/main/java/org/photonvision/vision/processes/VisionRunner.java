package org.photonvision.vision.processes;

import java.util.function.Consumer;
import java.util.function.Supplier;
import org.photonvision.common.logging.LogGroup;
import org.photonvision.common.logging.Logger;
import org.photonvision.vision.frame.Frame;
import org.photonvision.vision.frame.FrameProvider;
import org.photonvision.vision.pipeline.CVPipeline;
import org.photonvision.vision.pipeline.result.CVPipelineResult;

/** VisionRunner has a frame supplier, a pipeline supplier, and a result consumer */
@SuppressWarnings("rawtypes")
public class VisionRunner {

    private final Logger logger;
    private final Thread visionProcessThread;
    private final Supplier<Frame> frameSupplier;
    private final Supplier<CVPipeline> pipelineSupplier;
    private final Consumer<CVPipelineResult> pipelineResultConsumer;

    private long loopCount;

    /**
    * VisionRunner contains a <see cref="Thread">Thread</see> to run a pipeline, given a frame, and
    * will give the result to the consumer.
    *
    * @param frameSupplier The supplier of the latest frame.
    * @param pipelineSupplier The supplier of the current pipeline.
    * @param pipelineResultConsumer The consumer of the latest result.
    */
    public VisionRunner(
            FrameProvider frameSupplier,
            Supplier<CVPipeline> pipelineSupplier,
            Consumer<CVPipelineResult> pipelineResultConsumer) {
        this.frameSupplier = frameSupplier;
        this.pipelineSupplier = pipelineSupplier;
        this.pipelineResultConsumer = pipelineResultConsumer;

        visionProcessThread = new Thread(this::update);
        visionProcessThread.setName("VisionRunner - " + frameSupplier.getName());
        logger = new Logger(VisionRunner.class, frameSupplier.getName(), LogGroup.VisionProcess);
    }

    public void startProcess() {
        visionProcessThread.start();
    }

    private void update() {
        while (!Thread.interrupted()) {
            var pipeline = pipelineSupplier.get();
            var frame = frameSupplier.get();

            try {
                var pipelineResult = pipeline.run(frame);
                pipelineResultConsumer.accept(pipelineResult);
            } catch (Exception ex) {
                logger.error("Exception on loop " + loopCount);
                ex.printStackTrace();
            }
            loopCount++;
        }
    }
}
