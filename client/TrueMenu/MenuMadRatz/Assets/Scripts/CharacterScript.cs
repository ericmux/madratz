using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System.Collections.Generic;

public class CharacterScript : MonoBehaviour {
	public CharacterViewScript characterViewPrefab;
	public EmptyViewScript emptyViewPrefab;

	List<GameObject> views;

	public void Awake()
	{
		views = new List<GameObject>();
	}

	public void SetData (CharacterList model)
	{
		for(int i = 0; i < views.Count; i++)
		{
			Destroy(views[i].gameObject);
		}
		views.Clear();

		foreach(CharacterModel cm in model.list)
		{
			CharacterViewScript view = Instantiate(characterViewPrefab, gameObject.transform.position, Quaternion.identity) as CharacterViewScript;
			view.SetName(cm.name);
			view.SetLevel(cm.level);
			view.gameObject.transform.SetParent(gameObject.transform);
			views.Add(view.gameObject);
		}

		if(views.Count < 3)
		{
			EmptyViewScript view = Instantiate(emptyViewPrefab, gameObject.transform.position, Quaternion.identity) as EmptyViewScript;
			view.gameObject.transform.SetParent(gameObject.transform);
			views.Add(view.gameObject);
		}
		RepositionView();
	}

	private void RepositionView()
	{
		int W = 200;
		int counter = W*views.Count;
		int hW = W / 2;

		int initial = -(counter / 2) + hW;
		for(int i = 0; i < views.Count; i++)
		{
			views[i].transform.localPosition = new Vector2(initial+i*W, -100);
		}
	}
}
