using UnityEngine;
using System.Collections;
using LitJson;

public class LoginManager : MonoBehaviour {

	private string url;

	public void loginInAPI(string name){
		url = "localhost:8080/api/login/";

		StartCoroutine (loginWithName (name));
	}

	private IEnumerator loginWithName(string name){
		Debug.Log ("Logging in with " + url + name);

		WWW loginInfo = new WWW (url + name);

		yield return loginInfo;

		if (loginInfo.text != null) {
			try{
				JsonData data = JsonMapper.ToObject(loginInfo.text);
				Debug.Log ("Login Ok!: " + data["name"]);
			} catch (JsonException e){
				Debug.Log ("Name not found. Error: " + e.Message);
			}
		} else {
			Debug.Log("Login Error: "  + loginInfo.error);
		}
	}
}
