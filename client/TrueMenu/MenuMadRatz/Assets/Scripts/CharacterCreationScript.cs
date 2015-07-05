using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class CharacterCreationScript : MonoBehaviour {
	public LoadingScript loading;
	public InputField name;
	public Text status;

	void OnEnable()
	{
		status.text = "";
		name.text = "";
	}

	public void OnCreateClick()
	{
		loading.gameObject.SetActive(true);
		loading.StartCreateCharacterCoroutine(name.text);
		this.gameObject.SetActive(false);
	}

	public void SetStatus (string err, Color color)
	{
		status.text = err;
		status.color = color;
	}
}
