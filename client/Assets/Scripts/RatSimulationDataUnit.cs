using UnityEngine;
using System.Collections;

public class RatSimulationDataUnit {

	// Position
		
		public Vector3 position;
	
	// Rotation
	
	public Quaternion rotation;
	
	// Stats
	
	public float HP;
	public float SP;
	
	public Vector3 Position {
		get {
			return position;
		}
		set {
			position = value;
		}
	}
	
	public Quaternion Rotation {
		get {
			return rotation;
		}
		set {
			rotation = value;
		}
	}
}
