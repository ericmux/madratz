using UnityEngine;
using System.Collections.Generic;

public class TimerController : MonoBehaviour {
	public static int tick;

	private static float startTime;
	private static List<double> times = null;
	private static bool startedTick = false;

	// Use this for initialization
	void Start () {
		tick = 0;
	}
	
	// Update is called once per frame
	void Update () {
		if (startedTick) {
			float currentTime = Time.fixedTime - startTime;
			while(tick < times.Count && times[tick] < currentTime) {
				tick++;
			}
			// Debug.Log (tick);
		}
	}

	public static void startTick (List<double> times) {
		TimerController.startedTick = true;
		TimerController.startTime = Time.fixedTime;
		TimerController.times = times;
	}
}
