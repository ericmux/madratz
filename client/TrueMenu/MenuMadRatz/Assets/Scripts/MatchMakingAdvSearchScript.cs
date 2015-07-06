using UnityEngine;
using UnityEngine.UI;
using System.Collections;
using System.Linq;
using LitJson;

public class MatchMakingAdvSearchScript : MonoBehaviour {
	
	private string url_main = "localhost:8080/api/player/";
	private string url_rand = "/random/";
	
	public GlobalVariables _globals;
	public Text[] textFields;
	public JsonData adversaries;
	public Image playerPortrait;

	public Sprite personagem0;
	public Sprite personagem1;
	public Sprite personagem2;
	public Sprite personagemET;
	private int etNumber = 30;
	
	
	void OnEnable () {
		textFields = GetComponentsInChildren<Text> ();
		_globals = GlobalVariables.instance;
		adversaries = null;
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
		
		if (!gameObject.activeSelf) return;
		
		if (textFields.Length > 3) {
			textFields = textFields.Skip(3).ToArray();
		}
		
		StartCoroutine (searchAdversaries ());
	}
	
	private IEnumerator searchAdversaries(){
		
		StartCoroutine (requestRandomAdversaries ());
		
		int i = 0;
		while (adversaries == null) {
			foreach (Text t in textFields){
				t.text = string.Concat("Carregando", string.Join ("", Enumerable.Repeat(".",i).ToArray()));
			}
			if(i++ == 3) i=0;
			
			yield return new WaitForSeconds(.5f);
		}
		
		for(i = 0; i < textFields.Length; i++){
			textFields[i].text = (string) adversaries[i]["name"];
		}
	}
	
	private IEnumerator requestRandomAdversaries(){
		
		//remove this.
		yield return new WaitForSeconds (1.5f);
		
		string url = url_main + _globals.id + url_rand + textFields.Length.ToString() ;
		WWW loginRequest = new WWW (url);
		
		print (url);
		yield return loginRequest;
	
		if (loginRequest.text != null) {
			adversaries = JsonMapper.ToObject (loginRequest.text);
		}
	}
}
