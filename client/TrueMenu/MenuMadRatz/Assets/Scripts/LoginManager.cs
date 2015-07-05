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
	public LoadingScript loading;

	public void OnConnectClick()
	{
		loading.gameObject.SetActive(true);
		loading.StartLoginCoroutine(username, password);
		this.gameObject.SetActive(false);
	}
	
	public void getUsernameFromInputField(string username){
		this.username = username;
	}

	public void getPasswordFromInputField(string password){
		this.password = password;
	}

	public void setStatusText (string str)
	{
		this.statusText.text = str;
	}

	public void setStatusColor (Color color)
	{
		this.statusText.color = color;
	}
}
