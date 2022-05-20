package at.fhv.sysarch.lab3.pipeline.pull;

public class PullPipe<I> extends Pull<I, I>{

    public PullPipe(IPull<I> predecessor){
        super(predecessor);
    }

    @Override
    public I pull() {
        return predecessor.pull();
    }
}
