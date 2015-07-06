//
// Autogenerated by Thrift Compiler (0.9.2)
//
// DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//
var thrift = require('thrift');
var Thrift = thrift.Thrift;
var Q = thrift.Q;


var ttypes = module.exports = {};
ttypes.State = {
  'IDLE' : 1,
  'MOVING' : 2,
  'CASTING' : 3,
  'TACKLING' : 4,
  'DEAD' : 5
};
Vector2 = module.exports.Vector2 = function(args) {
  this.x = null;
  this.y = null;
  if (args) {
    if (args.x !== undefined) {
      this.x = args.x;
    }
    if (args.y !== undefined) {
      this.y = args.y;
    }
  }
};
Vector2.prototype = {};
Vector2.prototype.read = function(input) {
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
      if (ftype == Thrift.Type.DOUBLE) {
        this.x = input.readDouble();
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.DOUBLE) {
        this.y = input.readDouble();
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

Vector2.prototype.write = function(output) {
  output.writeStructBegin('Vector2');
  if (this.x !== null && this.x !== undefined) {
    output.writeFieldBegin('x', Thrift.Type.DOUBLE, 1);
    output.writeDouble(this.x);
    output.writeFieldEnd();
  }
  if (this.y !== null && this.y !== undefined) {
    output.writeFieldBegin('y', Thrift.Type.DOUBLE, 2);
    output.writeDouble(this.y);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

StateChange = module.exports.StateChange = function(args) {
  this.targetState = null;
  this.duration = null;
  if (args) {
    if (args.targetState !== undefined) {
      this.targetState = args.targetState;
    }
    if (args.duration !== undefined) {
      this.duration = args.duration;
    }
  }
};
StateChange.prototype = {};
StateChange.prototype.read = function(input) {
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
      if (ftype == Thrift.Type.I32) {
        this.targetState = input.readI32();
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.I32) {
        this.duration = input.readI32();
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

StateChange.prototype.write = function(output) {
  output.writeStructBegin('StateChange');
  if (this.targetState !== null && this.targetState !== undefined) {
    output.writeFieldBegin('targetState', Thrift.Type.I32, 1);
    output.writeI32(this.targetState);
    output.writeFieldEnd();
  }
  if (this.duration !== null && this.duration !== undefined) {
    output.writeFieldBegin('duration', Thrift.Type.I32, 2);
    output.writeI32(this.duration);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

Actor = module.exports.Actor = function(args) {
  this.id = null;
  this.position = null;
  this.angle = null;
  this.hp = null;
  this.width = null;
  this.stateChange = null;
  if (args) {
    if (args.id !== undefined) {
      this.id = args.id;
    }
    if (args.position !== undefined) {
      this.position = args.position;
    }
    if (args.angle !== undefined) {
      this.angle = args.angle;
    }
    if (args.hp !== undefined) {
      this.hp = args.hp;
    }
    if (args.width !== undefined) {
      this.width = args.width;
    }
    if (args.stateChange !== undefined) {
      this.stateChange = args.stateChange;
    }
  }
};
Actor.prototype = {};
Actor.prototype.read = function(input) {
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
        this.id = input.readString();
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.STRUCT) {
        this.position = new ttypes.Vector2();
        this.position.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.DOUBLE) {
        this.angle = input.readDouble();
      } else {
        input.skip(ftype);
      }
      break;
      case 4:
      if (ftype == Thrift.Type.DOUBLE) {
        this.hp = input.readDouble();
      } else {
        input.skip(ftype);
      }
      break;
      case 5:
      if (ftype == Thrift.Type.DOUBLE) {
        this.width = input.readDouble();
      } else {
        input.skip(ftype);
      }
      break;
      case 6:
      if (ftype == Thrift.Type.STRUCT) {
        this.stateChange = new ttypes.StateChange();
        this.stateChange.read(input);
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

Actor.prototype.write = function(output) {
  output.writeStructBegin('Actor');
  if (this.id !== null && this.id !== undefined) {
    output.writeFieldBegin('id', Thrift.Type.STRING, 1);
    output.writeString(this.id);
    output.writeFieldEnd();
  }
  if (this.position !== null && this.position !== undefined) {
    output.writeFieldBegin('position', Thrift.Type.STRUCT, 2);
    this.position.write(output);
    output.writeFieldEnd();
  }
  if (this.angle !== null && this.angle !== undefined) {
    output.writeFieldBegin('angle', Thrift.Type.DOUBLE, 3);
    output.writeDouble(this.angle);
    output.writeFieldEnd();
  }
  if (this.hp !== null && this.hp !== undefined) {
    output.writeFieldBegin('hp', Thrift.Type.DOUBLE, 4);
    output.writeDouble(this.hp);
    output.writeFieldEnd();
  }
  if (this.width !== null && this.width !== undefined) {
    output.writeFieldBegin('width', Thrift.Type.DOUBLE, 5);
    output.writeDouble(this.width);
    output.writeFieldEnd();
  }
  if (this.stateChange !== null && this.stateChange !== undefined) {
    output.writeFieldBegin('stateChange', Thrift.Type.STRUCT, 6);
    this.stateChange.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

