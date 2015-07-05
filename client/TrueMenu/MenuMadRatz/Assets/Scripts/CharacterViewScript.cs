﻿using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class CharacterViewScript : MonoBehaviour {

	public Text name;
	public Text level;

	private ConfirmPanel confirmPanel;
	private LoadingScript loadingScript;
	private CharacterScript characterSelectionScript;

	private CharacterModel model;

	public void SetCharacterModel(CharacterModel model)
	{
		this.model = model;
		this.name.text = "Nome: " + model.name;
		this.level.text = "Level: " + model.level;
	}

	public void SetConfirmPanel (ConfirmPanel confirmPanel)
	{
		this.confirmPanel = confirmPanel;
	}

	public void OnDelete()
	{
		confirmPanel.gameObject.SetActive(true);
		confirmPanel.SetStatus("Desejar deletar o personagem \"" + model.name + "\"?");
		confirmPanel.SetTitle("Confirmação");
		confirmPanel.SetOnConfirm(OnConfirmCallback);
	}

	public void SetReferences(LoadingScript loadingScript, CharacterScript characterSelectionScript)
	{
		this.loadingScript = loadingScript;
		this.characterSelectionScript = characterSelectionScript;
	}

	public void OnConfirmCallback()
	{
		loadingScript.gameObject.SetActive(true);
		characterSelectionScript.gameObject.SetActive(false);
		loadingScript.StartDeleteCharacterCoroutine(model._id);

	}
}
