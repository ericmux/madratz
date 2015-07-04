using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using LitJson;

public class RegisterPlayer : MonoBehaviour {



	// Retrieves player info from textboxes and starts register routine
	public void startRegisterCoroutine(string username/*, string password, string name*/){
		string url = "localhost:8080/api/register/";
		string password = "123456";
		string name = "MauroBrito";
		// TODO: put textbox info retrieval

		StartCoroutine (register (username, password, name, url));
	}

	// Makes request from API for registry
	private IEnumerator register(string username, string password, string name, string url){
		Debug.Log ("Sending registry request with username = " + username + ", password = " + password + ", name = " + name);
		Debug.Log ("Sending registry request to " + url);

		// form right url
		WWWForm regForm = new WWWForm ();
		regForm.AddField ("username", username);
		regForm.AddField ("password", password);
		regForm.AddField ("name", name);

		// make request
		WWW regRequest = new WWW (url, regForm);

		yield return regRequest;

		if (regRequest.text != null) {  // there is response from server
			JsonData data = JsonMapper.ToObject(regRequest.text);
			try {
				Debug.Log(data["msg"] + ", " + data["id"]);
				Debug.Log("criou");
			} catch (KeyNotFoundException e){ // if there is non-expected response
				Debug.Log(e.Message);
				Debug.Log("nao criou");
				Debug.Log(data["err"]);
			}
		} else { // error in server
			Debug.Log("Register error: " + regRequest.error);
		}

	}
}
