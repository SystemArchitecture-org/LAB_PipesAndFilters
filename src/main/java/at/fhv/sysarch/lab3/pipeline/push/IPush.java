package at.fhv.sysarch.lab3.pipeline.push;

import javax.naming.OperationNotSupportedException;

public interface IPush<I> {

    void push(I face);

}
