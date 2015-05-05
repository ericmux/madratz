using UnityEngine;
using System.Collections;

public class TimerController : MonoBehaviour {
	public static int tick;

	// Use this for initialization
	void Start () {
		tick = 0;
	}
	
	// Update is called once per frame
	void Update () {
		tick = tick + 1;
		Debug.Log (tick);
	}
}
