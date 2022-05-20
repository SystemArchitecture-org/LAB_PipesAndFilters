package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import com.hackoeur.jglm.Mat4;

public class PullProjectTransformationFilter<I extends Face> extends Pull<I, Face> {

    private final Mat4 transformation;

    public PullProjectTransformationFilter(IPull<I> predecessor, PipelineData pd) {
        super(predecessor);
        this.transformation = pd.getProjTransform();
    }

    @Override
    public Face pull() {
        return applyTransformation(predecessor.pull());
    }

    private Face applyTransformation(Face face) {

        return new Face(
                transformation.multiply(face.getV1()),
                transformation.multiply(face.getV2()),
                transformation.multiply(face.getV3()),
                face
        );
    }
}
