using UnityEngine;
using System.Collections;

public class GameMenuPanel : MonoBehaviour {
	public LoadingScript loadingPanel;

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

	public void OnSelectScriptClick()
	{
		this.gameObject.SetActive(false);
		loadingPanel.gameObject.SetActive(true);
		loadingPanel.StartLoadScriptsSimpleCoroutine();
	}
}
