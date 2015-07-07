using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System;
using System.Text;
using LitJson;

class VerifyResult
{
	public bool success { get; set; }
	public string errorType { get; set; }
	public string errorMsg { get; set; }
}

public class EditScriptPanel : MonoBehaviour {

	public delegate void OnExitDelegate();

	public InputField title;
	public InputField code;
	public Text status;
	public Text saveButton;
	public Button pasteButton;
	public ConfirmPanel confirmPanel;

	public ScriptModel scriptModel;

	private string savedScript;
	private OnExitDelegate exitFunction;

	public void SetScriptModel (ScriptModel scriptModel)
	{
		this.scriptModel = scriptModel;
		this.title.text = scriptModel.title;
		byte[] data = Convert.FromBase64String(scriptModel.code);
		savedScript = Encoding.UTF8.GetString(data);
		this.code.text = savedScript;
		if(scriptModel._id != null)
			SetStatus("Script carregado.", Color.black);
		else
			SetStatus("Criando script...", Color.black);
	}

	public void SetStatus(string status, Color color)
	{
		this.status.text = status;
		this.status.color = color;
	}

	public void SetSaveText(string text)
	{
		this.saveButton.text = text;
	}
	public void SetOnExit(OnExitDelegate onExit)
	{
		this.exitFunction = onExit;
	}

	public void Start()
	{
		pasteButton.gameObject.SetActive(!Application.isMobilePlatform);

		confirmPanel.SetTitle("Voltar");
		confirmPanel.SetStatus("Você tem mudanças pendentes! Deseja realmente voltar sem salvar essas mudanças?");
		confirmPanel.SetOnConfirm(OnExitConfirm);
	}
	
	public void OnVerify()
	{
		StartCoroutine(VerifyScriptCoroutine());
	}

	public void OnSave()
	{
		if(scriptModel._id == null)
			StartCoroutine(CreateScriptCoroutine());
		else
			StartCoroutine(SaveScriptCoroutine());
	}

	public void OnExit()
	{
		if (code.text != savedScript) {
			confirmPanel.gameObject.SetActive(true);
		} else {
			OnExitConfirm();
		}
	}

	public void OnExitConfirm()
	{
		exitFunction();
		this.gameObject.SetActive(false);
	}

	public void OnPasteClicked ()
	{
		code.text = ClipboardHelper.clipboard;
	}

	public void OnScriptChanged()
	{
		UpdateSaveText();
	}

	private void UpdateSaveText()
	{
		if (code.text == savedScript) {
			saveButton.text = "Salvar";
		} else {
			saveButton.text = "Salvar*";
		}
	}

	private IEnumerator VerifyScriptCoroutine(){
		string url = GlobalVariables.api_url + "/player/" + scriptModel._owner + "/script/verify";
		
		WWWForm scriptVerifyForm = new WWWForm();
		scriptVerifyForm.AddField("title", title.text);
		var plainTextBytes = System.Text.Encoding.UTF8.GetBytes(code.text);
		var encodedString = System.Convert.ToBase64String(plainTextBytes);
		scriptVerifyForm.AddField("code", encodedString);
		
		WWW scriptVerifyRequest = new WWW (url, scriptVerifyForm);
		SetStatus("Verificando...", Color.black);
		yield return scriptVerifyRequest;
		
		if (scriptVerifyRequest.text != null) {
			string rawJson = scriptVerifyRequest.text;
			JsonData data = JsonMapper.ToObject (rawJson);
			
			if(JSONUtils.JsonDataContainsKey(data, "err"))
			{
				string err = "Erro " + data["err"]; 
				Debug.Log(err);
				SetStatus(err, Color.red);
			}
			else
			{
				VerifyResult result = JsonMapper.ToObject<VerifyResult>(rawJson);
				if(result.success)
				{
					SetStatus("Verificado com sucesso!", Color.green);
				}
				else
				{
					SetStatus("Erro! Tipo: " + result.errorType + " Msg: " + result.errorMsg, Color.red);
				}
				Debug.Log (data.ToJson());
			}
		}
	}

	private IEnumerator SaveScriptCoroutine(){
		string url = GlobalVariables.api_url + "/player/" + scriptModel._owner + "/script/" + scriptModel._id + "/update";

		WWWForm scriptUpdateForm = new WWWForm();
		scriptUpdateForm.AddField("title", title.text);
		string script = code.text;
		var plainTextBytes = System.Text.Encoding.UTF8.GetBytes(script);
		var encodedString = System.Convert.ToBase64String(plainTextBytes);
		scriptUpdateForm.AddField("code", encodedString);

		WWW scriptUpdateRequest = new WWW (url, scriptUpdateForm);
		SetStatus("Salvando...", Color.black);
		yield return scriptUpdateRequest;
		
		if (scriptUpdateRequest.text != null) {
			string rawJson = scriptUpdateRequest.text;
			JsonData data = JsonMapper.ToObject (rawJson);
			
			if(JSONUtils.JsonDataContainsKey(data, "err"))
			{
				string err = "Erro " + data["err"]; 
				Debug.Log(err);
				SetStatus(err, Color.red);
			}
			else
			{
				Debug.Log (data.ToJson());
				SetStatus("Salvo!", Color.green);
				savedScript = script;
				UpdateSaveText();
			}
		}
	}

	private IEnumerator CreateScriptCoroutine(){
		string url = GlobalVariables.api_url + "/player/" + scriptModel._owner + "/script/create";
		
		WWWForm scriptCreateForm = new WWWForm();
		scriptCreateForm.AddField("title", title.text);
		var plainTextBytes = System.Text.Encoding.UTF8.GetBytes(code.text);
		var encodedString = System.Convert.ToBase64String(plainTextBytes);
		scriptCreateForm.AddField("code", encodedString);
		
		WWW scriptCreateRequest = new WWW (url, scriptCreateForm);
		SetStatus("Salvando...", Color.black);
		yield return scriptCreateRequest;
		
		if (scriptCreateRequest.text != null) {
			string rawJson = scriptCreateRequest.text;
			JsonData data = JsonMapper.ToObject (rawJson);
			
			if(JSONUtils.JsonDataContainsKey(data, "err"))
			{
				string err = "Erro " + data["err"]; 
				Debug.Log(err);
				SetStatus(err, Color.red);
			}
			else
			{
				Debug.Log (data.ToJson());
				OnExit();
			}
		}
	}
}
