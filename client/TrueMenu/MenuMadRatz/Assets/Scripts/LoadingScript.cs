using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using LitJson;
using System.Collections.Generic;
public class LoadingScript : MonoBehaviour {

	private float REPEAT_TIME = 0.5f;
	private int state = 1;
	private string url;
	public Text loadingMessage;
	private string id;

	public LoginManager loginScript;
	public CharacterScript characterScript;
	public CharacterCreationScript characterCreationScript;
	public ManageScriptsPanel manageScriptPanel;
	public SelectScriptPanel selectScriptPanel;
	public ListMatchPanel listMatchPanel;

	// Use this for initialization
	void OnEnable () {
		StartCoroutine( RepeatingFunction() );
	}

	IEnumerator RepeatingFunction ()
	{
		while(true)
		{
			switch(state)
			{
			case 0:
				loadingMessage.text = "Por favor, aguarde.";
				state = 1;
				break;
			case 1:
				loadingMessage.text = "Por favor, aguarde..";
				state = 2;
				break;
			case 2:
				loadingMessage.text = "Por favor, aguarde...";
				state = 0;
				break;
			}
			yield return new WaitForSeconds(REPEAT_TIME); 
		}
	}

	public void StartLoginCoroutine(string username, string password)
	{
		StartCoroutine (LoginCoroutine(username, password));
	}

	public void StartCreateCharacterCoroutine (string name, int image)
	{
		StartCoroutine(CreateCharacterCoroutine(name, image));
	}

	public void StartDeleteCharacterCoroutine (string characterId)
	{
		StartCoroutine(DeleteCharacterCoroutine(characterId));
	}

	public void StartLoadCharactersCoroutine ()
	{
		StartCoroutine(LoadCharactersCoroutine(this.id));
	}

	public void StartLoadScriptsCoroutine ()
	{
		StartCoroutine(LoadScriptsCoroutine(this.id));
	}

	public void StartLoadScriptsSimpleCoroutine ()
	{
		StartCoroutine(LoadScriptsSimpleCoroutine(this.id));
	}

	public void StartDeleteScriptsCoroutine (string playerId, string scriptId)
	{
		StartCoroutine(DeleteScriptsCoroutine(playerId, scriptId));
	}

	public void StartSetDefaultScriptsCoroutine (string playerId, string scriptId)
	{
		StartCoroutine(SetDefaultScriptsCoroutine(playerId, scriptId));
	}

	public void StartSetActiveScriptCoroutine (string playerId, string characterId, string scriptId)
	{
		StartCoroutine(SetActiveScriptCoroutine (playerId, characterId, scriptId));
	}

	public void StartViewHistoryCoroutine ()
	{
		StartCoroutine(ViewHistoryCoroutine(this.id));
	}

	public void StartStartMatchCoroutine (string character, string enemy)
	{
		StartCoroutine(StartMatchCoroutine(this.id, character, enemy));
	}

	private IEnumerator LoginCoroutine(string username, string password){
		this.url = GlobalVariables.api_url + "/login/";

		if (username != null && password != null) {
			WWWForm loginForm = new WWWForm ();
			loginForm.AddField ("username", username);
			loginForm.AddField ("password", password);
			
			WWW loginRequest = new WWW (url, loginForm);
			
			yield return loginRequest;
			
			if (loginRequest.text != null) {
				JsonData data = JsonMapper.ToObject (loginRequest.text);
				try {
					Debug.Log ("Logging as " + data["id"]);
					this.id = (string)data["id"];

					//Add to globals.
					GlobalVariables.instance.id = this.id;
					GlobalVariables.instance.username = username;
					GlobalVariables.instance.password = password;

					StartCoroutine(LoadCharactersCoroutine(this.id));
				} catch (KeyNotFoundException e1) {
					try{
						Debug.Log(e1.Message + " API Error: " + data["err"]);

						loginScript.setStatusColor(Color.red);
						loginScript.setStatusText("Erro: " + data["err"]);
						loginScript.gameObject.SetActive(true);
						this.gameObject.SetActive(false);
					} catch (KeyNotFoundException e2){
						Debug.Log(e2.Message + "Internet Connection?");
						
						loginScript.setStatusColor(Color.red);
						loginScript.setStatusText("Erro: problema na conexao");
						loginScript.gameObject.SetActive(true);
						this.gameObject.SetActive(false);
					}
				} finally {

				}
			} else {
				Debug.Log ("Login Error: " + loginRequest.error);
				
				loginScript.setStatusColor(Color.red);
				loginScript.setStatusText(loginRequest.error);
				loginScript.gameObject.SetActive(true);
				this.gameObject.SetActive(false);
			}
		} else {
			Debug.Log("Need both fields filled");
			
			loginScript.setStatusColor(Color.red);
			loginScript.setStatusText("Precisa preencher ambos os campos");
			loginScript.gameObject.SetActive(true);
			this.gameObject.SetActive(false);
		}
	}

	private IEnumerator LoadCharactersCoroutine(string id){
		this.url = GlobalVariables.api_url + "/player/" + id + "/character/list/";

		WWW characterListRequest = new WWW (url);
		
		yield return characterListRequest;

		if (characterListRequest.text != null) {
			string rawJson = characterListRequest.text;
			JsonData data = JsonMapper.ToObject (rawJson);

			if(JSONUtils.JsonDataContainsKey(data, "err"))
			{
				Debug.Log("Erro " + data["err"]);
			}
			else
			{
				Debug.Log (data.ToJson());
				CharacterList model = JsonMapper.ToObject<CharacterList>(rawJson);
				characterScript.gameObject.SetActive(true);
				characterScript.SetData(model);
				this.gameObject.SetActive(false);
			}
		}
	}

	private IEnumerator DeleteCharacterCoroutine(string characterId){
		this.url = GlobalVariables.api_url + "/player/" + this.id + "/character/" + characterId + "/delete";
		
		WWW deleteCharacterRequest = new WWW (url);
		
		yield return deleteCharacterRequest;
		
		if (deleteCharacterRequest.text != null) {
			string rawJson = deleteCharacterRequest.text;
			JsonData data = JsonMapper.ToObject (rawJson);
			
			if(JSONUtils.JsonDataContainsKey(data, "err"))
			{
				string err = "Erro " + data["err"] + ". Params: " + name;
				Debug.Log(err);
				characterScript.gameObject.SetActive(true);
				characterScript.SetStatus(err, Color.red);
				this.gameObject.SetActive(false);
			}
			else
			{
				Debug.Log (data.ToJson());
				StartCoroutine(LoadCharactersCoroutine(this.id));
			}
		}
	}

	private IEnumerator CreateCharacterCoroutine(string name, int image){
		this.url = GlobalVariables.api_url + "/player/" + this.id + "/character/create";
		
		WWWForm characterForm = new WWWForm ();
		characterForm.AddField ("name", name);
		characterForm.AddField ("image", image);
		
		WWW createCharacterRequest = new WWW (url, characterForm);
		
		yield return createCharacterRequest;
		
		if (createCharacterRequest.text != null) {
			string rawJson = createCharacterRequest.text;
			JsonData data = JsonMapper.ToObject (rawJson);
			
			if(JSONUtils.JsonDataContainsKey(data, "err"))
			{
				string err = "Erro " + data["err"] + ". Params: " + name;
				Debug.Log(err);
				characterCreationScript.gameObject.SetActive(true);
				characterCreationScript.SetStatus(err, Color.red);
				this.gameObject.SetActive(false);
			}
			else
			{
				Debug.Log (data.ToJson());
				StartCoroutine(LoadCharactersCoroutine(this.id));
			}
		}
	}

	private IEnumerator LoadScriptsCoroutine(string id){
		this.url = GlobalVariables.api_url + "/player/" + id + "/script/list/";
		
		WWW scriptListRequest = new WWW (url);
		
		yield return scriptListRequest;
		
		if (scriptListRequest.text != null) {
			string rawJson = scriptListRequest.text;
			JsonData data = JsonMapper.ToObject (rawJson);
			
			if(JSONUtils.JsonDataContainsKey(data, "err"))
			{
				Debug.Log("Erro " + data["err"]);
			}
			else
			{
				Debug.Log (data.ToJson());
				ScriptList list = JsonMapper.ToObject<ScriptList>(rawJson);
				manageScriptPanel.SetScriptList(list);
				manageScriptPanel.gameObject.SetActive(true);
				this.gameObject.SetActive(false);
			}
		}
	}

	private IEnumerator LoadScriptsSimpleCoroutine(string id){
		this.url = GlobalVariables.api_url + "/player/" + id + "/script/list/";
		
		WWW scriptListRequest = new WWW (url);
		
		yield return scriptListRequest;
		
		if (scriptListRequest.text != null) {
			string rawJson = scriptListRequest.text;
			JsonData data = JsonMapper.ToObject (rawJson);
			
			if(JSONUtils.JsonDataContainsKey(data, "err"))
			{
				Debug.Log("Erro " + data["err"]);
			}
			else
			{
				Debug.Log (data.ToJson());
				ScriptList list = JsonMapper.ToObject<ScriptList>(rawJson);
				selectScriptPanel.SetScriptList(list);
				selectScriptPanel.gameObject.SetActive(true);
				this.gameObject.SetActive(false);
			}
		}
	}

	private IEnumerator DeleteScriptsCoroutine (string playerId, string scriptId)
	{
		this.url = GlobalVariables.api_url + "/player/" + id + "/script/" + scriptId + "/delete";

		WWW scriptDeleteRequest = new WWW (url);
		
		yield return scriptDeleteRequest;
		
		if (scriptDeleteRequest.text != null) {
			string rawJson = scriptDeleteRequest.text;
			JsonData data = JsonMapper.ToObject (rawJson);
			
			if(JSONUtils.JsonDataContainsKey(data, "err"))
			{
				Debug.Log("Erro " + data["err"]);
			}
			else
			{
				Debug.Log (data.ToJson());
				StartCoroutine(LoadScriptsCoroutine(playerId));
			}
		}
	}

	private IEnumerator SetDefaultScriptsCoroutine (string playerId, string scriptId)
	{
		this.url = GlobalVariables.api_url + "/player/" + id + "/script/" + scriptId + "/setDefault";
		
		WWW scriptSetDefaultRequest = new WWW (url);
		
		yield return scriptSetDefaultRequest;
		
		if (scriptSetDefaultRequest.text != null) {
			string rawJson = scriptSetDefaultRequest.text;
			JsonData data = JsonMapper.ToObject (rawJson);
			
			if(JSONUtils.JsonDataContainsKey(data, "err"))
			{
				Debug.Log("Erro " + data["err"]);
			}
			else
			{
				Debug.Log (data.ToJson());
				StartCoroutine(LoadScriptsCoroutine(playerId));
			}
		}
	}

	private IEnumerator SetActiveScriptCoroutine (string playerId, string characterId, string scriptId)
	{
		this.url = GlobalVariables.api_url + "/player/" + playerId +"/character/" + characterId + "/script/" + scriptId + "/changeScript";
		
		WWW scriptSetActiveRequest = new WWW (url);
		
		yield return scriptSetActiveRequest;
		
		if (scriptSetActiveRequest.text != null) {
			string rawJson = scriptSetActiveRequest.text;
			JsonData data = JsonMapper.ToObject (rawJson);
			
			if(JSONUtils.JsonDataContainsKey(data, "err"))
			{
				Debug.Log("Erro " + data["err"]);
			}
			else
			{
				Debug.Log (data.ToJson());
				StartCoroutine(LoadScriptsSimpleCoroutine(playerId));
			}
		}
	}

	private IEnumerator ViewHistoryCoroutine(string id){
		this.url = GlobalVariables.api_url + "/player/" + id + "/match/list/";
		
		WWW viewHistoryRequest = new WWW (url);
		
		yield return viewHistoryRequest;
		
		if (viewHistoryRequest.text != null) {
			string rawJson = viewHistoryRequest.text;
			JsonData data = JsonMapper.ToObject (rawJson);
			
			if(JSONUtils.JsonDataContainsKey(data, "err"))
			{
				Debug.Log("Erro " + data["err"]);
			}
			else
			{
				Debug.Log (data.ToJson());
				MatchList list = JsonMapper.ToObject<MatchList>(rawJson);
				listMatchPanel.gameObject.SetActive(true);
				listMatchPanel.SetMatchList(list);
				this.gameObject.SetActive(false);
			}
		}
	}

	private IEnumerator StartMatchCoroutine(string id, string character, string enemy){
		this.url = GlobalVariables.api_url + "/player/" + id + "/match/create/onevsone";
		WWWForm startMatchForm = new WWWForm();
		startMatchForm.AddField("character", character);
		startMatchForm.AddField("enemy", enemy);

		WWW startMatchRequest = new WWW (url, startMatchForm);
		
		yield return startMatchRequest;
		
		if (startMatchRequest.text != null) {
			string rawJson = startMatchRequest.text;
			JsonData data = JsonMapper.ToObject (rawJson);
			
			if(JSONUtils.JsonDataContainsKey(data, "err"))
			{
				Debug.Log("Erro " + data["err"]);
			}
			else
			{
				Debug.Log (data.ToJson());
				StartCoroutine(ViewHistoryCoroutine(id));
			}
		}
	}
}
