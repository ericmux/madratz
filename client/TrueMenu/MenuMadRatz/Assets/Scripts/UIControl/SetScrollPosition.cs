using UnityEngine;
using System.Collections;
using UnityEngine.UI;

[RequireComponent(typeof(Scrollbar))]
public class SetScrollPosition : MonoBehaviour 
{
	[Range(0f, 1f)]
	public float scrollStart;

	void Start () 
	{
		GetComponent<Scrollbar>().value = scrollStart;
	}
}
