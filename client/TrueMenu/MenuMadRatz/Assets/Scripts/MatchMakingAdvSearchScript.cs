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
	public int image { get; set; }
}

public class MatchMakingAdvSearchScript : MonoBehaviour {
	public Button startMatch;

	List<RandomPlayer> listOfRandomPlayers;

	private string url_main = "localhost:8080/api/player/";
	private string url_rand = "/random/";
	
	public GlobalVariables _globals;

	public Text[] textFields;

<<<<<<< HEAD
	public LoadingScript loadingScript;
=======
	public Image playerPortrait;
	public Image enemyPortrait;

	public Sprite personagem0;
	public Sprite personagem1;
	public Sprite personagem2;
	public Sprite personagemET;
	private int etNumber = 30;
	
>>>>>>> origin/menu
	void OnEnable () {
		_globals = GlobalVariables.instance;

		switch (_globals.characterModel.image) {
		case 0: playerPortrait.sprite = personagem0;
			break;
		case 1: playerPortrait.sprite = personagem1;
			break;
		case 2: playerPortrait.sprite = personagem2;
			break;
		}
		if (_globals.characterModel.image == etNumber)
			playerPortrait.sprite = personagemET;

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

		switch (listOfRandomPlayers[i].image) {
		case 0: enemyPortrait.sprite = personagem0;
			break;
		case 1: enemyPortrait.sprite = personagem1;
			break;
		case 2: enemyPortrait.sprite = personagem2;
			break;
		}
		if (listOfRandomPlayers[i].image == etNumber)
			enemyPortrait.sprite = personagemET;

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

	public void OnCreate()
	{
		loadingScript.gameObject.SetActive(true);
		loadingScript.StartStartMatchCoroutine(_globals.characterModel._id, listOfRandomPlayers[0]._id);
		this.gameObject.SetActive(false);

	}
}
