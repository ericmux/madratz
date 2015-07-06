using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class MatchList
{
	public string		msg 		{ get; set; }
	public List<MatchModel> list 	{ get; set; }
}
public class MatchModel
{
	public string   _id     	{ get; set; }
	public string   _creator   	{ get; set; }
	public string   _character 	{ get; set; }
	public List<string> _enemies    	{ get; set; }
	public string   status     	{ get; set; }
	public string   date     	{ get; set; }
	public string   type     	{ get; set; }
	public string   _winner     	{ get; set; }
}

