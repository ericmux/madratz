﻿using UnityEngine;
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

	// Use this for initialization
	void Start () {
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

	public void StartCreateCharacterCoroutine (string name)
	{
		StartCoroutine(CreateCharacterCoroutine(name));
	}

	public void StartDeleteCharacterCoroutine (string characterId)
	{
		StartCoroutine(DeleteCharacterCoroutine(characterId));
	}

	public void StartLoadCharactersCoroutine ()
	{
		StartCoroutine(LoadCharactersCoroutine(this.id));
	}

	private IEnumerator LoginCoroutine(string username, string password){
		this.url = "localhost:8080/api/login/";

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
		this.url = "localhost:8080/api/player/" + id + "/character/list/";

		WWW characterListRequest = new WWW (url);
		
		yield return characterListRequest;

		if (characterListRequest.text != null) {
			string rawJson = characterListRequest.text;
			JsonData data = JsonMapper.ToObject (rawJson);

			if(JsonDataContainsKey(data, "err"))
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
		this.url = "localhost:8080/api/player/" + this.id + "/character/" + characterId + "/delete";
		
		WWW deleteCharacterRequest = new WWW (url);
		
		yield return deleteCharacterRequest;
		
		if (deleteCharacterRequest.text != null) {
			string rawJson = deleteCharacterRequest.text;
			JsonData data = JsonMapper.ToObject (rawJson);
			
			if(JsonDataContainsKey(data, "err"))
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

	private IEnumerator CreateCharacterCoroutine(string name){
		this.url = "localhost:8080/api/player/" + this.id + "/character/create";
		
		WWWForm characterForm = new WWWForm ();
		characterForm.AddField ("name", name);
		
		WWW createCharacterRequest = new WWW (url, characterForm);
		
		yield return createCharacterRequest;
		
		if (createCharacterRequest.text != null) {
			string rawJson = createCharacterRequest.text;
			JsonData data = JsonMapper.ToObject (rawJson);
			
			if(JsonDataContainsKey(data, "err"))
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

	static public bool JsonDataContainsKey(JsonData data, string key)
	{
		bool result = false;
		if(data == null)
			return result;
		if(!data.IsObject)
		{
			return result;
		}
		IDictionary tdictionary = data as IDictionary;
		if(tdictionary == null)
			return result;
		if(tdictionary.Contains(key))
		{
			result = true;
		}
		return result;
	}
}