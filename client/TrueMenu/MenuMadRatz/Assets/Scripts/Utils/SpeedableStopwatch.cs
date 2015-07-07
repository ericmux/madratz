using UnityEngine;
using System;

public class SpeedableStopwatch
{
	private float consolidatedTime = 0.0f;

	private float startTime;
	private bool running = false;

	private float speed = 1;

	public SpeedableStopwatch () { }

	public void Start()
	{
		if (!running) {
			running = true;
			startTime = Now();
		}
	}

	public void Stop()
	{
		if (running) {
			consolidatedTime += (Now() - startTime) * speed;
			running = false;
		}
	}

	public void Reset()
	{
		running = false;
		consolidatedTime = 0.0f;
	}

	public bool Running
	{
		get
		{
			return running;
		}
		set
		{
			if (value == running) return;

			if (running) Stop();
			else Start();

		}
	}

	public float CurrentTime
	{
		get
		{
			if (!running) return consolidatedTime;
			return consolidatedTime + (Now() - startTime) * speed;
		}
		set
		{
			consolidatedTime = value;
			if (running) startTime = Now();
		}
	}
	
	public float Speed
	{
		get {
			return speed;
		}
		set {
			CurrentTime = CurrentTime; // this will update the consolidatedTime
			speed = value;
		}
	}

	private float Now()
	{
		return Time.realtimeSinceStartup;
	}
}

