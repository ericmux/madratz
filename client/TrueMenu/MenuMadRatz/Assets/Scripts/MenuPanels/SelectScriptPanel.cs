﻿using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System.Collections.Generic;

public class SelectScriptPanel : MonoBehaviour {
	private int listIndex;
	public ScriptSimpleView scriptSimpleViewPrefab;

	public ScriptList scriptList;
	ScriptSimpleView view;

	public Button nextButton;
	public Button previousButton;

	public LoadingScript loadingPanel;
	List<GameObject> views;

	public void SetScriptList (ScriptList scriptList)
	{
		this.scriptList = scriptList;
		this.listIndex = 0;
		view = Instantiate(scriptSimpleViewPrefab) as ScriptSimpleView;

		view.gameObject.transform.SetParent(gameObject.transform, false);
		view.SetActivateDelegate(OnActivate);

		if(scriptList.list == null)
			scriptList.list = new List<ScriptModel>();
		if(scriptList.list.Count > 0)
		{
			if(scriptList.list.Count > 1)
			{
				nextButton.gameObject.SetActive(true);
				previousButton.gameObject.SetActive(true);

				string activeScriptId = GlobalVariables.instance.characterModel.script;
				listIndex = scriptList.list.FindIndex(s => Object.Equals(s._id, activeScriptId));
				if (listIndex < 0) listIndex = scriptList.list.FindIndex(s => s.isDefault);
				if (listIndex < 0) listIndex = 0;
			}
			else
			{
				nextButton.gameObject.SetActive(false);
				previousButton.gameObject.SetActive(false);
			}
			SetScript();
		}
	}

	private void SetScript()
	{
		Debug.Log (scriptList.list[listIndex].code);
		
		string scriptIndex = string.Format("({0}/{1}) ", listIndex + 1, scriptList.list.Count);
		view.SetScriptModel(scriptList.list[listIndex], scriptIndex);
	}
	
	public void OnNextClick()
	{
		listIndex = (listIndex + 1) % scriptList.list.Count;
		SetScript();
	}
	
	public void OnPreviousClick()
	{
		listIndex = (listIndex - 1) % scriptList.list.Count;
		listIndex = listIndex < 0 ? listIndex + scriptList.list.Count: listIndex;
		SetScript();
	}

	public void OnActivate(string playerId, string characterId, string scriptId)
	{
		this.gameObject.SetActive(false);
		loadingPanel.gameObject.SetActive(true);
		loadingPanel.StartSetActiveScriptCoroutine(playerId, characterId, scriptId);
	}
}
