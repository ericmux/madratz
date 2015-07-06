using UnityEngine;
using System.Collections.Generic;
using System.Linq;

public class RatSimulationScript : MonoBehaviour {
	private List<RatSimulationDataUnit> mDataList = null;

	private static Color GREEN = new Color(0, 1, 0);
	private static Color RED = new Color(1, 0, 0);

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		int tick = TimerController.tick;

		if (mDataList != null) {
			bool finished = (tick >= mDataList.Count);
			if (!finished) {
				RatSimulationDataUnit d = mDataList[tick];
				transform.localScale = new Vector3(d.width, 1, d.width);
				transform.position = d.Position;
				transform.eulerAngles = new Vector3(0, - (float) d.Angle / Mathf.PI * 180, 0);
				Material material = GetComponent<Renderer>().material;
				material.color = Color.Lerp (GREEN, RED, ((float)(100 - d.HP))/100);
			} else {
				transform.localScale = new Vector3(0, 0, 0);
			}
		}
	}
	
	public void loadData(List<RatSimulationDataUnit> dataList) {
		mDataList = dataList;
	}
}
