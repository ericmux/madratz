using UnityEngine;
using System.Collections;

public class GlobalVariables : MonoBehaviour {


	public string id;

	public string username;
	public string password;


	private static GlobalVariables _instance;
	
	public static GlobalVariables instance {
		get {
			if(_instance == null){
				_instance = GameObject.FindObjectsOfType<GlobalVariables>()[0];
			}

			_instance.username 	= "Muxagata";
			_instance.password 	= "a1b2c3d4";
			_instance.id 		= "5598f6053eade5bdb81fe4cb";

			return _instance;
		}
	
	}
	
	void Awake(){
		if(_instance == null)
		{
			_instance = this;
			DontDestroyOnLoad(this);
		}
		else if(this != _instance) Destroy(this.gameObject);
	}

}
