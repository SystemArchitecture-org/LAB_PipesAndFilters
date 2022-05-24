package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class PushProjectTransformationFilter extends Push<Pair<Face, Color>, Pair<Face, Color>> {

    private final Mat4 transformation;

    public PushProjectTransformationFilter(IPush<Pair<Face, Color>> successor, PipelineData pd) {
        super(successor);
        this.transformation = pd.getProjTransform();
    }

    @Override
    public void push(Pair<Face, Color> pair) {
        successor.push(applyTransformation(pair));
    }

    private Pair<Face, Color> applyTransformation(Pair<Face, Color> pair) {

        return new Pair<>(
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
