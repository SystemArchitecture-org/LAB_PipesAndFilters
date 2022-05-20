package at.fhv.sysarch.lab3.pipeline.pull;

public abstract class Pull<I, O> implements IPull<O>{
    private IPull<I> predecessor;

    public Pull(IPull<I> predecessor) {
        this.predecessor = predecessor;
    }
}
