using UnityEngine;
using System.Collections;

public class GameMenuPanel : MonoBehaviour {
	public LoadingScript loadingPanel;

	CharacterModel characterModel;
	
	public void SetCharacterModel(CharacterModel model)
	{
		this.characterModel = model;
	}

	public void OnSelectCharacterClick()
	{
		this.gameObject.SetActive(false);
		loadingPanel.gameObject.SetActive(true);
		loadingPanel.StartLoadCharactersCoroutine();
	}

	public void OnManageScriptsClick()
	{
		this.gameObject.SetActive(false);
		loadingPanel.gameObject.SetActive(true);
		loadingPanel.StartLoadScriptsCoroutine();
	}
}
