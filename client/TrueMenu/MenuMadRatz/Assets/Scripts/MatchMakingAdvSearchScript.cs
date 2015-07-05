using UnityEngine;
using UnityEngine.UI;
using System.Collections;
using System.Linq;

public class MatchMakingAdvSearchScript : MonoBehaviour {

	public GlobalVariables _globals;
	public Text[] textFields;
	
	
	void OnEnable() {
		textFields = GetComponentsInChildren<Text> ();
		_globals = GlobalVariables.instance;

		if (!gameObject.activeSelf) return;

		if (textFields.Length > 3) {
			textFields = textFields.Skip(3).ToArray();
		}

		StartCoroutine (searchAdversaries ());
	}

	private IEnumerator searchAdversaries(){
		int i = 0;
		while (true) {
			foreach (Text t in textFields){
				t.text = string.Concat("Carregando", string.Join ("", Enumerable.Repeat(".",i).ToArray()));
			}
			if(i++ == 3) i=0;

			yield return new WaitForSeconds(.5f);
		}
	}

}
