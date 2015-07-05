package com.madratz.behavior.api;

import com.madratz.gamelogic.Actor;
import com.madratz.gamelogic.player.Player;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class ActorInfo {

    public static ActorInfo fromActor(Actor actor) {
        Body body = actor.getBody();
        return new ActorInfo(body.getPosition(), direction(body.getAngle()));
    }

    public static ActorInfo fromPlayer(Player player) {
        Body body = player.getBody();
        return new ActorInfo(player.getId(), player.getHP(), body.getPosition(), direction(body.getAngle()));
    }

    private static Vec2 direction(double angle) {
        return new Vec2((float) Math.cos(angle), (float) Math.sin(angle));
    }

    public final String id;
    public final float hp;
    public final Vec2 position;
    public final Vec2 direction;

    public ActorInfo(Vec2 position, Vec2 direction) {
        this(null, 0, position, direction);
    }

    public ActorInfo(String id, float hp, Vec2 position, Vec2 direction) {
        this.id = id;
        this.hp = hp;
        this.position = position;
        this.direction = direction;
    }
}
