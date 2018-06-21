package org.engine.multithreading;

import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool extends ThreadGroup {
    private static IDAssigner     poolID = new IDAssigner(1);
    private        boolean        alive;
    private        int            id;
    private        List<Runnable> taskQueue;

    public ThreadPool(int numThreads) {
        super("ThreadPool-" + poolID.next());
        this.id = poolID.current();
        setDaemon(true);
        taskQueue = new LinkedList<>();
        alive = true;
        for (int i = 0; i < numThreads; i++) {
            new PooledThread(this).start();
        }
    }

    public synchronized void runTask(Runnable task) {
        if (!alive) {
            throw new IllegalStateException("ThreadPool-" + id + " is dead");
        }

        if (task != null) {
            taskQueue.add(task);
            notify();
        }
    }

    public synchronized void close() {
        if (!alive) {
            return;
        }
        alive = false;
        taskQueue.clear();
        interrupt();
    }

    public void join() {
        synchronized (this) {
            alive = false;
            notifyAll();
        }
        Thread[] threads = new Thread[activeCount()];
        int count = enumerate(threads);
        for (int i = 0; i < count; i++) {
            try {
                threads[i].join();
            }
            catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }

    @Nullable
    protected synchronized Runnable getTask() throws InterruptedException {
        while (taskQueue.size() == 0) {
            if (!alive) {
                return null;
            }
            wait();
        }
        return taskQueue.remove(0);
    }
}
