using UnityEngine;
using System.Collections.Generic;

public class TimerController : MonoBehaviour {
	public static int tick;
	public static bool playing;
	public static int speed;
	public static int MAX_SPEED = 5;
	public static int MIN_SPEED = -5;

	private static float startTime;
	private static List<double> times = null;
	private static bool startedTick = false;

	// Use this for initialization
	void Start () {
		tick = 0;
		playing = true;
		speed = 1;
	}
	
	// Update is called once per frame
	void Update () {
		if (startedTick) {
			/*float currentTime = Time.fixedTime - startTime;
			while(tick >= 0 && tick < times.Count && times[tick] < currentTime && playing) {
				tick += 1;
			}*/
			if (tick >= 0 && tick < times.Count && playing) {
				tick += speed;

				if (tick >= times.Count) tick = times.Count;
				if (tick < 0) tick = 0;
			}
		}
	}

	public static void startTick (List<double> times) {
		TimerController.startedTick = true;
		TimerController.startTime = Time.fixedTime;
		TimerController.times = times;
	}

	public static void togglePlaying() {
		playing = !playing;
	}

	public static void pause() {
		playing = false;
	}

	public static void restart() {
		tick = 0;
		// TimerController.startTime = Time.fixedTime;
		speed = 1;

		playing = true;
	}

	public static void increaseSpeed(int amount) {
		speed += amount ;
		if (speed > MAX_SPEED) {
			speed = MAX_SPEED;
		}
	}

	public static void decreaseSpeed(int amount) {
		speed -= amount ;
		if (speed < MIN_SPEED) {
			speed = MIN_SPEED;
		}
	}

	public static bool Playing {
		get {
			return playing;
		}
	}
}
