using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using LitJson;
using UnityEngine.UI;

public class LoginManager : MonoBehaviour {

	private string url;
	private string username;
	private string password;
	
	public Text statusText;

	public GameObject onLogin;
	public GameObject onCancel;

	void Start(){
		statusText = GameObject.Find ("StatusText").GetComponent<Text> ();
	}

	public void startLoginCoroutine(){
		url = "localhost:8080/api/login/";

		StartCoroutine (logInAPI ());
	}

	public void getUsernameFromInputField(string username){
		this.username = username;
	}

	public void getPasswordFromInputField(string password){
		this.password = password;
	}

	private IEnumerator logInAPI(){

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

					statusText.color = Color.black;
					statusText.text = "Logging as " + username;

					//GameObject.Find("AvatarSelectionPanel").transform.Translate(-1000, 0, 0);
					onLogin.SetActive(true);
					gameObject.SetActive(false);

				} catch (KeyNotFoundException e1) {
					try{
						Debug.Log(e1.Message + " API Error: " + data["err"]);

						statusText.color = Color.red;
						statusText.text = "Erro: Login ou senha invalidos";
					} catch (KeyNotFoundException e2){
						Debug.Log(e2.Message + "Internet Connection?");

						statusText.color = Color.red;
						statusText.text = "Erro: problema na conexao";
					}
				}
			} else {
				Debug.Log ("Login Error: " + loginRequest.error);

				statusText.color = Color.red;
				statusText.text = loginRequest.error;
			}
		} else {
			Debug.Log("Need both fields filled");

			statusText.color = Color.red;
			statusText.text = "Precisa preencher ambos os campos";
		}

		//statusText.color = Color.black;
	}
}
