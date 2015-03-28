package com.ui;

import org.jbox2d.testbed.framework.*;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class UIMain {

    private static final Logger log = LoggerFactory.getLogger(TestbedMain.class);


    public static void main(String[] args) {
        TestbedModel model = new TestbedModel();         // create our model

        // add tests
        model.addCategory("Rat Tests");             // add a category
        model.addTest(new SimulationTestbedTest());                // add our test
        model.addTest(new GraphicsTest());

        // add our custom setting "My Range Setting", with a default value of 10, between 0 and 20
        model.getSettings().addSetting(new TestbedSetting("My Range Setting", TestbedSetting.SettingType.ENGINE, 10, 0, 20));

        TestbedPanel panel = new TestPanelJ2D(model);    // create our testbed panel

        JFrame testbed = new TestbedFrame(model,panel, TestbedController.UpdateBehavior.UPDATE_CALLED);

        // etc
        testbed.setVisible(true);
        testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


}