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

public partial class SimulationService {
  public interface Iface {
    long startMatch(MatchParams match);
    #if SILVERLIGHT
    IAsyncResult Begin_startMatch(AsyncCallback callback, object state, MatchParams match);
    long End_startMatch(IAsyncResult asyncResult);
    #endif
    bool isMatchFinished(long matchId);
    #if SILVERLIGHT
    IAsyncResult Begin_isMatchFinished(AsyncCallback callback, object state, long matchId);
    bool End_isMatchFinished(IAsyncResult asyncResult);
    #endif
    MatchResult result(long matchId);
    #if SILVERLIGHT
    IAsyncResult Begin_result(AsyncCallback callback, object state, long matchId);
    MatchResult End_result(IAsyncResult asyncResult);
    #endif
    List<Snapshot> snapshots(long matchId);
    #if SILVERLIGHT
    IAsyncResult Begin_snapshots(AsyncCallback callback, object state, long matchId);
    List<Snapshot> End_snapshots(IAsyncResult asyncResult);
    #endif
  }

  public class Client : IDisposable, Iface {
    public Client(TProtocol prot) : this(prot, prot)
    {
    }

    public Client(TProtocol iprot, TProtocol oprot)
    {
      iprot_ = iprot;
      oprot_ = oprot;
    }

    protected TProtocol iprot_;
    protected TProtocol oprot_;
    protected int seqid_;

    public TProtocol InputProtocol
    {
      get { return iprot_; }
    }
    public TProtocol OutputProtocol
    {
      get { return oprot_; }
    }


    #region " IDisposable Support "
    private bool _IsDisposed;

    // IDisposable
    public void Dispose()
    {
      Dispose(true);
    }
    

    protected virtual void Dispose(bool disposing)
    {
      if (!_IsDisposed)
      {
        if (disposing)
        {
          if (iprot_ != null)
          {
            ((IDisposable)iprot_).Dispose();
          }
          if (oprot_ != null)
          {
            ((IDisposable)oprot_).Dispose();
          }
        }
      }
      _IsDisposed = true;
    }
    #endregion


    
    #if SILVERLIGHT
    public IAsyncResult Begin_startMatch(AsyncCallback callback, object state, MatchParams match)
    {
      return send_startMatch(callback, state, match);
    }

    public long End_startMatch(IAsyncResult asyncResult)
    {
      oprot_.Transport.EndFlush(asyncResult);
      return recv_startMatch();
    }

    #endif

    public long startMatch(MatchParams match)
    {
      #if !SILVERLIGHT
      send_startMatch(match);
      return recv_startMatch();

      #else
      var asyncResult = Begin_startMatch(null, null, match);
      return End_startMatch(asyncResult);

      #endif
    }
    #if SILVERLIGHT
    public IAsyncResult send_startMatch(AsyncCallback callback, object state, MatchParams match)
    #else
    public void send_startMatch(MatchParams match)
    #endif
    {
      oprot_.WriteMessageBegin(new TMessage("startMatch", TMessageType.Call, seqid_));
      startMatch_args args = new startMatch_args();
      args.Match = match;
      args.Write(oprot_);
      oprot_.WriteMessageEnd();
      #if SILVERLIGHT
      return oprot_.Transport.BeginFlush(callback, state);
      #else
      oprot_.Transport.Flush();
      #endif
    }

    public long recv_startMatch()
    {
      TMessage msg = iprot_.ReadMessageBegin();
      if (msg.Type == TMessageType.Exception) {
        TApplicationException x = TApplicationException.Read(iprot_);
        iprot_.ReadMessageEnd();
        throw x;
      }
      startMatch_result result = new startMatch_result();
      result.Read(iprot_);
      iprot_.ReadMessageEnd();
      if (result.__isset.success) {
        return result.Success;
      }
      if (result.__isset.exc) {
        throw result.Exc;
      }
      throw new TApplicationException(TApplicationException.ExceptionType.MissingResult, "startMatch failed: unknown result");
    }

    
    #if SILVERLIGHT
    public IAsyncResult Begin_isMatchFinished(AsyncCallback callback, object state, long matchId)
    {
      return send_isMatchFinished(callback, state, matchId);
    }

    public bool End_isMatchFinished(IAsyncResult asyncResult)
    {
      oprot_.Transport.EndFlush(asyncResult);
      return recv_isMatchFinished();
    }

    #endif

    public bool isMatchFinished(long matchId)
    {
      #if !SILVERLIGHT
      send_isMatchFinished(matchId);
      return recv_isMatchFinished();

      #else
      var asyncResult = Begin_isMatchFinished(null, null, matchId);
      return End_isMatchFinished(asyncResult);

      #endif
    }
    #if SILVERLIGHT
    public IAsyncResult send_isMatchFinished(AsyncCallback callback, object state, long matchId)
    #else
    public void send_isMatchFinished(long matchId)
    #endif
    {
      oprot_.WriteMessageBegin(new TMessage("isMatchFinished", TMessageType.Call, seqid_));
      isMatchFinished_args args = new isMatchFinished_args();
      args.MatchId = matchId;
      args.Write(oprot_);
      oprot_.WriteMessageEnd();
      #if SILVERLIGHT
      return oprot_.Transport.BeginFlush(callback, state);
      #else
      oprot_.Transport.Flush();
      #endif
    }

    public bool recv_isMatchFinished()
    {
      TMessage msg = iprot_.ReadMessageBegin();
      if (msg.Type == TMessageType.Exception) {
        TApplicationException x = TApplicationException.Read(iprot_);
        iprot_.ReadMessageEnd();
        throw x;
      }
      isMatchFinished_result result = new isMatchFinished_result();
      result.Read(iprot_);
      iprot_.ReadMessageEnd();
      if (result.__isset.success) {
        return result.Success;
      }
      if (result.__isset.exc) {
        throw result.Exc;
      }
      throw new TApplicationException(TApplicationException.ExceptionType.MissingResult, "isMatchFinished failed: unknown result");
    }

    
    #if SILVERLIGHT
    public IAsyncResult Begin_result(AsyncCallback callback, object state, long matchId)
    {
      return send_result(callback, state, matchId);
    }

    public MatchResult End_result(IAsyncResult asyncResult)
    {
      oprot_.Transport.EndFlush(asyncResult);
      return recv_result();
    }

    #endif

    public MatchResult result(long matchId)
    {
      #if !SILVERLIGHT
      send_result(matchId);
      return recv_result();

      #else
      var asyncResult = Begin_result(null, null, matchId);
      return End_result(asyncResult);

      #endif
    }
    #if SILVERLIGHT
    public IAsyncResult send_result(AsyncCallback callback, object state, long matchId)
    #else
    public void send_result(long matchId)
    #endif
    {
      oprot_.WriteMessageBegin(new TMessage("result", TMessageType.Call, seqid_));
      result_args args = new result_args();
      args.MatchId = matchId;
      args.Write(oprot_);
      oprot_.WriteMessageEnd();
      #if SILVERLIGHT
      return oprot_.Transport.BeginFlush(callback, state);
      #else
      oprot_.Transport.Flush();
      #endif
    }

    public MatchResult recv_result()
    {
      TMessage msg = iprot_.ReadMessageBegin();
      if (msg.Type == TMessageType.Exception) {
        TApplicationException x = TApplicationException.Read(iprot_);
        iprot_.ReadMessageEnd();
        throw x;
      }
      result_result result = new result_result();
      result.Read(iprot_);
      iprot_.ReadMessageEnd();
      if (result.__isset.success) {
        return result.Success;
      }
      if (result.__isset.exc) {
        throw result.Exc;
      }
      throw new TApplicationException(TApplicationException.ExceptionType.MissingResult, "result failed: unknown result");
    }

    
    #if SILVERLIGHT
    public IAsyncResult Begin_snapshots(AsyncCallback callback, object state, long matchId)
    {
      return send_snapshots(callback, state, matchId);
    }

    public List<Snapshot> End_snapshots(IAsyncResult asyncResult)
    {
      oprot_.Transport.EndFlush(asyncResult);
      return recv_snapshots();
    }

    #endif

    public List<Snapshot> snapshots(long matchId)
    {
      #if !SILVERLIGHT
      send_snapshots(matchId);
      return recv_snapshots();

      #else
      var asyncResult = Begin_snapshots(null, null, matchId);
      return End_snapshots(asyncResult);

      #endif
    }
    #if SILVERLIGHT
    public IAsyncResult send_snapshots(AsyncCallback callback, object state, long matchId)
    #else
    public void send_snapshots(long matchId)
    #endif
    {
      oprot_.WriteMessageBegin(new TMessage("snapshots", TMessageType.Call, seqid_));
      snapshots_args args = new snapshots_args();
      args.MatchId = matchId;
      args.Write(oprot_);
      oprot_.WriteMessageEnd();
      #if SILVERLIGHT
      return oprot_.Transport.BeginFlush(callback, state);
      #else
      oprot_.Transport.Flush();
      #endif
    }

    public List<Snapshot> recv_snapshots()
    {
      TMessage msg = iprot_.ReadMessageBegin();
      if (msg.Type == TMessageType.Exception) {
        TApplicationException x = TApplicationException.Read(iprot_);
        iprot_.ReadMessageEnd();
        throw x;
      }
      snapshots_result result = new snapshots_result();
      result.Read(iprot_);
      iprot_.ReadMessageEnd();
      if (result.__isset.success) {
        return result.Success;
      }
      if (result.__isset.exc) {
        throw result.Exc;
      }
      throw new TApplicationException(TApplicationException.ExceptionType.MissingResult, "snapshots failed: unknown result");
    }

  }
  public class Processor : TProcessor {
    public Processor(Iface iface)
    {
      iface_ = iface;
      processMap_["startMatch"] = startMatch_Process;
      processMap_["isMatchFinished"] = isMatchFinished_Process;
      processMap_["result"] = result_Process;
      processMap_["snapshots"] = snapshots_Process;
    }

    protected delegate void ProcessFunction(int seqid, TProtocol iprot, TProtocol oprot);
    private Iface iface_;
    protected Dictionary<string, ProcessFunction> processMap_ = new Dictionary<string, ProcessFunction>();

    public bool Process(TProtocol iprot, TProtocol oprot)
    {
      try
      {
        TMessage msg = iprot.ReadMessageBegin();
        ProcessFunction fn;
        processMap_.TryGetValue(msg.Name, out fn);
        if (fn == null) {
          TProtocolUtil.Skip(iprot, TType.Struct);
          iprot.ReadMessageEnd();
          TApplicationException x = new TApplicationException (TApplicationException.ExceptionType.UnknownMethod, "Invalid method name: '" + msg.Name + "'");
          oprot.WriteMessageBegin(new TMessage(msg.Name, TMessageType.Exception, msg.SeqID));
          x.Write(oprot);
          oprot.WriteMessageEnd();
          oprot.Transport.Flush();
          return true;
        }
        fn(msg.SeqID, iprot, oprot);
      }
      catch (IOException)
      {
        return false;
      }
      return true;
    }

    public void startMatch_Process(int seqid, TProtocol iprot, TProtocol oprot)
    {
      startMatch_args args = new startMatch_args();
      args.Read(iprot);
      iprot.ReadMessageEnd();
      startMatch_result result = new startMatch_result();
      try {
        result.Success = iface_.startMatch(args.Match);
      } catch (InvalidArgumentException exc) {
        result.Exc = exc;
      }
      oprot.WriteMessageBegin(new TMessage("startMatch", TMessageType.Reply, seqid)); 
      result.Write(oprot);
      oprot.WriteMessageEnd();
      oprot.Transport.Flush();
    }

    public void isMatchFinished_Process(int seqid, TProtocol iprot, TProtocol oprot)
    {
      isMatchFinished_args args = new isMatchFinished_args();
      args.Read(iprot);
      iprot.ReadMessageEnd();
      isMatchFinished_result result = new isMatchFinished_result();
      try {
        result.Success = iface_.isMatchFinished(args.MatchId);
      } catch (InvalidArgumentException exc) {
        result.Exc = exc;
      }
      oprot.WriteMessageBegin(new TMessage("isMatchFinished", TMessageType.Reply, seqid)); 
      result.Write(oprot);
      oprot.WriteMessageEnd();
      oprot.Transport.Flush();
    }

    public void result_Process(int seqid, TProtocol iprot, TProtocol oprot)
    {
      result_args args = new result_args();
      args.Read(iprot);
      iprot.ReadMessageEnd();
      result_result result = new result_result();
      try {
        result.Success = iface_.result(args.MatchId);
      } catch (InvalidArgumentException exc) {
        result.Exc = exc;
      }
      oprot.WriteMessageBegin(new TMessage("result", TMessageType.Reply, seqid)); 
      result.Write(oprot);
      oprot.WriteMessageEnd();
      oprot.Transport.Flush();
    }

    public void snapshots_Process(int seqid, TProtocol iprot, TProtocol oprot)
    {
      snapshots_args args = new snapshots_args();
      args.Read(iprot);
      iprot.ReadMessageEnd();
      snapshots_result result = new snapshots_result();
      try {
        result.Success = iface_.snapshots(args.MatchId);
      } catch (InvalidArgumentException exc) {
        result.Exc = exc;
      }
      oprot.WriteMessageBegin(new TMessage("snapshots", TMessageType.Reply, seqid)); 
      result.Write(oprot);
      oprot.WriteMessageEnd();
      oprot.Transport.Flush();
    }

  }


  #if !SILVERLIGHT
  [Serializable]
  #endif
  public partial class startMatch_args : TBase
  {
    private MatchParams _match;

    public MatchParams Match
    {
      get
      {
        return _match;
      }
      set
      {
        __isset.match = true;
        this._match = value;
      }
    }


    public Isset __isset;
    #if !SILVERLIGHT
    [Serializable]
    #endif
    public struct Isset {
      public bool match;
    }

    public startMatch_args() {
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
            if (field.Type == TType.Struct) {
              Match = new MatchParams();
              Match.Read(iprot);
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
      TStruct struc = new TStruct("startMatch_args");
      oprot.WriteStructBegin(struc);
      TField field = new TField();
      if (Match != null && __isset.match) {
        field.Name = "match";
        field.Type = TType.Struct;
        field.ID = 1;
        oprot.WriteFieldBegin(field);
        Match.Write(oprot);
        oprot.WriteFieldEnd();
      }
      oprot.WriteFieldStop();
      oprot.WriteStructEnd();
    }

    public override string ToString() {
      StringBuilder __sb = new StringBuilder("startMatch_args(");
      bool __first = true;
      if (Match != null && __isset.match) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Match: ");
        __sb.Append(Match== null ? "<null>" : Match.ToString());
      }
      __sb.Append(")");
      return __sb.ToString();
    }

  }


  #if !SILVERLIGHT
  [Serializable]
  #endif
  public partial class startMatch_result : TBase
  {
    private long _success;
    private InvalidArgumentException _exc;

    public long Success
    {
      get
      {
        return _success;
      }
      set
      {
        __isset.success = true;
        this._success = value;
      }
    }

    public InvalidArgumentException Exc
    {
      get
      {
        return _exc;
      }
      set
      {
        __isset.exc = true;
        this._exc = value;
      }
    }


    public Isset __isset;
    #if !SILVERLIGHT
    [Serializable]
    #endif
    public struct Isset {
      public bool success;
      public bool exc;
    }

    public startMatch_result() {
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
          case 0:
            if (field.Type == TType.I64) {
              Success = iprot.ReadI64();
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 1:
            if (field.Type == TType.Struct) {
              Exc = new InvalidArgumentException();
              Exc.Read(iprot);
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
      TStruct struc = new TStruct("startMatch_result");
      oprot.WriteStructBegin(struc);
      TField field = new TField();

      if (this.__isset.success) {
        field.Name = "Success";
        field.Type = TType.I64;
        field.ID = 0;
        oprot.WriteFieldBegin(field);
        oprot.WriteI64(Success);
        oprot.WriteFieldEnd();
      } else if (this.__isset.exc) {
        if (Exc != null) {
          field.Name = "Exc";
          field.Type = TType.Struct;
          field.ID = 1;
          oprot.WriteFieldBegin(field);
          Exc.Write(oprot);
          oprot.WriteFieldEnd();
        }
      }
      oprot.WriteFieldStop();
      oprot.WriteStructEnd();
    }

    public override string ToString() {
      StringBuilder __sb = new StringBuilder("startMatch_result(");
      bool __first = true;
      if (__isset.success) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Success: ");
        __sb.Append(Success);
      }
      if (Exc != null && __isset.exc) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Exc: ");
        __sb.Append(Exc== null ? "<null>" : Exc.ToString());
      }
      __sb.Append(")");
      return __sb.ToString();
    }

  }


  #if !SILVERLIGHT
  [Serializable]
  #endif
  public partial class isMatchFinished_args : TBase
  {
    private long _matchId;

    public long MatchId
    {
      get
      {
        return _matchId;
      }
      set
      {
        __isset.matchId = true;
        this._matchId = value;
      }
    }


    public Isset __isset;
    #if !SILVERLIGHT
    [Serializable]
    #endif
    public struct Isset {
      public bool matchId;
    }

    public isMatchFinished_args() {
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
            if (field.Type == TType.I64) {
              MatchId = iprot.ReadI64();
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
      TStruct struc = new TStruct("isMatchFinished_args");
      oprot.WriteStructBegin(struc);
      TField field = new TField();
      if (__isset.matchId) {
        field.Name = "matchId";
        field.Type = TType.I64;
        field.ID = 1;
        oprot.WriteFieldBegin(field);
        oprot.WriteI64(MatchId);
        oprot.WriteFieldEnd();
      }
      oprot.WriteFieldStop();
      oprot.WriteStructEnd();
    }

    public override string ToString() {
      StringBuilder __sb = new StringBuilder("isMatchFinished_args(");
      bool __first = true;
      if (__isset.matchId) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("MatchId: ");
        __sb.Append(MatchId);
      }
      __sb.Append(")");
      return __sb.ToString();
    }

  }


  #if !SILVERLIGHT
  [Serializable]
  #endif
  public partial class isMatchFinished_result : TBase
  {
    private bool _success;
    private InvalidArgumentException _exc;

    public bool Success
    {
      get
      {
        return _success;
      }
      set
      {
        __isset.success = true;
        this._success = value;
      }
    }

    public InvalidArgumentException Exc
    {
      get
      {
        return _exc;
      }
      set
      {
        __isset.exc = true;
        this._exc = value;
      }
    }


    public Isset __isset;
    #if !SILVERLIGHT
    [Serializable]
    #endif
    public struct Isset {
      public bool success;
      public bool exc;
    }

    public isMatchFinished_result() {
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
          case 0:
            if (field.Type == TType.Bool) {
              Success = iprot.ReadBool();
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 1:
            if (field.Type == TType.Struct) {
              Exc = new InvalidArgumentException();
              Exc.Read(iprot);
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
      TStruct struc = new TStruct("isMatchFinished_result");
      oprot.WriteStructBegin(struc);
      TField field = new TField();

      if (this.__isset.success) {
        field.Name = "Success";
        field.Type = TType.Bool;
        field.ID = 0;
        oprot.WriteFieldBegin(field);
        oprot.WriteBool(Success);
        oprot.WriteFieldEnd();
      } else if (this.__isset.exc) {
        if (Exc != null) {
          field.Name = "Exc";
          field.Type = TType.Struct;
          field.ID = 1;
          oprot.WriteFieldBegin(field);
          Exc.Write(oprot);
          oprot.WriteFieldEnd();
        }
      }
      oprot.WriteFieldStop();
      oprot.WriteStructEnd();
    }

    public override string ToString() {
      StringBuilder __sb = new StringBuilder("isMatchFinished_result(");
      bool __first = true;
      if (__isset.success) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Success: ");
        __sb.Append(Success);
      }
      if (Exc != null && __isset.exc) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Exc: ");
        __sb.Append(Exc== null ? "<null>" : Exc.ToString());
      }
      __sb.Append(")");
      return __sb.ToString();
    }

  }


  #if !SILVERLIGHT
  [Serializable]
  #endif
  public partial class result_args : TBase
  {
    private long _matchId;

    public long MatchId
    {
      get
      {
        return _matchId;
      }
      set
      {
        __isset.matchId = true;
        this._matchId = value;
      }
    }


    public Isset __isset;
    #if !SILVERLIGHT
    [Serializable]
    #endif
    public struct Isset {
      public bool matchId;
    }

    public result_args() {
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
            if (field.Type == TType.I64) {
              MatchId = iprot.ReadI64();
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
      TStruct struc = new TStruct("result_args");
      oprot.WriteStructBegin(struc);
      TField field = new TField();
      if (__isset.matchId) {
        field.Name = "matchId";
        field.Type = TType.I64;
        field.ID = 1;
        oprot.WriteFieldBegin(field);
        oprot.WriteI64(MatchId);
        oprot.WriteFieldEnd();
      }
      oprot.WriteFieldStop();
      oprot.WriteStructEnd();
    }

    public override string ToString() {
      StringBuilder __sb = new StringBuilder("result_args(");
      bool __first = true;
      if (__isset.matchId) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("MatchId: ");
        __sb.Append(MatchId);
      }
      __sb.Append(")");
      return __sb.ToString();
    }

  }


  #if !SILVERLIGHT
  [Serializable]
  #endif
  public partial class result_result : TBase
  {
    private MatchResult _success;
    private InvalidArgumentException _exc;

    public MatchResult Success
    {
      get
      {
        return _success;
      }
      set
      {
        __isset.success = true;
        this._success = value;
      }
    }

    public InvalidArgumentException Exc
    {
      get
      {
        return _exc;
      }
      set
      {
        __isset.exc = true;
        this._exc = value;
      }
    }


    public Isset __isset;
    #if !SILVERLIGHT
    [Serializable]
    #endif
    public struct Isset {
      public bool success;
      public bool exc;
    }

    public result_result() {
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
          case 0:
            if (field.Type == TType.Struct) {
              Success = new MatchResult();
              Success.Read(iprot);
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 1:
            if (field.Type == TType.Struct) {
              Exc = new InvalidArgumentException();
              Exc.Read(iprot);
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
      TStruct struc = new TStruct("result_result");
      oprot.WriteStructBegin(struc);
      TField field = new TField();

      if (this.__isset.success) {
        if (Success != null) {
          field.Name = "Success";
          field.Type = TType.Struct;
          field.ID = 0;
          oprot.WriteFieldBegin(field);
          Success.Write(oprot);
          oprot.WriteFieldEnd();
        }
      } else if (this.__isset.exc) {
        if (Exc != null) {
          field.Name = "Exc";
          field.Type = TType.Struct;
          field.ID = 1;
          oprot.WriteFieldBegin(field);
          Exc.Write(oprot);
          oprot.WriteFieldEnd();
        }
      }
      oprot.WriteFieldStop();
      oprot.WriteStructEnd();
    }

    public override string ToString() {
      StringBuilder __sb = new StringBuilder("result_result(");
      bool __first = true;
      if (Success != null && __isset.success) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Success: ");
        __sb.Append(Success== null ? "<null>" : Success.ToString());
      }
      if (Exc != null && __isset.exc) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Exc: ");
        __sb.Append(Exc== null ? "<null>" : Exc.ToString());
      }
      __sb.Append(")");
      return __sb.ToString();
    }

  }


  #if !SILVERLIGHT
  [Serializable]
  #endif
  public partial class snapshots_args : TBase
  {
    private long _matchId;

    public long MatchId
    {
      get
      {
        return _matchId;
      }
      set
      {
        __isset.matchId = true;
        this._matchId = value;
      }
    }


    public Isset __isset;
    #if !SILVERLIGHT
    [Serializable]
    #endif
    public struct Isset {
      public bool matchId;
    }

    public snapshots_args() {
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
            if (field.Type == TType.I64) {
              MatchId = iprot.ReadI64();
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
      TStruct struc = new TStruct("snapshots_args");
      oprot.WriteStructBegin(struc);
      TField field = new TField();
      if (__isset.matchId) {
        field.Name = "matchId";
        field.Type = TType.I64;
        field.ID = 1;
        oprot.WriteFieldBegin(field);
        oprot.WriteI64(MatchId);
        oprot.WriteFieldEnd();
      }
      oprot.WriteFieldStop();
      oprot.WriteStructEnd();
    }

    public override string ToString() {
      StringBuilder __sb = new StringBuilder("snapshots_args(");
      bool __first = true;
      if (__isset.matchId) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("MatchId: ");
        __sb.Append(MatchId);
      }
      __sb.Append(")");
      return __sb.ToString();
    }

  }


  #if !SILVERLIGHT
  [Serializable]
  #endif
  public partial class snapshots_result : TBase
  {
    private List<Snapshot> _success;
    private InvalidArgumentException _exc;

    public List<Snapshot> Success
    {
      get
      {
        return _success;
      }
      set
      {
        __isset.success = true;
        this._success = value;
      }
    }

    public InvalidArgumentException Exc
    {
      get
      {
        return _exc;
      }
      set
      {
        __isset.exc = true;
        this._exc = value;
      }
    }


    public Isset __isset;
    #if !SILVERLIGHT
    [Serializable]
    #endif
    public struct Isset {
      public bool success;
      public bool exc;
    }

    public snapshots_result() {
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
          case 0:
            if (field.Type == TType.List) {
              {
                Success = new List<Snapshot>();
                TList _list4 = iprot.ReadListBegin();
                for( int _i5 = 0; _i5 < _list4.Count; ++_i5)
                {
                  Snapshot _elem6;
                  _elem6 = new Snapshot();
                  _elem6.Read(iprot);
                  Success.Add(_elem6);
                }
                iprot.ReadListEnd();
              }
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 1:
            if (field.Type == TType.Struct) {
              Exc = new InvalidArgumentException();
              Exc.Read(iprot);
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
      TStruct struc = new TStruct("snapshots_result");
      oprot.WriteStructBegin(struc);
      TField field = new TField();

      if (this.__isset.success) {
        if (Success != null) {
          field.Name = "Success";
          field.Type = TType.List;
          field.ID = 0;
          oprot.WriteFieldBegin(field);
          {
            oprot.WriteListBegin(new TList(TType.Struct, Success.Count));
            foreach (Snapshot _iter7 in Success)
            {
              _iter7.Write(oprot);
            }
            oprot.WriteListEnd();
          }
          oprot.WriteFieldEnd();
        }
      } else if (this.__isset.exc) {
        if (Exc != null) {
          field.Name = "Exc";
          field.Type = TType.Struct;
          field.ID = 1;
          oprot.WriteFieldBegin(field);
          Exc.Write(oprot);
          oprot.WriteFieldEnd();
        }
      }
      oprot.WriteFieldStop();
      oprot.WriteStructEnd();
    }

    public override string ToString() {
      StringBuilder __sb = new StringBuilder("snapshots_result(");
      bool __first = true;
      if (Success != null && __isset.success) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Success: ");
        __sb.Append(Success);
      }
      if (Exc != null && __isset.exc) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Exc: ");
        __sb.Append(Exc== null ? "<null>" : Exc.ToString());
      }
      __sb.Append(")");
      return __sb.ToString();
    }

  }

}
