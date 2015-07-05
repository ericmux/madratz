using UnityEngine;
using System.Collections;

public class EmptyViewScript : MonoBehaviour {
	public CharacterCreationScript characterCreationScript;
	public CharacterScript characterScript;

	public void CreateNewCharacter() {
		characterCreationScript.gameObject.SetActive(true);
		characterScript.gameObject.SetActive(false);
	}
	
	public void SetReferences(CharacterCreationScript characterCreationScript, CharacterScript characterScript)
	{
		this.characterCreationScript = characterCreationScript;
		this.characterScript = characterScript;
	}
}
