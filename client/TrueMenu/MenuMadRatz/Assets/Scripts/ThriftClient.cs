﻿using UnityEngine;
using System.Collections.Generic;
using Thrift;
using Thrift.Protocol;
using Thrift.Server;
using Thrift.Transport;
using System.IO;
using System.Text;

public class ThriftClient {

	public static List<Snapshot> getSnapshotsFromFile(string fileName) {
		try {
			byte[] buffer = File.ReadAllBytes(fileName);
		
			TMemoryBuffer trans = new TMemoryBuffer(buffer);    //Transport
			TFramedTransport framed = new TFramedTransport(trans);
			TProtocol proto = new TCompactProtocol(framed);		//Protocol
			
			// List<Snapshot> snapshots = new List<Snapshot>();
			SnapshotsResult result = new SnapshotsResult();
			result.Read (proto);
			List<Snapshot> snapshots = result.SnapshotList;
			/*while (true) {
				try {
					Snapshot snapshot = new Snapshot();
					snapshot.Read(proto);
					snapshots.Add(snapshot);
				} catch (TTransportException e) {
					if (e.Type == TTransportException.ExceptionType.EndOfFile) break;
					else throw e;
				}
			}*/
			Debug.Log("Read " + snapshots.Count + " snapshots");
			Debug.Log("Read " + snapshots.FindLast((a) => true).Actors.FindLast((a) => true));
			return snapshots;
			
		} catch (IOException e) {
			Debug.Log("IOExeption");
		}

		return null;
	}
}