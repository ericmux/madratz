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
public partial class SnapshotsResult : TBase
{
  private List<Snapshot> _snapshotList;

  public List<Snapshot> SnapshotList
  {
    get
    {
      return _snapshotList;
    }
    set
    {
      __isset.snapshotList = true;
      this._snapshotList = value;
    }
  }


  public Isset __isset;
  #if !SILVERLIGHT
  [Serializable]
  #endif
  public struct Isset {
    public bool snapshotList;
  }

  public SnapshotsResult() {
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
          if (field.Type == TType.List) {
            {
              SnapshotList = new List<Snapshot>();
              TList _list4 = iprot.ReadListBegin();
              for( int _i5 = 0; _i5 < _list4.Count; ++_i5)
              {
                Snapshot _elem6;
                _elem6 = new Snapshot();
                _elem6.Read(iprot);
                SnapshotList.Add(_elem6);
              }
              iprot.ReadListEnd();
            }
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
    TStruct struc = new TStruct("SnapshotsResult");
    oprot.WriteStructBegin(struc);
    TField field = new TField();
    if (SnapshotList != null && __isset.snapshotList) {
      field.Name = "snapshotList";
      field.Type = TType.List;
      field.ID = 1;
      oprot.WriteFieldBegin(field);
      {
        oprot.WriteListBegin(new TList(TType.Struct, SnapshotList.Count));
        foreach (Snapshot _iter7 in SnapshotList)
        {
          _iter7.Write(oprot);
        }
        oprot.WriteListEnd();
      }
      oprot.WriteFieldEnd();
    }
    oprot.WriteFieldStop();
    oprot.WriteStructEnd();
  }

  public override string ToString() {
    StringBuilder __sb = new StringBuilder("SnapshotsResult(");
    bool __first = true;
    if (SnapshotList != null && __isset.snapshotList) {
      if(!__first) { __sb.Append(", "); }
      __first = false;
      __sb.Append("SnapshotList: ");
      __sb.Append(SnapshotList);
    }
    __sb.Append(")");
    return __sb.ToString();
  }

}

