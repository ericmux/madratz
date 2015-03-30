package com.ui;

import com.gamelogic.MHSActor;
import com.gamelogic.TacklerActor;
import com.simulation.MadratzWorld;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.testbed.framework.TestbedTest;

public class SimulationTestbedTest extends TestbedTest {
    @Override
    public void initTest(boolean b) {

        setTitle("Simulation Test");


        TacklerActor gunnerRat = new TacklerActor();
        getWorld().registerActor(gunnerRat);

        MHSActor mhsRat = new MHSActor();
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
