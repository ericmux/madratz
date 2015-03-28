package com.ui;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.testbed.framework.TestbedTest;
import com.simulation.GameWorld;
import com.simulation.PrintCallable;

public class SimulationTestbedTest extends TestbedTest {
    @Override
    public void initTest(boolean b) {

        setTitle("Simulation Test");

        for (int i = 0; i < 2; i++) {
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(1, 1);

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.DYNAMIC;
            bodyDef.position.set(5 * i, 0);
            bodyDef.allowSleep = false;
            Body body = getWorld().createBody(bodyDef, new PrintCallable());
            body.createFixture(polygonShape, 5.0f);

            body.applyForce(new Vec2(-10000 * (i - 1), 0), new Vec2());
        }
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
        argWorld = this.m_world;
        super.init(argWorld, argDeserialized);
    }
}
