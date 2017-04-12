package com.foivos.mergesortParallel;

public interface GUI {

    public void startButtonsSetEnabled(boolean a);

    public void setSingleThreadResult(String s);

    public void setMultiThreadResult(String s);

    public void setThreadsUsed(String s);

    public String getThreadsRequested();

    public String getIterationsRequested();

}
