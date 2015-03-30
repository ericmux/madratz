package com.ui;

import com.gamelogic.Actor;
import com.simulation.MHSBehavior;
import com.simulation.MadratzWorld;
import com.simulation.ShootBehavior;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.testbed.framework.TestbedTest;

public class SimulationTest extends TestbedTest {
    @Override
    public void initTest(boolean b) {

        setTitle("Simulation Test");

        Actor gunnerRat = new Actor(new ShootBehavior());
        getWorld().registerActor(gunnerRat);

        Actor mhsRat = new Actor(new MHSBehavior());
        getWorld().registerActor(mhsRat);

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
        this.m_world = new MadratzWorld(new Vec2());
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
