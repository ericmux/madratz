include "actor.thrift"

namespace java com.madratz.serialization


struct Snapshot {
  1: i32 frameId;
  2: list<actor.Actor> actors;

  3: optional bool finished;
}
