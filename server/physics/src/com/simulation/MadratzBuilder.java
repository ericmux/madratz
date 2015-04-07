package com.simulation;

import com.gamelogic.Actor;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import java.util.ArrayList;
import java.util.List;

public class MadratzBuilder {

    private final float WALL_THICKNESS = 1.0f;

    private float mWidth;
    private float mHeight;

    private List<Actor> mActorList;
    private List<ObstacleDef>  mObstaclesList;

    private Vec2 mGravity;


    public MadratzBuilder(float width, float height) {
        mActorList = new ArrayList<>();
        mObstaclesList = new ArrayList<>();
        mWidth = width;
        mHeight = height;
    }

    public MadratzBuilder addActor(Actor actor){
        mActorList.add(actor);
        return this;
    }

    public MadratzBuilder addObstacle(BodyDef bodyDef, FixtureDef fixtureDef){
        mObstaclesList.add(new ObstacleDef(bodyDef,fixtureDef));
        return this;
    }

    public MadratzBuilder setDimensions(float width, float height){
        mWidth = width;
        mHeight = height;
        return this;
    }

    public MadratzBuilder setGravity(Vec2 gravity){
        mGravity = gravity;
        return this;
    }

    public MadratzBuilder addWalls(){

        //left wall.
        addWallAt(new Vec2(-0.5f*mWidth,0.0f), true);

        //right wall.
        addWallAt(new Vec2(0.5f*mWidth,0.0f), true);

        //top wall.
        addWallAt(new Vec2(0.0f,0.5f*mHeight), false);

        //bottom wall.
        addWallAt(new Vec2(0.0f,-0.5f*mHeight), false);

        return this;
    }

    public MadratzWorld build(){
        MadratzWorld madWorld = new MadratzWorld(mGravity);

        mObstaclesList.stream().forEach(obstacleDef -> {
            Body body = madWorld.createBody(obstacleDef.bodyDef);
            body.createFixture(obstacleDef.fixtureDef);
        });

        mActorList.stream().forEach(madWorld::registerActor);

        return madWorld;
    }



    private void addWallAt(Vec2 position, boolean vertical){

        BodyDef wallBodyDef = new BodyDef();
        FixtureDef wallFixtureDef = new FixtureDef();

        wallBodyDef.position.set(position);

        PolygonShape polygonShape = new PolygonShape();

        if(vertical) {
            polygonShape.setAsBox(0.5f * WALL_THICKNESS, 0.5f * mHeight);
        } else polygonShape.setAsBox(0.5f*mWidth, 0.5f*WALL_THICKNESS);

        wallFixtureDef.shape = polygonShape;

        mObstaclesList.add(new ObstacleDef(wallBodyDef, wallFixtureDef));
    }


    private class ObstacleDef{
        private BodyDef bodyDef;
        private FixtureDef fixtureDef;

        public ObstacleDef(BodyDef bodyDef, FixtureDef fixtureDef) {
            this.bodyDef = bodyDef;
            this.fixtureDef = fixtureDef;
        }
    }

}
