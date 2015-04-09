package wrappers;

import com.decision.Decision;
import com.decision.RotateRequest;
import com.decision.SpeedUpRequest;
import com.decision.SpellRequest;
import com.gamelogic.Actor;
import com.simulation.MadratzWorld;
import org.jbox2d.dynamics.Body;

public class ActorWrapper {

    private Actor mActor;
    private Decision mDecision;

    public ActorWrapper(Actor actor, Decision decision) {
        mActor = actor;
        mDecision = decision;
    }

    public void speedUp(float intensity){
        mDecision.addActionRequest(new SpeedUpRequest(mActor,intensity));
    }

    public void rotate(float intensity){
        mDecision.addActionRequest(new RotateRequest(mActor,intensity));
    }

    public void shoot(){
        mDecision.addActionRequest(new SpellRequest(mActor));
    }

    public Decision getDecision() {
        return mDecision;
    }

    public Body __body__() {
        return mActor.getBody();
    }

    public MadratzWorld __world__() {
        return mActor.getWorld();
    }
}
