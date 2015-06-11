package com.madratz.behavior.api;

import com.madratz.decision.Decision;
import com.madratz.decision.MoveRequest;
import com.madratz.decision.SpellRequest;
import com.madratz.gamelogic.Player;

public class ActionsInterface {

    private final Player mPlayer;
    private final Decision mDecision;

    public ActionsInterface(Player player, Decision decision) {
        mPlayer = player;
        mDecision = decision;
    }

    public void set_velocity(float velocity){
        mDecision.addActionRequest(MoveRequest.forVelocity(mPlayer, modulate(velocity, Player.MAX_LINEAR_SPEED)));
    }

    public void rotate(float speed){
        mDecision.addActionRequest(MoveRequest.forRotation(mPlayer, modulate(speed, Player.MAX_ANGULAR_SPEED)));
    }

    public void shoot(){
        mDecision.addActionRequest(new SpellRequest(mPlayer));
    }

    private static float modulate(float fraction, float max) {
        return max * Math.max(-1, Math.min(fraction, 1));
    }
}
