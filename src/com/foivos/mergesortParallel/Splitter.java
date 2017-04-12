package com.foivos.mergesortParallel;

public class Splitter extends Thread implements ParentInterface {

    public static int maxDepth = 1;

    private volatile int activeChildren;

    private int depth;

    private int[] pinakas;

    private int first;

    private int n;

    private ParentInterface par;

    public Splitter(int depth, int[] pinakas, int first, int n,
            ParentInterface par) {
        this.depth = depth;
        this.pinakas = pinakas;
        this.first = first;
        this.n = n;
        this.par = par;
    }

    // ----------------------------------------------
    public void run() {

        if (depth < maxDepth) { // split in two

            int n1, n2;
            if (n > 1) {
                n1 = n / 2;
                n2 = n - n1;

                activeChildren = 2;
                Splitter sp1 = new Splitter(depth + 1, pinakas, first, n1, this);
                Splitter sp2 = new Splitter(depth + 1, pinakas, first + n1, n2,
                        this);

                synchronized (this) {
                    sp1.start();
                    sp2.start();
                    try {
                        wait();
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                }

                // on notify
                Mergesort.merge(pinakas, first, n1, n2);
                par.childFinished();
            } else {
                // Test.increaseThreadCount();
                par.childFinished();
            }

        } else {// sort with a single thread this part of the array

            Test.increaseThreadCount();

            activeChildren = 1;
            Mergesort ms = new Mergesort(pinakas, first, n, this);
            ms.setPriority(Thread.MAX_PRIORITY);

            synchronized (this) {
                ms.start();
                try {
                    wait();

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
                par.childFinished();
            }

        }

    }

    // --------------------------------------------------

    public synchronized void childFinished() {
        activeChildren--;
        if (activeChildren == 0)
            notifyAll();

    }

}
