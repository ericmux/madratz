using UnityEngine;
using System.Collections;
using LitJson;


public class PlayerRequest : MonoBehaviour {
	
	private string url;

	public void newgame(int scene){
		StartCoroutine (loadNewGameRep (scene));
	}

	private IEnumerator loadNewGameRep(int scene){
		url = "localhost:8080/api/creategame";

		WWWForm form = new WWWForm ();

		form.AddField ("game", "newgame");

		WWW newGamePost = new WWW (url, form);

		yield return newGamePost;
		
		if (newGamePost.error != null) {
			Debug.Log ("New Game Error: " + newGamePost.error);

		} else {
			JsonData data = JsonMapper.ToObject(newGamePost.text);
			
			Debug.Log(data["message"]);

			Application.LoadLevel (scene);
		}

	}
}
