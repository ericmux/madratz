using UnityEngine;
using System.Collections;
using System.Text;
using System.IO;

public class PlayerScript : MonoBehaviour {
	private static int MAX = 200;

	public string fileSource;
	private float[] xArray = new float[MAX];
	private float[] zArray = new float[MAX];

	// Use this for initialization
	void Start () {
		LoadFromSource (fileSource);
	}
	
	// Update is called once per frame
	void Update () {
		int tick = TimerController.tick;

		if (tick < xArray.Length && tick < zArray.Length) {
			transform.position = new Vector3 (xArray [tick], transform.position.y, zArray [tick]);
		} else {
			TimerController.tick = 0;
		}
	}

	private bool LoadFromSource(string fileSource) {
		// Debug.Log ("Reading coordinates from " + fileSource);
		try {
			StreamReader reader = new StreamReader (fileSource, Encoding.Default);

			string line;

			int counter = 0;
			using (reader) {
				do {
					line = reader.ReadLine();
					// Debug.Log ("Line read: " + line);

					if (line != null) {
						string[] entries = line.Split(',');
						if (entries.Length > 0) {
							float x = float.Parse(entries[0]);
							xArray[counter] = x;
							// Debug.Log ("x = " + x);

							float z = float.Parse(entries[1]);
							zArray[counter] = z;
							// Debug.Log ("z = " + z);
					
							counter ++;
						}
					}
					
				} while (line != null && counter < MAX);
				
				reader.Close();
			}
		} catch (IOException e) {
			Debug.Log ("IOException: " + e.ToString());
			return false;
		}

		return true;
	}
}
