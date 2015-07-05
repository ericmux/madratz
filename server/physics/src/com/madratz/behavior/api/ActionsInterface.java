package com.madratz.behavior.api;

import com.madratz.decision.Decision;
import com.madratz.decision.MoveRequest;
import com.madratz.gamelogic.player.Player;
import com.madratz.gamelogic.player.Weapon;
import org.jbox2d.common.Vec2;

public class ActionsInterface {

    private final Player mPlayer;
    private final Decision mDecision;

    public ActionsInterface(Player player, Decision decision) {
        mPlayer = player;
        mDecision = decision;
    }

    public void set_velocity(float velocity) {
        mDecision.addActionRequest(MoveRequest.forVelocity(mPlayer, modulate(velocity, Player.MAX_LINEAR_SPEED)));
    }

    public void set_local_velocity(float forward, float sideways) {
        // our convention is the opposite of JBox2d: sideways > 0 is to the right and < 0 to the left.
        Vec2 velocity = new Vec2(forward, -sideways);
        modulate(velocity, Player.MAX_LINEAR_SPEED);
        mDecision.addActionRequest(MoveRequest.forLocalVelocity(mPlayer, velocity));
    }

    public void set_world_velocity(float horizontal, float vertical) {
        Vec2 velocity = new Vec2(horizontal, vertical);
        modulate(velocity, Player.MAX_LINEAR_SPEED);
        mDecision.addActionRequest(MoveRequest.forWorldVelocity(mPlayer, velocity));
    }

    public void rotate(float speed) {
        mDecision.addActionRequest(MoveRequest.forRotation(mPlayer, modulate(speed, Player.MAX_ANGULAR_SPEED)));
    }

    public Weapon.Interface weapon() {
        return mPlayer.getWeapon().getInterface(mDecision);
    }

    private static float modulate(float fraction, float max) {
        return max * Math.max(-1, Math.min(fraction, 1));
    }

    private static void modulate(Vec2 fractionVec, float max) {
        float lengthSq = fractionVec.lengthSquared();
        if (lengthSq > 1) {
            fractionVec.mulLocal((float) (1 / Math.sqrt(lengthSq)));
        }
        fractionVec.mulLocal(max);
    }
}
