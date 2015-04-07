package com.ui;

import com.behaviors.MHSBehavior;
import com.behaviors.ShootBehavior;
import com.gamelogic.Actor;
import com.simulation.MadratzBuilder;
import com.simulation.MadratzWorld;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.testbed.framework.TestbedTest;

public class SimulationTest extends TestbedTest {

    private MadratzBuilder mMadratzBuilder;

    private int mNumberOfPlayers;

    public SimulationTest(int numberOfPlayers) {
        super();
        mMadratzBuilder = new MadratzBuilder(50.f,50.0f);
        mNumberOfPlayers = numberOfPlayers;

        Actor gunnerRat = new Actor(new ShootBehavior(), new Vec2(-5.0f,0.0f), 0.0f);
        Actor mhsRat = new Actor(new MHSBehavior(), new Vec2(5.0f,0.0f), 0.0f);

        mMadratzBuilder.addActor(gunnerRat).addActor(mhsRat).addWalls().setGravity(new Vec2());
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
        this.m_world = mMadratzBuilder.build();
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
