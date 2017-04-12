package com.foivos.mergesortParallel;

import java.util.Random;

public class Test implements ParentInterface {

    // PARAMETERS---------------------------------------
    // size of the array which is sorted. too big will invoke "OutOfMemoryError"
    private static final int tableSize = 17000000;

    // for a positive integer x, numbers will be in [-x,x].
    private static final int numbersLimit = 1000000;

    // -------------------------------------------------

    private int[] theArray; // the array which is sorted.

    private volatile int activeChildren;

    // main threads used
    static int threadCount = 0;

    public Test() {

        theArray = new int[tableSize];

    }

    // ---------------------------
    public long runSingleTest(int iterations) {

        long startTime, endTime;
        long totalTime = 0;
        System.out.println();
        System.out
                .println("------------------STARTING 1 THREAD TEST------------------------------");

        for (int i = 0; i < iterations; i++) {

            randomizeArray();

            // create the thread which will sort the array
            Mergesort ms = new Mergesort(theArray, 0, theArray.length, this);

            ms.setPriority(Thread.MAX_PRIORITY);

            setActiveChildren(1);

            // save start time timestamp
            startTime = System.currentTimeMillis();

            synchronized (this) {
                // sorting starts and this thread will wait until sorting
                // finishes.
                ms.start();

                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // get timestamp when sorting finishes
            endTime = System.currentTimeMillis();

            // just for debugging purposes
            System.out.println("sorted? ->" + checkIfSorted() + " in "
                    + (endTime - startTime) + " ms");
            System.out.println("--------------------");

            totalTime += (endTime - startTime);
        }

        System.out
                .println("----------------------------------------------------------------------");
        long meanTime = totalTime / iterations;
        return meanTime;

    }

    // -----------------------------------------------------------------------------------------------------
    public long runMultiTest(int coresToBeUsed, int iterations) {

        // base 2 logarithm of the No of cores to be used
        int treeDepth = (int) Math.ceil(Math.log(coresToBeUsed) / Math.log(2));

        System.out.println();
        System.out
                .println("------------------STARTING MULTI THREAD TEST---------------------------");
        System.out.println("-------------------treeDepth= " + treeDepth
                + " ---------------------------------------");
        System.out
                .println("-----------------------------------------------------------------------");

        Splitter.maxDepth = treeDepth;

        long startTime, endTime;
        long totalTime = 0;
        long meanTime = 0;

        for (int i = 0; i < iterations; i++) {

            randomizeArray();

            threadCount = 0;

            setActiveChildren(1);

            Splitter spl = new Splitter(0, theArray, 0, theArray.length, this);
            spl.setPriority(Thread.MAX_PRIORITY);

            startTime = System.currentTimeMillis();
            synchronized (this) {

                spl.start();

                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            endTime = System.currentTimeMillis();

            System.out.println("sorted? ->" + checkIfSorted() + " in "
                    + (endTime - startTime) + " ms");
            System.out.println("THREADS WERE : " + threadCount);
            System.out.println("---------------------");

            totalTime += (endTime - startTime);
        }

        System.out
                .println("----------------------------------------------------------------------");
        meanTime = totalTime / iterations;
        return meanTime;

    }

    // ----------------------------------
    private void setActiveChildren(int children) {
        activeChildren = children;
    }

    // --------------------------------
    public synchronized void childFinished() {
        activeChildren--;
        if (activeChildren == 0)
            notify();
    }

    // --------------------------------
    private void randomizeArray() {

        Random rand = new Random();
        for (int i = 0; i < tableSize; i++) {

            theArray[i] = rand.nextInt() % numbersLimit;
        }

    }

    // --------------------------------
    private boolean checkIfSorted() {
        boolean flag = true;

        int i = 0;
        while (flag && i < theArray.length - 1) {
            if (!(theArray[i] <= theArray[i + 1]))
                flag = false;
            i++;
        }
        return flag;
    }

    // --------------------------------
    private void printTable() {
        for (int i = 0; i < theArray.length; i++) {
            System.out.print(theArray[i] + "  ");

        }
        System.out.println();
    }

    // --------------------------------------------
    public static synchronized void increaseThreadCount() {
        Test.threadCount++;
    }

}// end class
// --------------------------------
