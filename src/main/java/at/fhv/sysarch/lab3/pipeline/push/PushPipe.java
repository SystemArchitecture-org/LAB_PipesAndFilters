package at.fhv.sysarch.lab3.pipeline.push;

public class PushPipe<I> extends Push<I, I> {


    public PushPipe(IPush<I> successor) {
        super(successor);
    }

    @Override
    public void push(I face) {
        successor.push(face);
    }
}
