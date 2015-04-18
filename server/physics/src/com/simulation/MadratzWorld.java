package com.simulation;

import com.decision.Decision;
import com.gamelogic.Actor;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class MadratzWorld extends World {

    private HashSet<Actor> mActiveActors;
    private Stack<Actor> mDestroyedActors;


    public MadratzWorld(Vec2 gravity) {
        super(gravity);
        mActiveActors = new HashSet<>();
        mDestroyedActors = new Stack<>();
    }

    public void registerActor(Actor actor){
        if(actor == null) return;

        Body body = createBody(actor.getBodyDef());
        body.createFixture(actor.getFixtureDef());

        body.setUserData(actor);
        actor.setBody(body);

        mActiveActors.add(actor);
    }

    public void destroyActor(Actor actor){
        if(actor == null || mDestroyedActors.contains(actor)) return;

        mDestroyedActors.add(actor);
    }


    @Override
    public void step(float dt, int velocityIterations, int positionIterations) {

        List<Decision> decisions = mActiveActors.stream()
                                                    .parallel()
                                                    .map(Actor::executeBehavior)
                                                    .collect(Collectors.toList());
        //need to split up since some action requests might modify the active actors stream.
        decisions.forEach(Decision::apply);

        super.step(dt, velocityIterations, positionIterations);

        //clean up dead bodies.
        while(!mDestroyedActors.empty()){
            Actor actor = mDestroyedActors.pop();

            destroyBody(actor.getBody());
            mActiveActors.remove(actor);
        }
    }





}
