package ie03.phase2.task5.generator;

import ie03.phase2.task5.InterfaceIn;

import java.util.ArrayList;

public class StopOversInput implements InterfaceIn {
    private Object[] stopovers;
    private int length;
    private int index;

    public void reset(Object[] stopovers){
        this.stopovers = stopovers;
        this.length = (int) stopovers[0];
        this.index = 1;
    }


    public String next() {
        return stopovers[index++].toString();
    }

    public int getLength() {
        return length;
    }
}
