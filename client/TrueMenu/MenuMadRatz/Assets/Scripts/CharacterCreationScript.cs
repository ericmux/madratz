using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class CharacterCreationScript : MonoBehaviour {

	private string name;
	public LoadingScript loading;
	public Text status;

	public void OnCreateClick()
	{
		loading.gameObject.SetActive(true);
		loading.StartCreateCharacterCoroutine(name);
		this.gameObject.SetActive(false);
	}
	
	public void getNameFromInputField(string name){
		this.name = name;
		Debug.Log (name);
	}

	public void SetStatus (string err, Color color)
	{
		status.text = err;
		status.color = color;
	}
}
