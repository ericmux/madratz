using UnityEngine;
using UnityEngine.UI;
using System.Collections;
using System.Linq;
using LitJson;
using System.Collections.Generic;

public class RandomPlayer
{
	public string _id { get; set; }
	public string name { get; set; }
}

public class MatchMakingAdvSearchScript : MonoBehaviour {
	public Button startMatch;

	List<RandomPlayer> listOfRandomPlayers;

	private string url_main = "localhost:8080/api/player/";
	private string url_rand = "/random/";
	
	public GlobalVariables _globals;

	public Text[] textFields;

	void OnEnable () {
		_globals = GlobalVariables.instance;
		listOfRandomPlayers = null;
		startMatch.gameObject.SetActive(false);

		if (!gameObject.activeSelf) return;
		
		StartCoroutine (searchAdversaries ());
	}
	
	private IEnumerator searchAdversaries(){
		
		StartCoroutine (requestRandomAdversaries ());
		
		int i = 0;
		while (listOfRandomPlayers == null) {
			for(int j = 0; j < textFields.Length; j++){
				textFields[j].text = string.Concat("Carregando", string.Join ("", Enumerable.Repeat(".",i).ToArray()));
			}

			if(i++ == 3) i=0;
			
			yield return new WaitForSeconds(.5f);
		}
		
		for(i = 0; i < listOfRandomPlayers.Count; i++){
			textFields[i].text = (string) listOfRandomPlayers[i].name;
		}

		startMatch.gameObject.SetActive(true);
	}
	
	private IEnumerator requestRandomAdversaries(){
		
		//remove this.
		yield return new WaitForSeconds (1.5f);
		
		string url = url_main + _globals.id + url_rand + textFields.Length.ToString() ;
		WWW loginRequest = new WWW (url);
		
		print (url);
		yield return loginRequest;
	
		if (loginRequest.text != null) {
			listOfRandomPlayers = JsonMapper.ToObject<List<RandomPlayer>> (loginRequest.text);
		}
	}
}
