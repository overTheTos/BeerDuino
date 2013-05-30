package it.vupo.beerduino.thread;

import java.util.Vector;

/**
 * The class Buffer implements a buffer that can be used by threads to exchange
 * objects. The threads that get blocked because the buffer is full or empty are
 * kept in a queue, so the buffer has fifo behaviour.
 *
 * @author  Stephan Fischli
 * @version 1.1
 */
public class Buffer
{
    /**
     * The capacity of the buffer.
     */
    private int capacity;

    /**
     * The elements of the buffer.
     */
    private Vector elements;

    /**
     * The queue of threads waiting until the buffer is not full or not empty.
     */
    private Vector queue;

    /**
     * Constructs a Buffer object.
     * @param capacity the capacity of the buffer
     */
    public Buffer( int capacity )
    {
        this.capacity = capacity;
        elements = new Vector( capacity );
        queue = new Vector();
    }

    /**
     * Returns the size of the buffer.
     */
    public int size()
    {
        return elements.size();
    }

    public boolean isEmpty() {
        if(this.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds an element to the end of the buffer. If the buffer is full, the
     * calling thread will be blocked until another thread removes an element
     * from the buffer.
     * @param element the element to be added
     */
    public synchronized void put( Object element )
    {
        Thread caller = Thread.currentThread();
        if ( elements.size() == capacity ) {
            queue.addElement( caller );
            while ( size() == capacity || caller != queue.firstElement() )
                try { wait(); }
                catch ( InterruptedException e ) {}
            queue.removeElement( caller );
        }
        elements.addElement( element );
        notifyAll();
    }

    /**
     * Removes the first element from the buffer. If the buffer is empty, the
     * calling thread will be blocked until another thread adds an element to
     * the buffer.
     * @return the removed element
     */
    public synchronized Object get()
    {
        Thread caller = Thread.currentThread();
        if ( elements.isEmpty() ) {
            queue.addElement( caller );
            while ( elements.isEmpty() || caller != queue.firstElement() )
                try { wait(); }
                catch ( InterruptedException e ) {}
            queue.removeElement( caller );
        }
        Object element = elements.firstElement();
        elements.removeElement( element );
        notifyAll();
        return element;
    }


}
