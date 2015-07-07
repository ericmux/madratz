using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using LitJson;
using UnityEngine.UI;
using UnityEngine.EventSystems;

public class LoginManager : MonoBehaviour {
	EventSystem system;

	private string url;

	public Text statusText;

	public InputField username;
	public InputField password;

	public GameObject onLogin;
	public GameObject onCancel;

	public LoadingScript loading;

	void Start ()
	{
		system = EventSystem.current;
		
	}

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

	public void Update()
	{
		if (Input.GetKeyDown(KeyCode.Tab))
		{
			Selectable next = system.currentSelectedGameObject.GetComponent<Selectable>().FindSelectableOnDown();
			
			if (next!= null) {
				
				InputField inputfield = next.GetComponent<InputField>();
				if (inputfield !=null) inputfield.OnPointerClick(new PointerEventData(system));  //if it's an input field, also set the text caret
				
				system.SetSelectedGameObject(next.gameObject, new BaseEventData(system));
			}
			
		}
		else if (Input.GetKeyDown (KeyCode.Return) || Input.GetKeyDown (KeyCode.KeypadEnter))
			OnConnectClick ();
	}
}
