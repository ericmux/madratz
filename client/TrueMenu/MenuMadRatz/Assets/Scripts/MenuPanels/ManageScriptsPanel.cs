using UnityEngine;
using System.Collections;
using System;
using System.Text;
using System.Collections.Generic;
using UnityEngine.UI;

public class ManageScriptsPanel : MonoBehaviour {
	private int listIndex;
	public ScriptView scriptViewPrefab;

	public ScriptList scriptList;
	ScriptView view;

	public Button nextButton;
	public Button previousButton;
	public Button createButton;
	public Button voltarButton;

	public EditScriptPanel editScriptPanel;
	public LoadingScript loadingPanel;

	public void SetScriptList (ScriptList scriptList)
	{
		this.scriptList = scriptList;
		this.listIndex = 0;
		view = Instantiate(scriptViewPrefab) as ScriptView;
		view.transform.position = new Vector3(view.transform.position.x, 55, view.transform.position.z);
		view.SetReferences(editScriptPanel, this);
		view.SetOnReloadDelegate(OnReload);
		view.SetOnDeleteDelegate(OnDelete);
		view.SetOnSetDefaultDelegate(OnSetDefault);
		view.gameObject.transform.SetParent(gameObject.transform, false);
		if(scriptList.list == null)
			scriptList.list = new List<ScriptModel>();
		if(scriptList.list.Count > 0)
		{
			if(scriptList.list.Count > 1)
			{
				nextButton.gameObject.SetActive(true);
				previousButton.gameObject.SetActive(true);
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

		view.SetScriptModel(scriptList.list[listIndex]);
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

	public void OnCreate()
	{
		editScriptPanel.gameObject.SetActive(true);
		editScriptPanel.SetOnExit(OnReload);
		ScriptModel sm = new ScriptModel();
		sm._owner = scriptList._owner;
		sm.title = "";
		sm.code = "";
		editScriptPanel.SetScriptModel(sm);
		editScriptPanel.SetSaveText("Criar");
	}

	public void OnReload()
	{
		this.gameObject.SetActive(false);
		loadingPanel.gameObject.SetActive(true);
		loadingPanel.StartLoadScriptsCoroutine();
	}

	public void OnDelete(string playerId, string scriptId)
	{
		this.gameObject.SetActive(false);
		loadingPanel.gameObject.SetActive(true);
		loadingPanel.StartDeleteScriptsCoroutine(playerId, scriptId);
	}

	public void OnSetDefault(string playerId, string scriptId)
	{
		this.gameObject.SetActive(false);
		loadingPanel.gameObject.SetActive(true);
		loadingPanel.StartSetDefaultScriptsCoroutine(playerId, scriptId);
	}

	public void ToggleButtons(bool value)
	{
		createButton.interactable = value;
		voltarButton.interactable = value;
	}
}
