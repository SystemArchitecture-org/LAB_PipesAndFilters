package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;

import java.util.Queue;

public class ExampleFilter extends Pull<Face, Face> {

    private PullSource pullSource;

    public ExampleFilter(PullSource pullSource) {
        super(pullSource);
        this.pullSource = pullSource;
    }

    public Face pull(){
        Face face = pullSource.pull();



        return face;
    }

}
