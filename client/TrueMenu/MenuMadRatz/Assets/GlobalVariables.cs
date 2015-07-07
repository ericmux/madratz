using UnityEngine;
using System.Collections;

public class GlobalVariables : MonoBehaviour {


	public static string api_url = "http://54.207.86.108:8080/api/";

	public static string replay_dir = Application.persistentDataPath + "/replay/";
	public string id;

	public string username;
	public string password;

	public CharacterModel characterModel;

	private static GlobalVariables _instance;
	
	public static GlobalVariables instance {
		get {
			if(_instance == null){
				_instance = GameObject.FindObjectsOfType<GlobalVariables>()[0];
			}

			_instance.username = "Muxagata";
			_instance.password = "a1b2c3d4";

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
