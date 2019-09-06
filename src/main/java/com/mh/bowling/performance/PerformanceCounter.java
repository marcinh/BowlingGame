package com.mh.bowling.performance;

public class PerformanceCounter implements AutoCloseable {
    private long start;
    private long end;

    private void start(){
        start = System.currentTimeMillis();
    }

    @Override
    public void close() {
        end = System.currentTimeMillis();
    }

    private long duration() {
        return end - start;
    }

    public static long within(Runnable runnable) {
        PerformanceCounter counter = new PerformanceCounter();
        try{
            counter.start();
            runnable.run();
        }
        finally {
            counter.close();
        }
        return counter.duration();
    }
}
