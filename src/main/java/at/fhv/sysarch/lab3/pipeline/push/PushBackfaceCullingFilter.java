package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;

public class PushBackfaceCullingFilter extends Push<Face, Face> {

    public PushBackfaceCullingFilter(IPush<Face> successor) {
        super(successor);
    }

    @Override
    public void push(Face face) {
        if (!needsCulling(face)) {
            successor.push(face);
        }
    }

    private boolean needsCulling(Face face) {
        float v1DotProduct = face.getV1().dot(face.getN1());
        float v2DotProduct = face.getV2().dot(face.getN2());
        float v3DotProduct = face.getV3().dot(face.getN3());

        return (v1DotProduct > 0 || v2DotProduct > 0 || v3DotProduct > 0);
    }
}
