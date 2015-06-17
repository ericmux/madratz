using UnityEngine;
using System.Collections;
// using LitJson;

public class LoginManager : MonoBehaviour {

	private string url;

	public void loginInAPI(string name){
		url = "localhost:8080/api/login/";

		StartCoroutine (loginWithName (name));
	}

	public void logupInAPI(string name){
		url = "http://localhost:8080/api/player";

		StartCoroutine (logupWithName (name));
	}

	private IEnumerator loginWithName(string name){
		Debug.Log ("Logging in with " + url + name);

		WWW loginInfo = new WWW (url + name);

		yield return loginInfo;

		/*if (loginInfo.text != null) {
			try{
				JsonData data = JsonMapper.ToObject(loginInfo.text);
				Debug.Log ("Login Ok!: " + data["name"]);
			} catch (JsonException e){
				Debug.Log ("Name not found. Error: " + e.Message);
			}
		} else {
			Debug.Log("Login Error: "  + loginInfo.error);
		}*/
	}

	private IEnumerator logupWithName(string name){
		Debug.Log ("Sending logup info to " + url);
		
		WWWForm form = new WWWForm ();
		form.AddField ("name", name);
		
		WWW logupInfo = new WWW (url, form);
		
		yield return logupInfo;
		
		/*if (logupInfo.error != null) {
			Debug.Log ("Logup Error: " + logupInfo.error);
		} else {
			JsonData data = JsonMapper.ToObject(logupInfo.text);

			Debug.Log(data["message"]);
		}*/
	}
}
