using UnityEngine;
using System.Collections.Generic;
using System.Text;
using System.IO;
using UnityEngine.UI;

public class BattleDirectorController : MonoBehaviour {
	public GameObject playerPrefab;

	public enum PlayerNumber
	{
		PLAYER_NUMBER_1,
		PLAYER_NUMBER_2,
		PLAYER_NUMBER_3,
		PLAYER_NUMBER_4
	}

	private PlayerNumber playerNumber = PlayerNumber.PLAYER_NUMBER_1;

	private float loadingTime = 0;
	public Slider loadingSlider;
	public Text loadingPercentText;

	// Use this for initialization
	void Start () {
		GameObject loadingCanvas = GameObject.FindGameObjectWithTag ("LoadingCanvas");

		List<PlayerData> player1Data = createFromFile ("Assets/Files/player_circle");
		List<PlayerData> player2Data = createFromFile ("Assets/Files/player_elastic");

		loadingCanvas.SetActive (false);

		GameObject player1 = (GameObject) Instantiate (playerPrefab);
		PlayerScript player1Script = player1.GetComponent<PlayerScript> ();
		player1Script.loadData (player1Data);
		// player1.SetActive (false);

		GameObject player2 = (GameObject) Instantiate (playerPrefab);
		PlayerScript player2Script = player2.GetComponent<PlayerScript> ();
		player2Script.loadData (player2Data);
		// player2.SetActive (false);

		// Playing starts when ticks are counting
		TimerController.startTick ();

		switch (playerNumber) {
		case PlayerNumber.PLAYER_NUMBER_1:
			CameraController.player = player1;

			break;
		case PlayerNumber.PLAYER_NUMBER_2:
			CameraController.player = player2;

			break;
		}

	}
	
	// Update is called once per frame
	void Update () {
	
	}

	public void downloadFiles() {

	}

	public List<PlayerData> createFromFile(string fileName) {
		List<PlayerData> playerDataList = new List<PlayerData> ();

		Debug.Log ("Reading coordinates from " + fileName);
		try {
			StreamReader reader = new StreamReader (fileName, Encoding.Default);
			
			string line;

			int nLines = int.Parse(reader.ReadLine());
			int lineCounter = 0;
			using (reader) {
				do {
					line = reader.ReadLine();
					// Debug.Log ("Line read: " + line);

					PlayerData data;
					if (line != null) {
						string[] entries = line.Split(',');
						if (entries.Length > 0) {
							// Get data from line
							float x = float.Parse(entries[0]);
							float z = float.Parse(entries[1]);
							// Debug.Log ("x = " + x + ", z = " + z);

							// Setup a data unit
							data = new PlayerData();
							data.Position = new Vector3(x, 0.5f, z);

							// Add it to data list
							playerDataList.Add(data);
							// Debug.Log ("data.Position = " + data.Position.ToString());
						}
					}

					lineCounter ++;
	
					loadingTime = lineCounter / nLines;
					loadingSlider.value = loadingTime;
					loadingPercentText.text = ((int) loadingTime) + " %%";
					
				} while (line != null);
				
				reader.Close();
			}
		} catch (IOException e) {
			Debug.Log ("IOException: " + e.ToString());
		}

		return playerDataList;
	}
}
