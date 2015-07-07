using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using System.IO;

public class ListMatchPanel : MonoBehaviour {
	public MatchView matchViewPrefab;
	MatchView view;
	public MatchList matchList;
	private int listIndex;
	public LoadingScript loadingPanel;

	List<GameObject> views;

	public void Awake()
	{
		views = new List<GameObject>();
	}

	public void SetMatchList (MatchList matchList)
	{
		for(int i = 0; i < views.Count; i++)
		{
			Destroy(views[i].gameObject);
		}
		views.Clear();
		int counter = 0;
		foreach(MatchModel cm in matchList.list)
		{
			if(counter > (matchList.list.Count - 5))
			{
				MatchView view = Instantiate(matchViewPrefab) as MatchView;
				view.gameObject.transform.SetParent(gameObject.transform, false);
				view.SetMatchModel(counter, cm);
				views.Add(view.gameObject);
			}
			counter++;
		}
		RepositionView();
	}

	private void RepositionView()
	{
		int W = 230;
		int counter = W*views.Count;
		int hW = W / 2;
		
		int initial = -(counter / 2) + hW;
		for(int i = 0; i < views.Count; i++)
		{
			views[i].transform.localPosition = new Vector2(initial+i*W, -34);
		}
	}

	public void OnRefresh()
	{
		this.gameObject.SetActive(false);
		loadingPanel.gameObject.SetActive(true);
		loadingPanel.StartViewHistoryCoroutine();
	}
}
