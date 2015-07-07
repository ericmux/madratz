/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.IO;
using Thrift;
using Thrift.Collections;
using System.Runtime.Serialization;
using Thrift.Protocol;
using Thrift.Transport;


#if !SILVERLIGHT
[Serializable]
#endif
public partial class Snapshot : TBase
{
  private double _elapsedTime;
  private List<Actor> _actors;
  private bool _finished;

  public double ElapsedTime
  {
    get
    {
      return _elapsedTime;
    }
    set
    {
      __isset.elapsedTime = true;
      this._elapsedTime = value;
    }
  }

  public List<Actor> Actors
  {
    get
    {
      return _actors;
    }
    set
    {
      __isset.actors = true;
      this._actors = value;
    }
  }

  public bool Finished
  {
    get
    {
      return _finished;
    }
    set
    {
      __isset.finished = true;
      this._finished = value;
    }
  }


  public Isset __isset;
  #if !SILVERLIGHT
  [Serializable]
  #endif
  public struct Isset {
    public bool elapsedTime;
    public bool actors;
    public bool finished;
  }

  public Snapshot() {
  }

  public void Read (TProtocol iprot)
  {
    TField field;
    iprot.ReadStructBegin();
    while (true)
    {
      field = iprot.ReadFieldBegin();
      if (field.Type == TType.Stop) { 
        break;
      }
      switch (field.ID)
      {
        case 1:
          if (field.Type == TType.Double) {
            ElapsedTime = iprot.ReadDouble();
          } else { 
            TProtocolUtil.Skip(iprot, field.Type);
          }
          break;
        case 2:
          if (field.Type == TType.List) {
            {
              Actors = new List<Actor>();
              TList _list0 = iprot.ReadListBegin();
              for( int _i1 = 0; _i1 < _list0.Count; ++_i1)
              {
                Actor _elem2;
                _elem2 = new Actor();
                _elem2.Read(iprot);
                Actors.Add(_elem2);
              }
              iprot.ReadListEnd();
            }
          } else { 
            TProtocolUtil.Skip(iprot, field.Type);
          }
          break;
        case 3:
          if (field.Type == TType.Bool) {
            Finished = iprot.ReadBool();
          } else { 
            TProtocolUtil.Skip(iprot, field.Type);
          }
          break;
        default: 
          TProtocolUtil.Skip(iprot, field.Type);
          break;
      }
      iprot.ReadFieldEnd();
    }
    iprot.ReadStructEnd();
  }

  public void Write(TProtocol oprot) {
    TStruct struc = new TStruct("Snapshot");
    oprot.WriteStructBegin(struc);
    TField field = new TField();
    if (__isset.elapsedTime) {
      field.Name = "elapsedTime";
      field.Type = TType.Double;
      field.ID = 1;
      oprot.WriteFieldBegin(field);
      oprot.WriteDouble(ElapsedTime);
      oprot.WriteFieldEnd();
    }
    if (Actors != null && __isset.actors) {
      field.Name = "actors";
      field.Type = TType.List;
      field.ID = 2;
      oprot.WriteFieldBegin(field);
      {
        oprot.WriteListBegin(new TList(TType.Struct, Actors.Count));
        foreach (Actor _iter3 in Actors)
        {
          _iter3.Write(oprot);
        }
        oprot.WriteListEnd();
      }
      oprot.WriteFieldEnd();
    }
    if (__isset.finished) {
      field.Name = "finished";
      field.Type = TType.Bool;
      field.ID = 3;
      oprot.WriteFieldBegin(field);
      oprot.WriteBool(Finished);
      oprot.WriteFieldEnd();
    }
    oprot.WriteFieldStop();
    oprot.WriteStructEnd();
  }

  public override string ToString() {
    StringBuilder __sb = new StringBuilder("Snapshot(");
    bool __first = true;
    if (__isset.elapsedTime) {
      if(!__first) { __sb.Append(", "); }
      __first = false;
      __sb.Append("ElapsedTime: ");
      __sb.Append(ElapsedTime);
    }
    if (Actors != null && __isset.actors) {
      if(!__first) { __sb.Append(", "); }
      __first = false;
      __sb.Append("Actors: ");
      __sb.Append(Actors);
    }
    if (__isset.finished) {
      if(!__first) { __sb.Append(", "); }
      __first = false;
      __sb.Append("Finished: ");
      __sb.Append(Finished);
    }
    __sb.Append(")");
    return __sb.ToString();
  }

}
