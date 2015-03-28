package com.ui;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public class BareMain {

    private static final World WORLD = new World(new Vec2());

    public static void main(String[] args) {

        WORLD.setContinuousPhysics(true);

        Body[] bodies = new Body[2];

        for (int i = 0; i < 2; i++) {
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(1, 1);

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.DYNAMIC;
            bodyDef.position.set(5 * i, 0);
            bodyDef.allowSleep = false;
            bodies[i] = WORLD.createBody(bodyDef);
            bodies[i].createFixture(polygonShape, 5.0f);

            bodies[i].applyForce(new Vec2(-10000 * (i - 1), 0), new Vec2(bodies[i].getLocalCenter()));


        }

        float dt = 1.0f/60.0f;
        while (true){

            WORLD.step(dt,10,10);

            System.out.println("Body 1: " + bodies[0].getPosition().toString());
            System.out.println("Body 2: " + bodies[1].getPosition().toString());

            System.out.println();

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {}
        }

    }
}
