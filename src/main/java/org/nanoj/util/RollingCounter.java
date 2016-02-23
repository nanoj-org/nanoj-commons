package org.nanoj.util;

public class RollingCounter {
	
	private int counter = 0;

    public synchronized int increment() {
    	
    	if ( counter == Integer.MAX_VALUE ) {
    		counter = 0 ;
    	}
        counter++;
        return counter ;
    }

    public synchronized int value() {
        return counter;
    }
}
