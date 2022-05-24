package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class PushColoringFilter extends Push<Face, Pair<Face, Color>> {

    private final PipelineData pd;

    public PushColoringFilter(IPush<Pair<Face, Color>> successor, PipelineData pd) {
        super(successor);
        this.pd = pd;
    }

    @Override
    public void push(Face face) {
        successor.push(new Pair<>(face, pd.getModelColor()));
    }
}
