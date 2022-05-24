package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class PullLightningFilter<I extends Pair<Face, Color>> extends Pull<I, Pair<Face, Color>> {

    private final PipelineData pd;

    public PullLightningFilter(IPull<I> predecessor, PipelineData pd) {
        super(predecessor);
        this.pd = pd;
    }

    @Override
    public Pair<Face, Color> pull() {
        Pair<Face, Color> pair = predecessor.pull();
        double dotProduct = pair.fst().getN1().dot(new Vec4(pd.getLightPos().getUnitVector(), 0));

        return new Pair<>(
                pair.fst(),
                pair.snd().deriveColor(0, 1, dotProduct, 1)
        );
    }
}
