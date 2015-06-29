include "world.thrift"

namespace java com.madratz.service

struct PlayerInfo {
  1: i64 id;
  2: string script;
}

struct MatchParams {
  // The Match ID should be either provided externally or a unique one will be
  // created internally, in case the caller doesn't track several IDs.
  1: optional i64 matchId;
  2: list<PlayerInfo> players;
  3: i64 timeLimitSec = 300;  // 5 minutes by default
}

struct MatchResult {
  1: optional i64 winnerId;
  2: double elapsedTimeSec;
}

struct CompilationResult {
  1: bool success;
  2: optional string errorType;
  3: optional string errorMsg;
}

exception InvalidArgumentException {
  1: string msg;
}

service SimulationService {
  // Starts a match with the given parameters and returns an id
  // for querying.
  i64 startMatch(1: MatchParams match) throws (1: InvalidArgumentException exc);

  bool isMatchFinished(1: i64 matchId) throws (1: InvalidArgumentException exc);

  MatchResult result(1: i64 matchId) throws (1: InvalidArgumentException exc);

  list<world.Snapshot> snapshots(1: i64 matchId) throws (1: InvalidArgumentException exc);

  CompilationResult compileScript(1: string script);
}
