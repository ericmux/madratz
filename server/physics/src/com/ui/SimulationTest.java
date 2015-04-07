package com.ui;

import com.leveldesigner.Arena;
import com.simulation.MadratzWorld;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.testbed.framework.TestbedTest;

public class SimulationTest extends TestbedTest {

    private Arena mArena;

    // Ideally, we want to get just the level ID and the number of players here, so we load and build
    // the appropriate level through the Arena, allowing the same levels to be used with less players
    // than expected.
    public SimulationTest(int numberOfPlayers, int levelID) {
        super();
        mArena = new Arena(numberOfPlayers);
    }

    @Override
    public void initTest(boolean b) {

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
        //hacky solution to attach a GameWorld to the UI.
        this.m_world = mArena.buildWorld();
        super.init(this.m_world, argDeserialized);
    }


    @Override
    public void endContact(Contact contact) {
        super.endContact(contact);
    }

    @Override
    public void beginContact(Contact contact) {
        super.beginContact(contact);
    }
}