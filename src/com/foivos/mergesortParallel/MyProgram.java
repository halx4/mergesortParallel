package com.foivos.mergesortParallel;

public class MyProgram implements ProgramCore {

    private GUI gui;

    private Test test;

    public static void main(String[] args) {
        new MyProgram();
    }

    // --------------------------------
    public MyProgram() {

        test = new Test();
        gui = new AWTGUI(this);

    }

    // --------------------------------------------
    @Override
    public void startSinglePressed() {

        int iterations;
        String iterationsReq = gui.getIterationsRequested();

        try {

            iterations = Integer.parseInt(iterationsReq);
            if (iterations < 1)
                throw new NumberFormatException();
            gui.startButtonsSetEnabled(false);
            long xronos = test.runSingleTest(iterations);
            gui.setSingleThreadResult(Long.toString(xronos));
            gui.startButtonsSetEnabled(true);
        } catch (NumberFormatException e) {
            gui.setSingleThreadResult("Invalid Settings");
        }

    }

    // -------------------------------------------
    @Override
    public void startMultiPressed() {

        int coresToBeUsed, iterations;
        String threadsReq = gui.getThreadsRequested();
        String iterationsReq = gui.getIterationsRequested();

        try {

            coresToBeUsed = Integer.parseInt(threadsReq);
            iterations = Integer.parseInt(iterationsReq);
            if (iterations < 1)
                throw new NumberFormatException();
            if (coresToBeUsed < 0)
                throw new NumberFormatException();

            gui.startButtonsSetEnabled(false);

            if (coresToBeUsed == 0)
                coresToBeUsed = Runtime.getRuntime().availableProcessors();

            gui.setThreadsUsed(" ");
            long time = test.runMultiTest(coresToBeUsed, iterations);

            gui.setMultiThreadResult(Long.toString(time));
            gui.setThreadsUsed(Integer.toString(Test.threadCount));
            gui.startButtonsSetEnabled(true);

        } catch (NumberFormatException e) {
            gui.setMultiThreadResult("Invalid Settings");
            gui.setThreadsUsed(" ");
        }

    }

}// end class

