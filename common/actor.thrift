namespace java com.madratz.serialization

struct Position {
  1: double x;
  2: double y;
}

enum State {
  IDLE      = 1,
  MOVING    = 2,  //non-zero velocity
  CASTING   = 3,  //spell casting
  TACKLING  = 4,  //melee attack
  DEAD      = 5
}

struct StateChange {
  1: State targetState;
  2: i32 duration;
}

struct Actor {
  1: i32 id;
  2: Position position; //position of center of mass.
  3: double angle;
  4: double hp;
  5: double width;

  6: optional StateChange stateChange;
}
