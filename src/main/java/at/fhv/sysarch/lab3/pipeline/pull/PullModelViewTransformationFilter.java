package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import com.hackoeur.jglm.Mat4;

public class PullModelViewTransformationFilter<I extends Face> extends Pull<I, Face> {

    private final PipelineData pd;
    private Mat4 rotationMatrix;

    public PullModelViewTransformationFilter(IPull<I> predecessor, PipelineData pd) {
        super(predecessor);
        this.pd = pd;

    }

    @Override
    public Face pull() {
        Face face = predecessor.pull();
        return rotate(face);
    }

    private Face rotate(Face face) {
        if (rotationMatrix == null) {
            throw new IllegalStateException("No Rotation set :( - filter sad");
        }

        return new Face(
                rotationMatrix.multiply(face.getV1()),
                rotationMatrix.multiply(face.getV2()),
                rotationMatrix.multiply(face.getV3()),
                rotationMatrix.multiply(face.getN1()),
                rotationMatrix.multiply(face.getN2()),
                rotationMatrix.multiply(face.getN3())
        );
    }

    public void setRotationMatrix(Mat4 newRotation) {
        this.rotationMatrix = pd.getViewTransform().multiply(pd.getModelTranslation()).multiply(newRotation);
    }
}
