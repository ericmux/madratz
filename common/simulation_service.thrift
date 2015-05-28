include "world.thrift"

namespace java com.madratz.service

struct Player {
  1: i64 id;
  2: string script;
}

struct MatchParams {
  1: list<Player> players;
}

struct MatchResult {
  1: i64 winnerId;
}

service SimulationService {
  // Starts a match with the given parameters and returns an id
  // for querying.
  i64 startMatch(1: MatchParams match);

  bool isMatchFinished(1: i64 matchId);

  MatchResult result(1: i64 matchId);
}
