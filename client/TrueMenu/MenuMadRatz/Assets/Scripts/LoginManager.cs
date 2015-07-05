using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using LitJson;
using UnityEngine.UI;

public class LoginManager : MonoBehaviour {

	private string url;

	public Text statusText;

	public InputField username;
	public InputField password;

	public GameObject onLogin;
	public GameObject onCancel;

	public LoadingScript loading;

	void OnEnable()
	{
		username.text = "";
		password.text = "";
	}

	public void OnConnectClick()
	{
		loading.gameObject.SetActive(true);
		loading.StartLoginCoroutine(username.text, password.text);
		this.gameObject.SetActive(false);
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
