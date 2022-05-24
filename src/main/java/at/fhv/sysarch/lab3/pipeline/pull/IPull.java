package at.fhv.sysarch.lab3.pipeline.pull;

import javax.naming.OperationNotSupportedException;

public interface IPull<O> {

    O pull();

    boolean hasNext();

}
