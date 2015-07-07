using UnityEngine;
using System.Collections.Generic;

public class TimerController : MonoBehaviour {

	private static readonly float[] SPEEDS = {-5, -4, -3, -2, -1, -0.5f, 0.5f, 1, 2, 3, 4, 5};
	private const int DEFAULT_SPEED_IDX = 7;

	public static int tick;

	private static List<double> times = null;
	private static bool startedTicking = false;

	private static int speedIdx = DEFAULT_SPEED_IDX;
	private static SpeedableStopwatch stopwatch = new SpeedableStopwatch ();

	// Use this for initialization
	void Start () {
		tick = 0;
		SpeedIdx = DEFAULT_SPEED_IDX;
		stopwatch.Reset();
	}
	
	// Update is called once per frame
	void Update () {
		if (startedTicking && stopwatch.Running) {
			float now = stopwatch.CurrentTime;

			if (Speed > 0) {
				while (tick < times.Count && times[tick] < now) {
					tick++;
				}
			} else if (Speed < 0) {
				while (tick >= 0 && times[tick] > now) {
					tick--;
				}
			}
			if (HasFinished()) stopwatch.Stop();
		}
	}

	public static bool HasFinished()
	{
		return tick == times.Count || tick == -1;
	}

	public static void StartTicking (List<double> atimes) {
		startedTicking = true;
		times = atimes;
		stopwatch.Start();
	}

	public static void togglePlaying() {
		stopwatch.Running = !stopwatch.Running;
	}

	public static void restart() {
		Progress = Speed > 0 ? 0 : 1;
		stopwatch.Start();
	}

	public static void increaseSpeed(int amount) {
		SpeedIdx = Mathf.Min(SpeedIdx + amount, SPEEDS.Length - 1);
	}

	public static void decreaseSpeed(int amount) {
		SpeedIdx = Mathf.Max(SpeedIdx - amount, 0);
	}

	private static int SpeedIdx {
		get {
			return speedIdx;
		}
		set {		
			speedIdx = value;
			stopwatch.Speed = SPEEDS[speedIdx];
		}
	}

	public static bool Playing {
		get {
			return stopwatch.Running;
		}
	}

	public static float Speed {
		get {
			return stopwatch.Speed;
		}
	}

	public static float Progress {
		get {
			return tick / (float) times.Count;
		}
		set {
			if (value < 0 || value > 1) return;

			tick = (int) (value * (times.Count - 1));
			stopwatch.CurrentTime = (float) times[tick];
		}
	}
}
