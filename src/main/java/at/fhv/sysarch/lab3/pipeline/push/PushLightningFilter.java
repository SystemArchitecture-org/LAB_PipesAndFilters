package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class PushLightningFilter extends Push<Pair<Face, Color>, Pair<Face, Color>> {

    private final PipelineData pd;

    public PushLightningFilter(IPush<Pair<Face, Color>> successor, PipelineData pd) {
        super(successor);
        this.pd = pd;
    }

    @Override
    public void push(Pair<Face, Color> pair) {

        double dotProduct = pair.fst().getN1().dot(new Vec4(pd.getLightPos().getUnitVector(), 0));

        successor.push(new Pair<>(
                pair.fst(),
                pair.snd().deriveColor(0, 1, dotProduct, 1)
        ));
    }
}
