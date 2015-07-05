using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class CharacterViewScript : MonoBehaviour {

	public Text name;
	public Text level;
	
	public void SetName (string name)
	{
		this.name.text = "Nome: " + name;
	}

	public void SetLevel (int level)
	{
		this.level.text = "Level: " + level;
	}
}
