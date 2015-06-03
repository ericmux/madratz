package com.madratz.simulation;

import com.madratz.decision.Decision;
import com.madratz.gamelogic.Actor;
import com.madratz.gamelogic.Player;
import com.madratz.ui.SimulationTest;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import java.util.*;
import java.util.stream.Collectors;

public class MadratzWorld extends World {

    private HashSet<Actor> mActiveActors;
    private Stack<Actor> mDestroyedActors;

    private int mFrameID;

    public MadratzWorld(Vec2 gravity) {
        super(gravity);
        mActiveActors = new HashSet<>();
        mDestroyedActors = new Stack<>();
        mFrameID = 0;
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

        mFrameID++;
    }

    public List<Player> getPlayers() {
        return mActiveActors.stream()
                .filter(p -> p instanceof Player)
                .map(p -> (Player)p)
                .collect(Collectors.toList());
    }

    public Set<Actor> getActiveActors() {
        return Collections.unmodifiableSet(mActiveActors);
    }

    public int getFrameID() {
        return mFrameID;
    }

    public float getElapsedTime(){
        return mFrameID*SimulationTest.TIMESTEP;
    }



}
