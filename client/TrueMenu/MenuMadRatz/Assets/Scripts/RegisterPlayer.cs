using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using UnityEngine.UI;
using LitJson;

public class RegisterPlayer : MonoBehaviour {

	string username, password, realname, url;

	public Text messageText;
	public GameObject onSuccess;

	// Retrieves player info from textboxes and starts register routine
	public void startRegisterCoroutine(){
		url = "localhost:8080/api/register";

		StartCoroutine (register ());
	}

	public void setUsernameFromInputField(string username){
		this.username = username;
	}

	public void setPasswordFromInputField(string password){
		this.password = password;
	}

	public void setNameFromInputField(string realname){
		this.realname = realname;
	}

	private void WaitBizu(){
		onSuccess.SetActive(true);
		gameObject.SetActive(false);
	}

	// Makes request from API for registry
	private IEnumerator register(){
		Debug.Log ("Sending registry request with username = " + username + ", password = " + password + ", name = " + realname);
		Debug.Log ("Sending registry request to " + url);

		// form right url
		WWWForm regForm = new WWWForm ();
		regForm.AddField ("username", username);
		regForm.AddField ("password", password);
		regForm.AddField ("name", realname);

		// make request
		WWW regRequest = new WWW (url, regForm);

		yield return regRequest;

		if (regRequest.text != null) {  // there is response from server
			JsonData data = JsonMapper.ToObject(regRequest.text);
			try {
				Debug.Log(data["msg"] + ", " + data["id"]);
				Debug.Log("criou");

				messageText.color = Color.black;
				messageText.text = "Jogador criado";

				Invoke("WaitBizu", 2);

//				onSuccess.SetActive(true);
//				gameObject.SetActive(false);

			} catch (KeyNotFoundException e1){ // if there is non-expected response
				try{
					Debug.Log(e1.Message);
					Debug.Log("nao criou");
					Debug.Log(data["err"]);

					messageText.color = Color.red;
					messageText.text = "" + data["err"];

				} catch(KeyNotFoundException e2){
					Debug.Log(e2.Message);
					Debug.Log("nao criou");
					Debug.Log ("Internet connection?");

					messageText.color = Color.red;
					messageText.text = "Internet connection problem";
				}
			}
		} else { // error in server
			Debug.Log("Register error: " + regRequest.error);

			messageText.color = Color.red;
			messageText.text = "Internet connection problem";
		}

	}


}
