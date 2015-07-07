using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using UnityEngine.UI;

public class CharacterCreationScript : MonoBehaviour {
	public LoadingScript loading;
	public InputField name;
	public Text status;
	public Image portrait;
	
	public Sprite personagem0;
	public Sprite personagem1;
	public Sprite personagem2;
	public Sprite personagemET;
	public int count = 0;
	private int etNumber = 30;

	void OnEnable()
	{
		status.text = "";
		name.text = "";
		portrait.sprite = personagem0;
	}

	public void OnCreateClick()
	{
		loading.gameObject.SetActive(true);

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
			portrait.sprite = personagem0;
			break;
		case 1:
			portrait.sprite = personagem1;
			break;
		case 2:
			portrait.sprite = personagem2;
			break;
		}
		if (count == etNumber) {
			portrait.sprite = personagemET;
		}
	}

	public void OnPreviousClick() 
	{
		count--;
		if (count < 0)
			count += 3;
		switch (count%3) {
		case 0:
			portrait.sprite = personagem0;
			break;
		case 1:
			portrait.sprite = personagem1;
			break;
		case 2:
			portrait.sprite = personagem2;
			break;
		}
	}

	public void SetStatus (string err, Color color)
	{
		status.text = err;
		status.color = color;
	}
}
