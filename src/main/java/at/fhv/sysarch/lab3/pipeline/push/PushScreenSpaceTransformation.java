package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class PushScreenSpaceTransformation extends Push<Pair<Face, Color>, Pair<Face, Color>> {

    private final Mat4 transformation;

    public PushScreenSpaceTransformation(IPush<Pair<Face, Color>> successor, PipelineData pd) {
        super(successor);
        this.transformation = pd.getViewportTransform();
    }

    private Vec4 applyTransformation(Vec4 vec) {
        return transformation.multiply(vec.multiply(1f / vec.getW()));
    }

    @Override
    public void push(Pair<Face, Color> pair) {
        successor.push(new Pair<>(
                new Face(
                        applyTransformation(pair.fst().getV1()),
                        applyTransformation(pair.fst().getV2()),
                        applyTransformation(pair.fst().getV3()),
                        pair.fst()
                ),
                pair.snd()
        ));
    }
}
