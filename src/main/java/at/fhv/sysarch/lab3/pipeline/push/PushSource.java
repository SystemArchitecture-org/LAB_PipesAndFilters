package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.exceptions.OperationNotAllowedException;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class PushSource extends Push<Face, Face> {

    private final Queue<Face> queueFaces;

    public PushSource(IPush<Face> successor) {
        super(successor);
        this.queueFaces = new ArrayDeque<>();
    }

    public void setFaces(List<Face> faces) {
        this.queueFaces.addAll(faces);

        while (!queueFaces.isEmpty()) {
            successor.push(queueFaces.poll());
        }
    }

    @Override
    public void push(Face face) {
        throw new OperationNotAllowedException("can't push on source");
    }
}
