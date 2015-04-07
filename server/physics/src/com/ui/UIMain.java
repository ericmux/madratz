package com.ui;

import org.jbox2d.testbed.framework.*;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class UIMain {

    private static final Logger log = LoggerFactory.getLogger(TestbedMain.class);


    public static void main(String[] args) {

        if(args.length < 1) throw new IllegalArgumentException("Number of player for this match not provided.");

        int numPlayers = Integer.parseInt(args[0]);
        if(numPlayers < 2) throw new IllegalArgumentException("At least two players are needed for a match.");


        TestbedModel model = new TestbedModel();

        // add tests
        model.addCategory("Rat Tests");
        model.addTest(new SimulationTest(numPlayers,0));

        // add our custom setting "My Range Setting", with a default value of 10, between 0 and 20
        model.getSettings().addSetting(new TestbedSetting("My Range Setting", TestbedSetting.SettingType.ENGINE, 10, 0, 20));

        TestbedPanel panel = new TestPanelJ2D(model);    // create our testbed panel

        JFrame testbed = new TestbedFrame(model,panel, TestbedController.UpdateBehavior.UPDATE_CALLED);
        testbed.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // etc
        testbed.setVisible(true);
        testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


}
