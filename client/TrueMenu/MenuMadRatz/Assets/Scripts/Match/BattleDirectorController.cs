using UnityEngine;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.IO;
using UnityEngine.UI;

public class BattleDirectorController : MonoBehaviour {
	public static string matchId = "559b5ac7098207ca7afd6bd0";
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
	Dictionary<string, GameObject> ratObjects;
	private List<GameObject> spellObjects = new List<GameObject>();
	private List<RatSimulationDataUnit> player1SimList;
	GameObject player1Object;

	/*private float loadingTime = 0;
	public Slider loadingSlider;
	public Text loadingPercentText;*/

	public Slider playBackSlider;
	public Text playBackButtonText;
	public Text speedText;
	public Slider playerLifeBar;

	private bool endOfSimulation = false;

	// Use this for initialization
	void Start () {
		snapshots = ThriftClient.getSnapshotsFromFile (GlobalVariables.replay_dir + matchId + ".out");

		ratObjects = new Dictionary<string, GameObject> ();

		var ratSimData = GetAllRatSimDataFromSnapshots (snapshots);

		foreach (var rat in ratSimData) {
			GameObject ratObject = (GameObject) Instantiate (ratPrefab);
			ratObjects.Add(rat.Key, ratObject);
			RatSimulationScript ratScript = ratObject.GetComponent<RatSimulationScript> ();
			ratScript.loadData (rat.Value);
		}

		player1SimList = ratSimData.Values.ToArray()[0];
		player1Object = ratObjects.Values.ToArray()[0];

		CameraController.player = player1Object;

		// Playing starts when ticks are counting
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
	

		if (tick >= totalTicks) {
			endOfSimulation = true;
			TimerController.pause();
			playBackButtonText.text = "Replay";
			return;
		}

		float playBackProgress = ((float) tick) / totalTicks;
		playBackSlider.value = playBackProgress;

		if (tick < player1SimList.Count) {
			double player1HP = player1SimList.ElementAt(tick).HP / 100;
			playerLifeBar.value = (float) player1HP;
		}
	
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
		spellObject.transform.position = new Vector3((float) spell.Position.X / 6, 2.0f, (float) spell.Position.Y / 6);
	}
	
	public Dictionary<string, List<RatSimulationDataUnit>> GetAllRatSimDataFromSnapshots(List<Snapshot> snapshots) {
		Dictionary<string, List<RatSimulationDataUnit>> ratSimData = new Dictionary<string, List<RatSimulationDataUnit>>();

		foreach (Snapshot s in snapshots) {
			foreach (Actor a in s.Actors) {
				if (!a.__isset.id) continue;
				if (!ratSimData.ContainsKey(a.Id)) ratSimData[a.Id] = new List<RatSimulationDataUnit>();
				
				RatSimulationDataUnit dataUnit = new RatSimulationDataUnit();
				dataUnit.Position = new Vector3((float) a.Position.X / 6, 2, (float) a.Position.Y / 6);
				dataUnit.Angle = a.Angle;
				dataUnit.HP = a.Hp;
				dataUnit.width = (float) a.Width / 6;
				
				ratSimData[a.Id].Add(dataUnit);
			}
		}

		foreach (var entry in ratSimData) {
			print (entry.Key);
		}

		return ratSimData;
	}

	public List<RatSimulationDataUnit> createFromFile(string fileName) {
		List<RatSimulationDataUnit> ratDataList = new List<RatSimulationDataUnit> ();

		try {
			StreamReader reader = new StreamReader (fileName, Encoding.Default);
			
			string line;

			int nLines = int.Parse(reader.ReadLine());
			int lineCounter = 0;
			using (reader) {
				do {
					line = reader.ReadLine();

					RatSimulationDataUnit data;
					if (line != null) {


						string[] entries = line.Split(',');
						if (entries.Length > 0) {
							// Get data from line
							float x = float.Parse(entries[0]);
							float z = float.Parse(entries[1]);

							// Setup a data unit
							data = new RatSimulationDataUnit();
							data.Position = new Vector3(x, 0.5f, z);

							// Add it to data list
							ratDataList.Add(data);
						}
					}

					lineCounter ++;
			
				} while (line != null);
				
				reader.Close();
			}
		} catch (IOException e) {
			Debug.Log ("IOException: " + e.ToString());
		}

		return ratDataList;
	}

	public void onPlayPauseButtonClick() {
		if (endOfSimulation) {
			endOfSimulation = false;
			playBackButtonText.text = "Play";
			TimerController.restart();
			return;
		}

		TimerController.togglePlaying ();

		if (TimerController.Playing) {
			playBackButtonText.text = "Pause";
		} else {
			playBackButtonText.text = "Play";
		}
	}

	public void onRewindButtonClick() {
		TimerController.decreaseSpeed(1);

		speedText.text = TimerController.speed + ".0 X";
	}

	public void onForwardButtonClick() {
		TimerController.increaseSpeed(1);

		speedText.text = TimerController.speed + ".0 X";
	}

	public void onExitButtonClicked()
	{
		Application.LoadLevel(0);
	}
}
