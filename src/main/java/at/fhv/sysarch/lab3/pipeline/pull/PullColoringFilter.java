package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class PullColoringFilter<I extends Face> extends Pull<I, Pair<Face, Color>> {

    private final PipelineData pd;

    public PullColoringFilter(IPull<I> predecessor, PipelineData pd) {
        super(predecessor);
        this.pd = pd;
    }

    @Override
    public Pair<Face, Color> pull() {
        return new Pair<>(predecessor.pull(), pd.getModelColor());
    }
}
