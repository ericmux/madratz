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

	private static List<RandomPlayer> enemyPlayers;
	
	private string url_main = GlobalVariables.api_url + "/player/";
	private string url_rand = "/random/";
	
	private static GlobalVariables _globals;

	public Text[] textFields;

	public LoadingScript loadingScript;

	public Image playerPortrait;
	public Image[] enemyPortraits;
	
	public Sprite personagem0;
	public Sprite personagem1;
	public Sprite personagem2;
	public Sprite personagemET;
	private int etNumber = 30;

	void OnEnable () {
		_globals = GlobalVariables.instance;
		if (_globals.characterModel == null)
			return;


		foreach (Image img in enemyPortraits)
			img.enabled = false;

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

		enemyPlayers = null;
		startMatch.gameObject.SetActive(false);
		
		StartCoroutine (searchAdversaries ());
	}
	
	private IEnumerator searchAdversaries(){
		
		StartCoroutine (requestRandomAdversaries ());
		
		int i = 0;
		while (enemyPlayers == null) {
			for(int j = 0; j < textFields.Length; j++){
				textFields[j].text = string.Concat("Carregando", string.Join ("", Enumerable.Repeat(".",i).ToArray()));
			}

			if(i++ == 3) i=0;
			
			yield return new WaitForSeconds(.5f);
		}

		for(i = 0; i < enemyPlayers.Count; i++){
			textFields[i].text = (string) enemyPlayers[i].name;
			enemyPortraits[i].enabled = true;
			switch (enemyPlayers[i].image) {
				case 0: enemyPortraits[i].sprite = personagem0;
					break;
				case 1: enemyPortraits[i].sprite = personagem1;
					break;
				case 2: enemyPortraits[i].sprite = personagem2;
					break;
			}

			if (enemyPlayers[i].image == etNumber)
				enemyPortraits[i].sprite = personagemET;
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
			enemyPlayers = JsonMapper.ToObject<List<RandomPlayer>> (loginRequest.text);
		}
	}

	public void OnCreate()
	{
		loadingScript.gameObject.SetActive(true);

		string[] enemies = enemyPlayers.Select (i => i._id).ToArray();

		loadingScript.StartStartMatchCoroutine(_globals.characterModel._id, enemies);
		this.gameObject.SetActive(false);

	}
}
