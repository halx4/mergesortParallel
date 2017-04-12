package com.foivos.mergesortParallel;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AWTGUI extends Frame implements GUI {

    private Button startSingle, startMulti;

    private ProgramCore source;

    private Label l0, l1, l2, l3, l4, l5, l6, l7;

    private TextField singleThreadResult, multiThreadResult, threadsRequested,
            threadsUsed, iterations;

    public AWTGUI(ProgramCore source) {

        this.source = source;

        super.setTitle("Mergesort");
        this.setLayout(null);
        this.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        this.setBackground(new Color(119, 136, 153));

        this.setLocation(855, 200);
        this.setSize(600, 470);

        this.toFront();
        this.setResizable(false);
        this.addWindowListener(new ExitHandler());

        l0 = new Label("COMMON SETTING : iterations:");
        this.add(l0);
        l0.setBounds(100, 50, 210, 20);

        l1 = new Label("SERIAL");
        this.add(l1);
        l1.setBounds(20, 110, 150, 20);

        // l1.setVisible(true);

        l2 = new Label("PARALLEL");
        this.add(l2);
        l2.setBounds(350, 110, 150, 20);

        // l2.setVisible(true);

        l3 = new Label("CPU Cores:");
        this.add(l3);
        l3.setBounds(350, 160, 200, 20);

        // l3.setVisible(true);

        l4 = new Label("(0 for auto-select)");
        this.add(l4);
        l4.setBounds(350, 185, 200, 20);

        // l4.setVisible(true);

        l5 = new Label("Mean Time(ms):");
        this.add(l5);
        l5.setBounds(20, 340, 150, 20);

        l6 = new Label("Mean Time(ms):");
        this.add(l6);
        l6.setBounds(350, 340, 150, 20);

        l7 = new Label("Main Threads:");
        this.add(l7);
        l7.setBounds(350, 385, 150, 20);

        singleThreadResult = new TextField();
        this.add(singleThreadResult);
        singleThreadResult.setBounds(20, 360, 140, 20);
        singleThreadResult.setEditable(false);

        multiThreadResult = new TextField();
        this.add(multiThreadResult);
        multiThreadResult.setBounds(350, 360, 140, 20);
        multiThreadResult.setEditable(false);

        threadsRequested = new TextField("0");
        this.add(threadsRequested);
        threadsRequested.setBounds(350, 220, 60, 20);
        threadsRequested.setEditable(true);

        threadsUsed = new TextField();
        this.add(threadsUsed);
        threadsUsed.setBounds(350, 405, 140, 20);
        threadsUsed.setEditable(false);

        iterations = new TextField("5");
        this.add(iterations);
        iterations.setBounds(310, 50, 60, 20);
        iterations.setEditable(true);

        startSingle = new Button("Start");
        startSingle.addActionListener(new StartSingleButtonHandler());
        this.add(startSingle);
        startSingle.setBounds(20, 270, 70, 30);
        startSingle.setEnabled(true);

        startMulti = new Button("Start");
        startMulti.addActionListener(new StartMultiButtonHandler());
        this.add(startMulti);
        startMulti.setBounds(350, 270, 70, 30);
        startMulti.setEnabled(true);

        this.setVisible(true);
        threadsRequested.requestFocusInWindow();

    }

    // ----------------------
    @Override
    public void startButtonsSetEnabled(boolean a) {
        startSingle.setEnabled(a);
        startMulti.setEnabled(a);
        threadsRequested.setEnabled(a);
    }

    // ---------------------------------------
    @Override
    public void setSingleThreadResult(String s) {
        singleThreadResult.setText(s);
    }

    // ---------------------------------------
    @Override
    public void setMultiThreadResult(String s) {
        multiThreadResult.setText(s);
    }

    // ---------------------------------------
    @Override
    public void setThreadsUsed(String s) {
        threadsUsed.setText(s);
    }

    // ---------------------------------------
    @Override
    public String getThreadsRequested() {
        return threadsRequested.getText();
    }

    // ---------------------------------------
    @Override
    public String getIterationsRequested() {
        return iterations.getText();
    }

    // ---------------------------------------

    class ExitHandler extends WindowAdapter {
        public void windowClosing(WindowEvent e) {

            Frame source = (Frame) e.getSource();
            source.dispose();
            System.exit(0);
        }
    }

    // ---------------------------------------
    class StartSingleButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            source.startSinglePressed();
        }
    }

    // ---------------------------------------
    class StartMultiButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            source.startMultiPressed();
        }
    }
    // ---------------------------------------
}
