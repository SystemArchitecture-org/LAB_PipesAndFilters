package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import com.hackoeur.jglm.Mat4;

public class PushModelViewTransformationFilter extends Push<Face, Face> {

    private final PipelineData pd;
    private Mat4 rotationMatrix;

    public PushModelViewTransformationFilter(IPush<Face> successor, PipelineData pd) {
        super(successor);
        this.pd = pd;

    }

    @Override
    public void push(Face face) {
        successor.push(rotate(face));
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