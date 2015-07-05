using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class MatchMakingPlayerScript : MonoBehaviour {

	public GlobalVariables _globals;
	public Text textField;

	// Use this for initialization
	void Start () {
		textField = GetComponent<Text> ();
		_globals = GlobalVariables.instance;
	}
	
	void OnGUI(){
		textField.text = _globals.username;
	}
}
