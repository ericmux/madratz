using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System;
using System.IO;

public class MatchView : MonoBehaviour {

	public Text title;
	public Text matchType;
	public Text winner;
	public Text date;
	public Text status;

	public Button download;
	public Text buttonText;
	public Button view;

	private MatchModel matchModel;
	void Awake()
	{
		if(!Directory.Exists(GlobalVariables.replay_dir))
		{
			Directory.CreateDirectory(GlobalVariables.replay_dir);
		}
	}

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
			download.interactable = false;
			buttonText.text = "Baixar";
			view.interactable = false;
		}
		else if(mm.status == "finished")
		{
			this.status.text = "Terminado";
			this.winner.text = "Vencedor: " + mm._winner;
			if (File.Exists(GlobalVariables.replay_dir + matchModel._id + ".out"))
			{
				download.interactable = false;
				buttonText.text = "Baixado";
				view.interactable = true;
			}
			else
			{
				download.interactable = true;
				buttonText.text = "Baixar";
				view.interactable = false;
			}
		}
	}

	public void OnDownload()
	{
		StartCoroutine(DownloadSnapshotsCoroutine());
		download.interactable = false;
		buttonText.text = "Baixando...";
	}

	private IEnumerator DownloadSnapshotsCoroutine ()
	{
		string url = GlobalVariables.api_url + "/player/" + matchModel._creator + "/match/" + matchModel._id + "/getsnapshots";
		WWW www = new WWW(url);

		yield return www;
		if(www.bytes != null) {
			if (File.Exists(GlobalVariables.replay_dir + matchModel._id + ".out"))
			{
				Debug.Log(matchModel._id + " file data already exists.");
				yield break;
			}
			File.WriteAllBytes(GlobalVariables.replay_dir + matchModel._id + ".out", www.bytes);
			view.interactable = true;
			buttonText.text = "Baixado";
		}
		else
		{
			buttonText.text = "Baixar";
			download.interactable = true;
		}
	}

	public void OnView()
	{
		BattleDirectorController.matchId = matchModel._id;
		Application.LoadLevel (1);
	}
}
