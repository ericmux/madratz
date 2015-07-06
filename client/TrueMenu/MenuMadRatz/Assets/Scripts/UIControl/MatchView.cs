using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System;

public class MatchView : MonoBehaviour {

	public Text title;
	public Text matchType;
	public Text winner;
	public Text date;
	public Text status;

	private MatchModel matchModel;

	public void SetMatchModel(int counter, MatchModel mm)
	{
		
		this.matchModel = mm;
		this.title.text = "Partida #" + (counter + 1);
		this.matchType.text = "Tipo: " + mm.type;
		this.date.text = "Data: " + mm.date;
		if(mm.status == "created")
		{
			this.status.text = "Simulando...";
			this.winner.text = "";
		}
		else if(mm.status == "finished")
		{
			this.status.text = "Terminado";
			this.winner.text = "Vencedor: " + mm._winner;
		}
	}
}
