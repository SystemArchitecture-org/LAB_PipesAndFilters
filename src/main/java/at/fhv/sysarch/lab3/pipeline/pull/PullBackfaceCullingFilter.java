package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Vec4;

import java.util.ArrayDeque;
import java.util.Queue;

public class PullBackfaceCullingFilter<I extends Face> extends Pull<I, Face> {

//    private Face nextFace = null;
//
//    public PullBackfaceCullingFilter(IPull<I> source) {
//        super(source);
//    }
//
//    @Override
//    public Face pull() {
//        prepareNext();
//        var temp = nextFace;
//        nextFace = null;
//        return temp;
//    }
//
//    @Override
//    public boolean hasNext() {
//        prepareNext();
//        return nextFace != null;
//    }
//
//    private void prepareNext() {
//        while(predecessor.hasNext() && nextFace == null) {
//            Face f = predecessor.pull();
//            if (f.getV1().dot(f.getN1()) < 0) {
//                nextFace = f;
//            }
//        }
//    }


    public PullBackfaceCullingFilter(IPull<I> predecessor) {
        super(predecessor);
    }

    @Override
    public Face pull() {
        Face face = predecessor.pull();

        while (predecessor.hasNext()) {
            if (needsCulling(face)) {
                face = predecessor.pull();
            } else {
                return face;
            }
        }
        if (needsCulling(face)) {
            return new Face(new Vec4(0,0,0,0),new Vec4(0,0,0,0),new Vec4(0,0,0,0),new Vec4(0,0,0,0),new Vec4(0,0,0,0),new Vec4(0,0,0,0));
        }
        return face;
    }

    private boolean needsCulling(Face face) {
        float v1DotProduct = face.getV1().dot(face.getN1());
        float v2DotProduct = face.getV2().dot(face.getN2());
        float v3DotProduct = face.getV3().dot(face.getN3());

        return (v1DotProduct > 0 || v2DotProduct > 0 || v3DotProduct > 0);
    }
}
