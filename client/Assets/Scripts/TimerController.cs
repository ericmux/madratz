using UnityEngine;
using System.Collections;

public class TimerController : MonoBehaviour {
	public static int tick;

	private static bool startedTick = false;

	// Use this for initialization
	void Start () {
		tick = 0;
	}
	
	// Update is called once per frame
	void Update () {
		if (startedTick) tick = tick + 1;
		// Debug.Log (tick);
	}

	public static void startTick () {
		TimerController.startedTick = true;
	}
}
