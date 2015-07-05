include "world.thrift"

namespace java com.madratz.service

struct PlayerInfo {
  1: i64 id;
  2: string script;
}

struct MatchParams {
  1: string matchId;
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
  void startMatch(1: MatchParams match) throws (1: InvalidArgumentException exc);

  // Called to free the match from memory after it's been finished and nothing
  // is needed from the simulation service anymore (e.g. the result or snapshots).
  void finalizeMatch(1: string matchId) throws (1: InvalidArgumentException exc);

  bool isMatchFinished(1: string matchId) throws (1: InvalidArgumentException exc);

  MatchResult result(1: string matchId) throws (1: InvalidArgumentException exc);

  list<world.Snapshot> snapshots(1: string matchId) throws (1: InvalidArgumentException exc);

  CompilationResult compileScript(1: string script);
}
