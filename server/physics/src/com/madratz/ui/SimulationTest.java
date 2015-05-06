package com.madratz.ui;

import com.madratz.gamelogic.CollisionHandler;
import com.madratz.leveldesigner.WorldLoader;
import com.madratz.simulation.MadratzWorld;
import org.jbox2d.dynamics.World;
import org.jbox2d.testbed.framework.TestbedTest;

public class SimulationTest extends TestbedTest {

    public static final float TIMESTEP = 1.0f/60.0f;
    private int mNumPlayers;

    // Ideally, we want to get just the level ID and the number of players here, so we load and build
    // the appropriate level through the Arena, allowing the same levels to be used with less players
    // than expected.
    public SimulationTest(int numberOfPlayers, int levelID) {
        super();
        mNumPlayers = numberOfPlayers;
    }

    @Override
    public void initTest(boolean b) {
        this.m_world.setContactListener(new CollisionHandler());
        setTitle("Simulation Test");

    }

    @Override
    public String getTestName() {
        return "Simulation Test";
    }


    @Override
    public MadratzWorld getWorld() {
        return (MadratzWorld)super.getWorld();
    }

    @Override
    public void init(World argWorld, boolean argDeserialized) {
        //hacky solution to attach a MadratzWorld to the UI.
        this.m_world = WorldLoader.buildScriptedWorld(mNumPlayers);
        super.init(this.m_world, argDeserialized);
    }
}
