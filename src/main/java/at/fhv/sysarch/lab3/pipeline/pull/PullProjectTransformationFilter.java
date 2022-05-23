package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class PullProjectTransformationFilter<I extends Pair<Face, Color>> extends Pull<I, Pair<Face, Color>> {

    private final Mat4 transformation;

    public PullProjectTransformationFilter(IPull<I> predecessor, PipelineData pd) {
        super(predecessor);
        this.transformation = pd.getProjTransform();
    }

    @Override
    public Pair<Face, Color> pull() {
        return applyTransformation(predecessor.pull());
    }

    private Pair<Face, Color> applyTransformation(Pair<Face, Color> pair) {

        return new Pair(
                new Face(
                        transformation.multiply(pair.fst().getV1()),
                        transformation.multiply(pair.fst().getV2()),
                        transformation.multiply(pair.fst().getV3()),
                        pair.fst()
                ),
                pair.snd()
        );
    }
}
