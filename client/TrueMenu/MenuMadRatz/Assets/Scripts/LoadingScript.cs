using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using LitJson;
using System.Collections.Generic;
public class LoadingScript : MonoBehaviour {
	private float REPEAT_TIME = 0.5f;
	private int state = 1;
	private string username;
	private string password;
	private string url;
	private string id;
	public Text loadingMessage;

	public LoginManager loginScript;
	public CharacterScript characterScript;

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
				loadingMessage.text = "Loading.";
				state = 1;
				break;
			case 1:
				loadingMessage.text = "Loading..";
				state = 2;
				break;
			case 2:
				loadingMessage.text = "Loading...";
				state = 0;
				break;
			}
			yield return new WaitForSeconds(REPEAT_TIME); 
		}
	}

	public void StartLoginCoroutine(string username, string password)
	{
		this.username = username;
		this.password = password;

		StartCoroutine (LoginCoroutine());
	}

	private IEnumerator LoginCoroutine(){
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
					StartCoroutine(LoadCharactersCoroutine());
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

	private IEnumerator LoadCharactersCoroutine(){
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
