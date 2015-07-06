//
// Autogenerated by Thrift Compiler (0.9.2)
//
// DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//
var thrift = require('thrift');
var Thrift = thrift.Thrift;
var Q = thrift.Q;

var world_ttypes = require('./world_types')


var ttypes = require('./simulation_service_types');
//HELPER FUNCTIONS AND STRUCTURES

SimulationService_startMatch_args = function(args) {
  this.match = null;
  if (args) {
    if (args.match !== undefined) {
      this.match = args.match;
    }
  }
};
SimulationService_startMatch_args.prototype = {};
SimulationService_startMatch_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRUCT) {
        this.match = new ttypes.MatchParams();
        this.match.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

SimulationService_startMatch_args.prototype.write = function(output) {
  output.writeStructBegin('SimulationService_startMatch_args');
  if (this.match !== null && this.match !== undefined) {
    output.writeFieldBegin('match', Thrift.Type.STRUCT, 1);
    this.match.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SimulationService_startMatch_result = function(args) {
  this.exc = null;
  if (args instanceof ttypes.InvalidArgumentException) {
    this.exc = args;
    return;
  }
  if (args) {
    if (args.exc !== undefined) {
      this.exc = args.exc;
    }
  }
};
SimulationService_startMatch_result.prototype = {};
SimulationService_startMatch_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRUCT) {
        this.exc = new ttypes.InvalidArgumentException();
        this.exc.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

SimulationService_startMatch_result.prototype.write = function(output) {
  output.writeStructBegin('SimulationService_startMatch_result');
  if (this.exc !== null && this.exc !== undefined) {
    output.writeFieldBegin('exc', Thrift.Type.STRUCT, 1);
    this.exc.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SimulationService_finalizeMatch_args = function(args) {
  this.matchId = null;
  if (args) {
    if (args.matchId !== undefined) {
      this.matchId = args.matchId;
    }
  }
};
SimulationService_finalizeMatch_args.prototype = {};
SimulationService_finalizeMatch_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.matchId = input.readString();
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

SimulationService_finalizeMatch_args.prototype.write = function(output) {
  output.writeStructBegin('SimulationService_finalizeMatch_args');
  if (this.matchId !== null && this.matchId !== undefined) {
    output.writeFieldBegin('matchId', Thrift.Type.STRING, 1);
    output.writeString(this.matchId);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SimulationService_finalizeMatch_result = function(args) {
  this.exc = null;
  if (args instanceof ttypes.InvalidArgumentException) {
    this.exc = args;
    return;
  }
  if (args) {
    if (args.exc !== undefined) {
      this.exc = args.exc;
    }
  }
};
SimulationService_finalizeMatch_result.prototype = {};
SimulationService_finalizeMatch_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRUCT) {
        this.exc = new ttypes.InvalidArgumentException();
        this.exc.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

SimulationService_finalizeMatch_result.prototype.write = function(output) {
  output.writeStructBegin('SimulationService_finalizeMatch_result');
  if (this.exc !== null && this.exc !== undefined) {
    output.writeFieldBegin('exc', Thrift.Type.STRUCT, 1);
    this.exc.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SimulationService_isMatchFinished_args = function(args) {
  this.matchId = null;
  if (args) {
    if (args.matchId !== undefined) {
      this.matchId = args.matchId;
    }
  }
};
SimulationService_isMatchFinished_args.prototype = {};
SimulationService_isMatchFinished_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.matchId = input.readString();
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

SimulationService_isMatchFinished_args.prototype.write = function(output) {
  output.writeStructBegin('SimulationService_isMatchFinished_args');
  if (this.matchId !== null && this.matchId !== undefined) {
    output.writeFieldBegin('matchId', Thrift.Type.STRING, 1);
    output.writeString(this.matchId);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SimulationService_isMatchFinished_result = function(args) {
  this.success = null;
  this.exc = null;
  if (args instanceof ttypes.InvalidArgumentException) {
    this.exc = args;
    return;
  }
  if (args) {
    if (args.success !== undefined) {
      this.success = args.success;
    }
    if (args.exc !== undefined) {
      this.exc = args.exc;
    }
  }
};
SimulationService_isMatchFinished_result.prototype = {};
SimulationService_isMatchFinished_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 0:
      if (ftype == Thrift.Type.BOOL) {
        this.success = input.readBool();
      } else {
        input.skip(ftype);
      }
      break;
      case 1:
      if (ftype == Thrift.Type.STRUCT) {
        this.exc = new ttypes.InvalidArgumentException();
        this.exc.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

SimulationService_isMatchFinished_result.prototype.write = function(output) {
  output.writeStructBegin('SimulationService_isMatchFinished_result');
  if (this.success !== null && this.success !== undefined) {
    output.writeFieldBegin('success', Thrift.Type.BOOL, 0);
    output.writeBool(this.success);
    output.writeFieldEnd();
  }
  if (this.exc !== null && this.exc !== undefined) {
    output.writeFieldBegin('exc', Thrift.Type.STRUCT, 1);
    this.exc.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SimulationService_result_args = function(args) {
  this.matchId = null;
  if (args) {
    if (args.matchId !== undefined) {
      this.matchId = args.matchId;
    }
  }
};
SimulationService_result_args.prototype = {};
SimulationService_result_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.matchId = input.readString();
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

SimulationService_result_args.prototype.write = function(output) {
  output.writeStructBegin('SimulationService_result_args');
  if (this.matchId !== null && this.matchId !== undefined) {
    output.writeFieldBegin('matchId', Thrift.Type.STRING, 1);
    output.writeString(this.matchId);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SimulationService_result_result = function(args) {
  this.success = null;
  this.exc = null;
  if (args instanceof ttypes.InvalidArgumentException) {
    this.exc = args;
    return;
  }
  if (args) {
    if (args.success !== undefined) {
      this.success = args.success;
    }
    if (args.exc !== undefined) {
      this.exc = args.exc;
    }
  }
};
SimulationService_result_result.prototype = {};
SimulationService_result_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 0:
      if (ftype == Thrift.Type.STRUCT) {
        this.success = new ttypes.MatchResult();
        this.success.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 1:
      if (ftype == Thrift.Type.STRUCT) {
        this.exc = new ttypes.InvalidArgumentException();
        this.exc.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

SimulationService_result_result.prototype.write = function(output) {
  output.writeStructBegin('SimulationService_result_result');
  if (this.success !== null && this.success !== undefined) {
    output.writeFieldBegin('success', Thrift.Type.STRUCT, 0);
    this.success.write(output);
    output.writeFieldEnd();
  }
  if (this.exc !== null && this.exc !== undefined) {
    output.writeFieldBegin('exc', Thrift.Type.STRUCT, 1);
    this.exc.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SimulationService_snapshots_args = function(args) {
  this.matchId = null;
  if (args) {
    if (args.matchId !== undefined) {
      this.matchId = args.matchId;
    }
  }
};
SimulationService_snapshots_args.prototype = {};
SimulationService_snapshots_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.matchId = input.readString();
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

SimulationService_snapshots_args.prototype.write = function(output) {
  output.writeStructBegin('SimulationService_snapshots_args');
  if (this.matchId !== null && this.matchId !== undefined) {
    output.writeFieldBegin('matchId', Thrift.Type.STRING, 1);
    output.writeString(this.matchId);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SimulationService_snapshots_result = function(args) {
  this.success = null;
  this.exc = null;
  if (args instanceof ttypes.InvalidArgumentException) {
    this.exc = args;
    return;
  }
  if (args) {
    if (args.success !== undefined) {
      this.success = args.success;
    }
    if (args.exc !== undefined) {
      this.exc = args.exc;
    }
  }
};
SimulationService_snapshots_result.prototype = {};
SimulationService_snapshots_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 0:
      if (ftype == Thrift.Type.STRUCT) {
        this.success = new ttypes.SnapshotList();
        this.success.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 1:
      if (ftype == Thrift.Type.STRUCT) {
        this.exc = new ttypes.InvalidArgumentException();
        this.exc.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

SimulationService_snapshots_result.prototype.write = function(output) {
  output.writeStructBegin('SimulationService_snapshots_result');
  if (this.success !== null && this.success !== undefined) {
    output.writeFieldBegin('success', Thrift.Type.STRUCT, 0);
    this.success.write(output);
    output.writeFieldEnd();
  }
  if (this.exc !== null && this.exc !== undefined) {
    output.writeFieldBegin('exc', Thrift.Type.STRUCT, 1);
    this.exc.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SimulationService_compileScript_args = function(args) {
  this.script = null;
  if (args) {
    if (args.script !== undefined) {
      this.script = args.script;
    }
  }
};
SimulationService_compileScript_args.prototype = {};
SimulationService_compileScript_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.script = input.readString();
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

SimulationService_compileScript_args.prototype.write = function(output) {
  output.writeStructBegin('SimulationService_compileScript_args');
  if (this.script !== null && this.script !== undefined) {
    output.writeFieldBegin('script', Thrift.Type.STRING, 1);
    output.writeString(this.script);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SimulationService_compileScript_result = function(args) {
  this.success = null;
  if (args) {
    if (args.success !== undefined) {
      this.success = args.success;
    }
  }
};
SimulationService_compileScript_result.prototype = {};
SimulationService_compileScript_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 0:
      if (ftype == Thrift.Type.STRUCT) {
        this.success = new ttypes.CompilationResult();
        this.success.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

SimulationService_compileScript_result.prototype.write = function(output) {
  output.writeStructBegin('SimulationService_compileScript_result');
  if (this.success !== null && this.success !== undefined) {
    output.writeFieldBegin('success', Thrift.Type.STRUCT, 0);
    this.success.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SimulationServiceClient = exports.Client = function(output, pClass) {
    this.output = output;
    this.pClass = pClass;
    this._seqid = 0;
    this._reqs = {};
};
SimulationServiceClient.prototype = {};
SimulationServiceClient.prototype.seqid = function() { return this._seqid; }
SimulationServiceClient.prototype.new_seqid = function() { return this._seqid += 1; }
SimulationServiceClient.prototype.startMatch = function(match, callback) {
  this._seqid = this.new_seqid();
  if (callback === undefined) {
    var _defer = Q.defer();
    this._reqs[this.seqid()] = function(error, result) {
      if (error) {
        _defer.reject(error);
      } else {
        _defer.resolve(result);
      }
    };
    this.send_startMatch(match);
    return _defer.promise;
  } else {
    this._reqs[this.seqid()] = callback;
    this.send_startMatch(match);
  }
};

SimulationServiceClient.prototype.send_startMatch = function(match) {
  var output = new this.pClass(this.output);
  output.writeMessageBegin('startMatch', Thrift.MessageType.CALL, this.seqid());
  var args = new SimulationService_startMatch_args();
  args.match = match;
  args.write(output);
  output.writeMessageEnd();
  return this.output.flush();
};

SimulationServiceClient.prototype.recv_startMatch = function(input,mtype,rseqid) {
  var callback = this._reqs[rseqid] || function() {};
  delete this._reqs[rseqid];
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(input);
    input.readMessageEnd();
    return callback(x);
  }
  var result = new SimulationService_startMatch_result();
  result.read(input);
  input.readMessageEnd();

  if (null !== result.exc) {
    return callback(result.exc);
  }
  callback(null)
};
SimulationServiceClient.prototype.finalizeMatch = function(matchId, callback) {
  this._seqid = this.new_seqid();
  if (callback === undefined) {
    var _defer = Q.defer();
    this._reqs[this.seqid()] = function(error, result) {
      if (error) {
        _defer.reject(error);
      } else {
        _defer.resolve(result);
      }
    };
    this.send_finalizeMatch(matchId);
    return _defer.promise;
  } else {
    this._reqs[this.seqid()] = callback;
    this.send_finalizeMatch(matchId);
  }
};

SimulationServiceClient.prototype.send_finalizeMatch = function(matchId) {
  var output = new this.pClass(this.output);
  output.writeMessageBegin('finalizeMatch', Thrift.MessageType.CALL, this.seqid());
  var args = new SimulationService_finalizeMatch_args();
  args.matchId = matchId;
  args.write(output);
  output.writeMessageEnd();
  return this.output.flush();
};

SimulationServiceClient.prototype.recv_finalizeMatch = function(input,mtype,rseqid) {
  var callback = this._reqs[rseqid] || function() {};
  delete this._reqs[rseqid];
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(input);
    input.readMessageEnd();
    return callback(x);
  }
  var result = new SimulationService_finalizeMatch_result();
  result.read(input);
  input.readMessageEnd();

  if (null !== result.exc) {
    return callback(result.exc);
  }
  callback(null)
};
SimulationServiceClient.prototype.isMatchFinished = function(matchId, callback) {
  this._seqid = this.new_seqid();
  if (callback === undefined) {
    var _defer = Q.defer();
    this._reqs[this.seqid()] = function(error, result) {
      if (error) {
        _defer.reject(error);
      } else {
        _defer.resolve(result);
      }
    };
    this.send_isMatchFinished(matchId);
    return _defer.promise;
  } else {
    this._reqs[this.seqid()] = callback;
    this.send_isMatchFinished(matchId);
  }
};

SimulationServiceClient.prototype.send_isMatchFinished = function(matchId) {
  var output = new this.pClass(this.output);
  output.writeMessageBegin('isMatchFinished', Thrift.MessageType.CALL, this.seqid());
  var args = new SimulationService_isMatchFinished_args();
  args.matchId = matchId;
  args.write(output);
  output.writeMessageEnd();
  return this.output.flush();
};

SimulationServiceClient.prototype.recv_isMatchFinished = function(input,mtype,rseqid) {
  var callback = this._reqs[rseqid] || function() {};
  delete this._reqs[rseqid];
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(input);
    input.readMessageEnd();
    return callback(x);
  }
  var result = new SimulationService_isMatchFinished_result();
  result.read(input);
  input.readMessageEnd();

  if (null !== result.exc) {
    return callback(result.exc);
  }
  if (null !== result.success) {
    return callback(null, result.success);
  }
  return callback('isMatchFinished failed: unknown result');
};
SimulationServiceClient.prototype.result = function(matchId, callback) {
  this._seqid = this.new_seqid();
  if (callback === undefined) {
    var _defer = Q.defer();
    this._reqs[this.seqid()] = function(error, result) {
      if (error) {
        _defer.reject(error);
      } else {
        _defer.resolve(result);
      }
    };
    this.send_result(matchId);
    return _defer.promise;
  } else {
    this._reqs[this.seqid()] = callback;
    this.send_result(matchId);
  }
};

SimulationServiceClient.prototype.send_result = function(matchId) {
  var output = new this.pClass(this.output);
  output.writeMessageBegin('result', Thrift.MessageType.CALL, this.seqid());
  var args = new SimulationService_result_args();
  args.matchId = matchId;
  args.write(output);
  output.writeMessageEnd();
  return this.output.flush();
};

SimulationServiceClient.prototype.recv_result = function(input,mtype,rseqid) {
  var callback = this._reqs[rseqid] || function() {};
  delete this._reqs[rseqid];
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(input);
    input.readMessageEnd();
    return callback(x);
  }
  var result = new SimulationService_result_result();
  result.read(input);
  input.readMessageEnd();

  if (null !== result.exc) {
    return callback(result.exc);
  }
  if (null !== result.success) {
    return callback(null, result.success);
  }
  return callback('result failed: unknown result');
};
SimulationServiceClient.prototype.snapshots = function(matchId, callback) {
  this._seqid = this.new_seqid();
  if (callback === undefined) {
    var _defer = Q.defer();
    this._reqs[this.seqid()] = function(error, result) {
      if (error) {
        _defer.reject(error);
      } else {
        _defer.resolve(result);
      }
    };
    this.send_snapshots(matchId);
    return _defer.promise;
  } else {
    this._reqs[this.seqid()] = callback;
    this.send_snapshots(matchId);
  }
};

SimulationServiceClient.prototype.send_snapshots = function(matchId) {
  var output = new this.pClass(this.output);
  output.writeMessageBegin('snapshots', Thrift.MessageType.CALL, this.seqid());
  var args = new SimulationService_snapshots_args();
  args.matchId = matchId;
  args.write(output);
  output.writeMessageEnd();
  return this.output.flush();
};

SimulationServiceClient.prototype.recv_snapshots = function(input,mtype,rseqid) {
  var callback = this._reqs[rseqid] || function() {};
  delete this._reqs[rseqid];
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(input);
    input.readMessageEnd();
    return callback(x);
  }
  var result = new SimulationService_snapshots_result();
  result.read(input);
  input.readMessageEnd();

  if (null !== result.exc) {
    return callback(result.exc);
  }
  if (null !== result.success) {
    return callback(null, result.success);
  }
  return callback('snapshots failed: unknown result');
};
SimulationServiceClient.prototype.compileScript = function(script, callback) {
  this._seqid = this.new_seqid();
  if (callback === undefined) {
    var _defer = Q.defer();
    this._reqs[this.seqid()] = function(error, result) {
      if (error) {
        _defer.reject(error);
      } else {
        _defer.resolve(result);
      }
    };
    this.send_compileScript(script);
    return _defer.promise;
  } else {
    this._reqs[this.seqid()] = callback;
    this.send_compileScript(script);
  }
};

SimulationServiceClient.prototype.send_compileScript = function(script) {
  var output = new this.pClass(this.output);
  output.writeMessageBegin('compileScript', Thrift.MessageType.CALL, this.seqid());
  var args = new SimulationService_compileScript_args();
  args.script = script;
  args.write(output);
  output.writeMessageEnd();
  return this.output.flush();
};

SimulationServiceClient.prototype.recv_compileScript = function(input,mtype,rseqid) {
  var callback = this._reqs[rseqid] || function() {};
  delete this._reqs[rseqid];
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(input);
    input.readMessageEnd();
    return callback(x);
  }
  var result = new SimulationService_compileScript_result();
  result.read(input);
  input.readMessageEnd();

  if (null !== result.success) {
    return callback(null, result.success);
  }
  return callback('compileScript failed: unknown result');
};
SimulationServiceProcessor = exports.Processor = function(handler) {
  this._handler = handler
}
SimulationServiceProcessor.prototype.process = function(input, output) {
  var r = input.readMessageBegin();
  if (this['process_' + r.fname]) {
    return this['process_' + r.fname].call(this, r.rseqid, input, output);
  } else {
    input.skip(Thrift.Type.STRUCT);
    input.readMessageEnd();
    var x = new Thrift.TApplicationException(Thrift.TApplicationExceptionType.UNKNOWN_METHOD, 'Unknown function ' + r.fname);
    output.writeMessageBegin(r.fname, Thrift.MessageType.EXCEPTION, r.rseqid);
    x.write(output);
    output.writeMessageEnd();
    output.flush();
  }
}

SimulationServiceProcessor.prototype.process_startMatch = function(seqid, input, output) {
  var args = new SimulationService_startMatch_args();
  args.read(input);
  input.readMessageEnd();
  if (this._handler.startMatch.length === 1) {
    Q.fcall(this._handler.startMatch, args.match)
      .then(function(result) {
        var result = new SimulationService_startMatch_result({success: result});
        output.writeMessageBegin("startMatch", Thrift.MessageType.REPLY, seqid);
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      }, function (err) {
        var result = new SimulationService_startMatch_result(err);
        output.writeMessageBegin("startMatch", Thrift.MessageType.REPLY, seqid);
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      });
  } else {
    this._handler.startMatch(args.match,  function (err, result) {
      var result = new SimulationService_startMatch_result((err != null ? err : {success: result}));
      output.writeMessageBegin("startMatch", Thrift.MessageType.REPLY, seqid);
      result.write(output);
      output.writeMessageEnd();
      output.flush();
    });
  }
}

SimulationServiceProcessor.prototype.process_finalizeMatch = function(seqid, input, output) {
  var args = new SimulationService_finalizeMatch_args();
  args.read(input);
  input.readMessageEnd();
  if (this._handler.finalizeMatch.length === 1) {
    Q.fcall(this._handler.finalizeMatch, args.matchId)
      .then(function(result) {
        var result = new SimulationService_finalizeMatch_result({success: result});
        output.writeMessageBegin("finalizeMatch", Thrift.MessageType.REPLY, seqid);
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      }, function (err) {
        var result = new SimulationService_finalizeMatch_result(err);
        output.writeMessageBegin("finalizeMatch", Thrift.MessageType.REPLY, seqid);
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      });
  } else {
    this._handler.finalizeMatch(args.matchId,  function (err, result) {
      var result = new SimulationService_finalizeMatch_result((err != null ? err : {success: result}));
      output.writeMessageBegin("finalizeMatch", Thrift.MessageType.REPLY, seqid);
      result.write(output);
      output.writeMessageEnd();
      output.flush();
    });
  }
}

SimulationServiceProcessor.prototype.process_isMatchFinished = function(seqid, input, output) {
  var args = new SimulationService_isMatchFinished_args();
  args.read(input);
  input.readMessageEnd();
  if (this._handler.isMatchFinished.length === 1) {
    Q.fcall(this._handler.isMatchFinished, args.matchId)
      .then(function(result) {
        var result = new SimulationService_isMatchFinished_result({success: result});
        output.writeMessageBegin("isMatchFinished", Thrift.MessageType.REPLY, seqid);
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      }, function (err) {
        var result = new SimulationService_isMatchFinished_result(err);
        output.writeMessageBegin("isMatchFinished", Thrift.MessageType.REPLY, seqid);
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      });
  } else {
    this._handler.isMatchFinished(args.matchId,  function (err, result) {
      var result = new SimulationService_isMatchFinished_result((err != null ? err : {success: result}));
      output.writeMessageBegin("isMatchFinished", Thrift.MessageType.REPLY, seqid);
      result.write(output);
      output.writeMessageEnd();
      output.flush();
    });
  }
}

SimulationServiceProcessor.prototype.process_result = function(seqid, input, output) {
  var args = new SimulationService_result_args();
  args.read(input);
  input.readMessageEnd();
  if (this._handler.result.length === 1) {
    Q.fcall(this._handler.result, args.matchId)
      .then(function(result) {
        var result = new SimulationService_result_result({success: result});
        output.writeMessageBegin("result", Thrift.MessageType.REPLY, seqid);
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      }, function (err) {
        var result = new SimulationService_result_result(err);
        output.writeMessageBegin("result", Thrift.MessageType.REPLY, seqid);
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      });
  } else {
    this._handler.result(args.matchId,  function (err, result) {
      var result = new SimulationService_result_result((err != null ? err : {success: result}));
      output.writeMessageBegin("result", Thrift.MessageType.REPLY, seqid);
      result.write(output);
      output.writeMessageEnd();
      output.flush();
    });
  }
}

SimulationServiceProcessor.prototype.process_snapshots = function(seqid, input, output) {
  var args = new SimulationService_snapshots_args();
  args.read(input);
  input.readMessageEnd();
  if (this._handler.snapshots.length === 1) {
    Q.fcall(this._handler.snapshots, args.matchId)
      .then(function(result) {
        var result = new SimulationService_snapshots_result({success: result});
        output.writeMessageBegin("snapshots", Thrift.MessageType.REPLY, seqid);
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      }, function (err) {
        var result = new SimulationService_snapshots_result(err);
        output.writeMessageBegin("snapshots", Thrift.MessageType.REPLY, seqid);
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      });
  } else {
    this._handler.snapshots(args.matchId,  function (err, result) {
      var result = new SimulationService_snapshots_result((err != null ? err : {success: result}));
      output.writeMessageBegin("snapshots", Thrift.MessageType.REPLY, seqid);
      result.write(output);
      output.writeMessageEnd();
      output.flush();
    });
  }
}

SimulationServiceProcessor.prototype.process_compileScript = function(seqid, input, output) {
  var args = new SimulationService_compileScript_args();
  args.read(input);
  input.readMessageEnd();
  if (this._handler.compileScript.length === 1) {
    Q.fcall(this._handler.compileScript, args.script)
      .then(function(result) {
        var result = new SimulationService_compileScript_result({success: result});
        output.writeMessageBegin("compileScript", Thrift.MessageType.REPLY, seqid);
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      }, function (err) {
        var result = new SimulationService_compileScript_result(err);
        output.writeMessageBegin("compileScript", Thrift.MessageType.REPLY, seqid);
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      });
  } else {
    this._handler.compileScript(args.script,  function (err, result) {
      var result = new SimulationService_compileScript_result((err != null ? err : {success: result}));
      output.writeMessageBegin("compileScript", Thrift.MessageType.REPLY, seqid);
      result.write(output);
      output.writeMessageEnd();
      output.flush();
    });
  }
}

