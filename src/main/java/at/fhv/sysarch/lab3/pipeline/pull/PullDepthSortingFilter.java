package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;

import java.util.*;

public class PullDepthSortingFilter<I extends Face> extends Pull<I, Face> {

//    private final LinkedList<Face> sortedFaces = new LinkedList<>();
//
//    public PullDepthSortingFilter(IPull<I> source) {
//        super(predecessor);
//    }
//
//    @Override
//    public Face pull() {
//        return sortedFaces.removeFirst();
//    }
//
//    @Override
//    public boolean hasNext() {
//        if (predecessor.hasNext() && sortedFaces.isEmpty()) {
//            sortNewFaces();
//        }
//        return !sortedFaces.isEmpty();
//    }
//
//    private void sortNewFaces() {
//        while(predecessor.hasNext()) {
//            sortedFaces.add(predecessor.pull());
//        }
//
//        sortedFaces.sort(Comparator.comparing(face -> face.getV1().getZ() + face.getV2().getZ() + face.getV3().getZ()));
//    }


    private final List<Face> sortedFaces = new LinkedList<>();

    public PullDepthSortingFilter(IPull<I> predecessor) {
        super(predecessor);
    }

    @Override
    public Face pull() {
        if (predecessor.hasNext() && sortedFaces.isEmpty()) {
            while (predecessor.hasNext()) {
                sortedFaces.add(predecessor.pull());
            }

            sortedFaces.sort(Comparator.comparing(face -> face.getV1().getZ() + face.getV2().getZ() + face.getV3().getZ()));
        }

        return sortedFaces.remove(0);
    }
}
