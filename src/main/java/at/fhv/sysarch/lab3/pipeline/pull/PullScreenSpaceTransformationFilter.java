package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class PullScreenSpaceTransformationFilter extends Pull<Pair<Face, Color>, Pair<Face, Color>> {

    private Mat4 transformation;

    public PullScreenSpaceTransformationFilter(IPull<Pair<Face, Color>> predecessor, PipelineData pd) {
        super(predecessor);
        this.transformation = pd.getViewportTransform();
    }

    @Override
    public Pair<Face, Color> pull() {
        return transform(predecessor.pull());
    }

    private Pair<Face, Color> transform(Pair<Face, Color> pair) {

        return new Pair<>(
                new Face(
                        applyTransformation(pair.fst().getV1()),
                        applyTransformation(pair.fst().getV2()),
                        applyTransformation(pair.fst().getV3()),
                        pair.fst()
                ),
                pair.snd()
        );
    }

    private Vec4 applyTransformation(Vec4 vec) {
        return transformation.multiply(vec.multiply(1f / vec.getW()));
    }
}
