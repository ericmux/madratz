using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using UnityEngine.UI;

public class CharacterCreationScript : MonoBehaviour {
	public LoadingScript loading;
	public InputField name;
	public Text status;
	public GameObject personagem0;
	public GameObject personagem1;
	public GameObject personagem2;
	public GameObject personagemET;
	public int count = 0;
	private int etNumber = 30;

	void OnEnable()
	{
		status.text = "";
		name.text = "";
		personagem0.SetActive (true);
		personagem1.SetActive (false);
		personagem2.SetActive (false);
	}

	public void OnCreateClick()
	{
		loading.gameObject.SetActive(true);
		personagem0.SetActive (false);
		personagem1.SetActive (false);
		personagem2.SetActive (false);
		personagemET.SetActive (false);
		if(count == etNumber)
			loading.StartCreateCharacterCoroutine(name.text, count);
		else
			loading.StartCreateCharacterCoroutine(name.text, count%3);
		this.gameObject.SetActive(false);
	}

	public void OnNextClick() 
	{
		count++;
		switch (count%3) {
		case 0:
			personagem2.SetActive (false);
			personagem0.SetActive (true);
			personagemET.SetActive (false);
			break;
		case 1:
			personagem0.SetActive (false);
			personagem1.SetActive (true);
			personagemET.SetActive (false);
			break;
		case 2:
			personagem1.SetActive (false);
			personagem2.SetActive (true);
			personagemET.SetActive (false);
			break;
		}
		if (count == etNumber) {
			personagem0.SetActive (false);
			personagem1.SetActive (false);
			personagem2.SetActive (false);
			personagemET.SetActive (true);
		}
	}

	public void OnPreviousClick() 
	{
		count--;
		if (count < 0)
			count += 3;
		switch (count%3) {
		case 0:
			personagem1.SetActive (false);
			personagem0.SetActive (true);
			personagemET.SetActive (false);
			break;
		case 1:
			personagem2.SetActive (false);
			personagem1.SetActive (true);
			personagemET.SetActive (false);
			break;
		case 2:
			personagem0.SetActive (false);
			personagem2.SetActive (true);
			personagemET.SetActive (false);
			break;
		}
	}

	public void SetStatus (string err, Color color)
	{
		status.text = err;
		status.color = color;
	}
}
