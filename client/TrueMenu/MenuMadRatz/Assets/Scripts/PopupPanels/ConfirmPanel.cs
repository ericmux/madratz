using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class ConfirmPanel : MonoBehaviour {

	public delegate void OnConfirmDelegate();

	public Text status;
	public Text title;

	OnConfirmDelegate confirmFunction;

	public void SetStatus(string status)
	{
		this.status.text = status;
	}

	public void SetTitle(string title)
	{
		this.title.text = title;
	}

	public void SetOnConfirm(OnConfirmDelegate onConfirm)
	{
		this.confirmFunction = onConfirm;
	}

	public void OnConfirm()
	{
		confirmFunction();
		this.gameObject.SetActive(false);
	}

}
