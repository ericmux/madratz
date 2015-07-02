package com.madratz.behavior.api;

import com.madratz.decision.Decision;
import com.madratz.decision.MoveRequest;
import com.madratz.gamelogic.player.Player;
import com.madratz.gamelogic.player.Weapon;

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

    public Weapon.Interface weapon() {
        return mPlayer.getWeapon().getInterface(mDecision);
    }

    private static float modulate(float fraction, float max) {
        return max * Math.max(-1, Math.min(fraction, 1));
    }
}
