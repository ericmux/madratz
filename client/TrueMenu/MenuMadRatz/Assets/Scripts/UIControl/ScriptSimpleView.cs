using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System;
using System.Text;

public class ScriptSimpleView : MonoBehaviour {
	public delegate void OnActivateDelegate(string playerId, string characterId, string scriptId);

	public Text title;
	public Text textArea;
	public Text defaultText;

	public Button activate;

	private ScriptModel scriptModel;

	private OnActivateDelegate activateFunction;

	public void SetScriptModel(ScriptModel sm)
	{
		this.scriptModel = sm;
		this.title.text = sm.title;
		byte[] data = Convert.FromBase64String(sm.code);
		string decodedString = Encoding.UTF8.GetString(data);
		this.textArea.text = decodedString;
		if(GlobalVariables.instance.characterModel.script == null)
		{
			if(sm.isDefault)
			{
				this.defaultText.text = "(Ativo)";
				activate.gameObject.SetActive(false);
			}
			else
			{
				this.defaultText.text = "";
				activate.gameObject.SetActive(true);
			}
		}
		else if(GlobalVariables.instance.characterModel.script.Equals(sm._id))
		{
			this.defaultText.text = "(Ativo)";
			activate.gameObject.SetActive(false);
		}
		else
		{
			this.defaultText.text = "";
			activate.gameObject.SetActive(true);
		}
	}

	public void SetActivateDelegate(OnActivateDelegate onActivate)
	{
		this.activateFunction = onActivate;
	}

	public void OnActivate()
	{
		GlobalVariables.instance.characterModel.script = scriptModel._id;
		this.activateFunction(scriptModel._owner, GlobalVariables.instance.characterModel._id, scriptModel._id);
	}
}
