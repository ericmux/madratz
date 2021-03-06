﻿using UnityEngine;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.IO;
using UnityEngine.UI;

public class BattleDirectorController : MonoBehaviour {
	public GameObject ratPrefab;
	public GameObject shotPrefab;

	public enum PlayerNumber
	{
		PLAYER_NUMBER_1,
		PLAYER_NUMBER_2,
		PLAYER_NUMBER_3,
		PLAYER_NUMBER_4
	}

	private PlayerNumber playerNumber = PlayerNumber.PLAYER_NUMBER_1;

	private List<Snapshot> snapshots;
	private List<GameObject> spellObjects = new List<GameObject>();

	/*private float loadingTime = 0;
	public Slider loadingSlider;
	public Text loadingPercentText;*/

	public Slider playBackSlider;

	// Use this for initialization
	void Start () {
		// GameObject loadingCanvas = GameObject.FindGameObjectWithTag ("LoadingCanvas");
		// loadingCanvas.SetActive (false);

		snapshots = ThriftClient.getSnapshotsFromFile ("Assets/Files/match.out");

		var ratSimData = GetAllRatSimDataFromSnapshots (snapshots);

		foreach (var rat in ratSimData) {
			GameObject rat1 = (GameObject) Instantiate (ratPrefab);
			RatSimulationScript rat1Script = rat1.GetComponent<RatSimulationScript> ();
			rat1Script.loadData (rat.Value);
			// rat1.SetActive (false);
		}

		// Playing starts when ticks are countin1g
		TimerController.startTick (snapshots.Select(s => s.ElapsedTime).ToList());

		/*
		switch (playerNumber) {
		case PlayerNumber.PLAYER_NUMBER_1:
			CameraController.player = rat1;

			break;
		case PlayerNumber.PLAYER_NUMBER_2:
			CameraController.player = rat2;

			break;
		}*/
	}

	// Update is called once per frame
	void Update () {
		int tick = TimerController.tick;
		int totalTicks = snapshots.Count;

		if (tick >= totalTicks) return;

		float playBackProgress = ((float) tick) / totalTicks;
		// playBackSlider.value = playBackProgress;
	
		Snapshot snapshot = snapshots[tick];

		int i = 0;
		foreach (Actor spell in snapshot.Actors.Where(a => !a.__isset.id)) {
			GameObject spellObject;
			if (i < spellObjects.Count) {
				spellObject = spellObjects[i];
			} else {
				spellObject = Instantiate(shotPrefab);
				spellObjects.Add(spellObject);
			}
			UpdateSpellObject(spellObject, spell);
			i++;
		}
		while (i < spellObjects.Count) {
			spellObjects[i++].transform.localScale = new Vector3();
		}
	}

	private void UpdateSpellObject(GameObject spellObject, Actor spell) {
		spellObject.transform.localScale = new Vector3(0.25f, 0.25f, 0.25f);
		spellObject.transform.position = new Vector3((float) spell.Position.X / 6, 1, (float) spell.Position.Y / 6);
	}
	
	public Dictionary<long, List<RatSimulationDataUnit>> GetAllRatSimDataFromSnapshots(List<Snapshot> snapshots) {
		Dictionary<long, List<RatSimulationDataUnit>> ratSimData = new Dictionary<long, List<RatSimulationDataUnit>>();

		foreach (Snapshot s in snapshots) {
			foreach (Actor a in s.Actors) {
				if (!a.__isset.id) continue;
				if (!ratSimData.ContainsKey(a.Id)) ratSimData[a.Id] = new List<RatSimulationDataUnit>();
				
				RatSimulationDataUnit dataUnit = new RatSimulationDataUnit();
				dataUnit.Position = new Vector3((float) a.Position.X / 6, 1, (float) a.Position.Y / 6);
				dataUnit.Angle = a.Angle;
				dataUnit.HP = a.Hp;
				dataUnit.width = (float) a.Width / 6;
				// Debug.Log (dataUnit.position.ToString());
				
				ratSimData[a.Id].Add(dataUnit);
			}
		}

		return ratSimData;
	}

	public List<RatSimulationDataUnit> createFromFile(string fileName) {
		List<RatSimulationDataUnit> ratDataList = new List<RatSimulationDataUnit> ();

		// Debug.Log ("Reading coordinates from " + fileName);
		try {
			StreamReader reader = new StreamReader (fileName, Encoding.Default);
			
			string line;

			int nLines = int.Parse(reader.ReadLine());
			int lineCounter = 0;
			using (reader) {
				do {
					line = reader.ReadLine(); 	
					// Thread.Sleep(100);
					// Debug.Log ("Line read: " + line);

					RatSimulationDataUnit data;
					if (line != null) {


						string[] entries = line.Split(',');
						if (entries.Length > 0) {
							// Get data from line
							float x = float.Parse(entries[0]);
							float z = float.Parse(entries[1]);
							// Debug.Log ("x = " + x + ", z = " + z);

							// Setup a data unit
							data = new RatSimulationDataUnit();
							data.Position = new Vector3(x, 0.5f, z);

							// Add it to data list
							ratDataList.Add(data);
							// Debug.Log ("data.Position = " + data.Position.ToString());
						}
					}

					lineCounter ++;
	
					/*loadingTime = ((float) lineCounter) / nLines;
					loadingSlider.value = loadingTime;
					string loadingPercentString = ((int) (loadingTime * 100)) + " %";
					Debug.Log (loadingPercentString);
					loadingPercentText.text = loadingPercentString;*/
			
				} while (line != null);
				
				reader.Close();
			}
		} catch (IOException e) {
			Debug.Log ("IOException: " + e.ToString());
		}

		return ratDataList;
	}
}
