package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class PullSource extends Pull<Face, Face> {

    private final Queue<Face> faces;

    public PullSource() {
        super(null);
        this.faces = new ArrayDeque<>();
    }

    public Face pull() {
        return faces.poll();
    }

    public void setFaces(List<Face> faces) {
        this.faces.addAll(faces);
    }

    public boolean hasNext() {
        return !faces.isEmpty();
    }
}
