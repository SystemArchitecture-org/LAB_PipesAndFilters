package at.fhv.sysarch.lab3.pipeline.push;

public abstract class Push<I, O> implements IPush<I> {

    protected IPush<O> successor;

    public Push(IPush<O> successor) {
        this.successor = successor;
    }
}
