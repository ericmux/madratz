using UnityEngine;
using System.Collections.Generic;

public class RatSimulationScript : MonoBehaviour {
	private List<RatSimulationDataUnit> mDataList = null;
	
	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		int tick = TimerController.tick;
		
		if (mDataList != null) {
			Vector3 position = mDataList[tick % mDataList.Count].Position;
			transform.position = position;
			// Debug.Log("Position = " + position.ToString());
		}
	}
	
	public void loadData(List<RatSimulationDataUnit> dataList) {
		mDataList = dataList;
	}
}
