package ie.engine.implementations;
import java.util.concurrent.*;

/* A queue to trick the executor into blocking until a Thread is available when offer is called */
public class SpecialSyncQueue<E> extends SynchronousQueue<E> {
    @Override
    public boolean offer(E e) {
        try {
            put(e);
            return true;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}