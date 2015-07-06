using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System;
using System.Text;

public class ScriptView : MonoBehaviour {
	public delegate void OnReloadDelegate();
	public delegate void OnDeleteDelegate(string playerId, string scriptId);
	public delegate void OnSetDefaultDelegate(string playerId, string scriptId);

	public Text title;
	public Text textArea;
	public Button deleteButton;
	public Button setDefault;
	public Text defaultText;
	public EditScriptPanel editScriptPanel;
	public ManageScriptsPanel manageScriptPanel;
	private ScriptModel scriptModel;
	private OnReloadDelegate reloadFunction;
	private OnDeleteDelegate deleteFunction;
	private OnSetDefaultDelegate setDefaultFunction;
	
	public void SetScriptModel(ScriptModel sm)
	{
		this.scriptModel = sm;
		this.title.text = sm.title;
		byte[] data = Convert.FromBase64String(sm.code);
		string decodedString = Encoding.UTF8.GetString(data);
		this.textArea.text = decodedString;
		if(sm.isDefault)
			this.defaultText.text = "(Principal)";
		else
			this.defaultText.text = "";
		deleteButton.gameObject.SetActive(!sm.isDefault);
		setDefault.gameObject.SetActive(!sm.isDefault);
	}

	public void SetReferences(EditScriptPanel editScriptPanel, ManageScriptsPanel manageScriptPanel)
	{
		this.editScriptPanel = editScriptPanel;
		this.manageScriptPanel = manageScriptPanel;
	}

	public void SetOnReloadDelegate(OnReloadDelegate onReload)
	{
		this.reloadFunction = onReload;
	}

	public void SetOnDeleteDelegate(OnDeleteDelegate onDelete)
	{
		this.deleteFunction = onDelete;
	}

	public void SetOnSetDefaultDelegate(OnSetDefaultDelegate onSetDefault)
	{
		this.setDefaultFunction = onSetDefault;
	}

	public void OnExit()
	{
		reloadFunction();
		this.manageScriptPanel.ToggleButtons (true);
	}
	public void OnEdit()
	{
		editScriptPanel.gameObject.SetActive(true);
		editScriptPanel.SetOnExit(OnExit);
		editScriptPanel.SetScriptModel(scriptModel);
		editScriptPanel.SetSaveText("Salvar");
		this.manageScriptPanel.ToggleButtons (false);
	}

	public void OnDelete()
	{
		deleteFunction(scriptModel._owner, scriptModel._id);
	}

	public void OnSetDefault()
	{
		setDefaultFunction(scriptModel._owner, scriptModel._id);
	}
}
