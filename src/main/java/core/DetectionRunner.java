package core;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class DetectionRunner implements Runnable {
    private final AtomicBoolean isRunning;
    private HashSet gate1 = new HashSet();
    private HashSet gate2 = new HashSet();

    public DetectionRunner() {
        this.isRunning = new AtomicBoolean(false);

    }

    @Override
    public void run() {
        while (isRunning.get()) {

        }
    }


}
