package com.ui;

import com.gamelogic.TacklerRat;
import com.simulation.GameWorld;
import com.simulation.MHSRatCallable;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.testbed.framework.TestbedTest;

public class SimulationTestbedTest extends TestbedTest {
    @Override
    public void initTest(boolean b) {

        setTitle("Simulation Test");


        TacklerRat gunnerRat = new TacklerRat();

        Body body = getWorld().createBody(gunnerRat.getBodyDef());
        body.createFixture(gunnerRat.getFixtureDef());

        getWorld().registerCallback(body, gunnerRat.getGameBodyCallable());


        TacklerRat mhsRat = new TacklerRat();

        body = getWorld().createBody(mhsRat.getBodyDef());
        body.createFixture(mhsRat.getFixtureDef());

        getWorld().registerCallback(body, new MHSRatCallable());

    }

    @Override
    public String getTestName() {
        return "Simulation Test";
    }


    @Override
    public GameWorld getWorld() {
        return (GameWorld)super.getWorld();
    }

    @Override
    public void init(World argWorld, boolean argDeserialized) {
        //hacky solution to attach a GameWorld to the UI.
        this.m_world = new GameWorld(new Vec2());
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
