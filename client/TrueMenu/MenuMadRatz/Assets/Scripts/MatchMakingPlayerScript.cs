﻿using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class MatchMakingPlayerScript : MonoBehaviour {

	public GlobalVariables _globals;
	public Text textField;
	
	void OnEnable () {
		textField = GetComponent<Text> ();
		_globals = GlobalVariables.instance;
		textField.text = _globals.characterModel.name;
	}
}
