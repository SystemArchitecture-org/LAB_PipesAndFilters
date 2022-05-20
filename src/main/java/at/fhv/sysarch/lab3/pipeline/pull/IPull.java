package at.fhv.sysarch.lab3.pipeline.pull;

public interface IPull<O> {

    O pull();

    boolean hasNext();

}
