package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;

public class PullScreenSpaceTransformationFilter extends Pull<Face, Face> {

    private Mat4 transformation;

    public PullScreenSpaceTransformationFilter(IPull<Face> predecessor, PipelineData pd) {
        super(predecessor);
        this.transformation = pd.getViewportTransform();
    }

    @Override
    public Face pull() {
        return transform(predecessor.pull());
    }

    private Face transform(Face face) {

        return new Face(
                applyTransformation(face.getV1()),
                applyTransformation(face.getV2()),
                applyTransformation(face.getV3()),
                face
        );
    }

    private Vec4 applyTransformation(Vec4 vec) {
        return transformation.multiply(vec.multiply(1f / vec.getW()));
    }
}
