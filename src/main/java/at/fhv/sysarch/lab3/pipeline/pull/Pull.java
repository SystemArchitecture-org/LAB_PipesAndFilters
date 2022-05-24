package at.fhv.sysarch.lab3.pipeline.pull;

public abstract class Pull<I, O> implements IPull<O> {

    protected IPull<I> predecessor;

    public Pull(IPull<I> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public boolean hasNext() {
        return predecessor.hasNext();
    }
}
