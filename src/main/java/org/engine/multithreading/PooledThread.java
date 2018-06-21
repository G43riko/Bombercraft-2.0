package org.engine.multithreading;


public class PooledThread extends Thread {
    private static IDAssigner poolID = new IDAssigner(1);
    private        ThreadPool pool;

    public PooledThread(ThreadPool pool) {
        super(pool, "PooledThread-" + poolID.next());
        this.pool = pool;
    }


    @Override
    public void run() {
        while (!isInterrupted()) {
            Runnable task = null;
            try {
                task = pool.getTask();
            }
            catch (InterruptedException e) {
                System.err.println(e);
            }

            if (task == null) {
                return;
            }

            try {
                task.run();

            }
            catch (Throwable e) {
                pool.uncaughtException(this, e);
            }
        }
    }
}
