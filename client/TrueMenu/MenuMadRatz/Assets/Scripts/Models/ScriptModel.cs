using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class ScriptList
{
	public string		msg 		{ get; set; }
	public string		_owner 		{ get; set; }
	public List<ScriptModel> list 	{ get; set; }
}
public class ScriptModel
{
	public string   _id     	{ get; set; }
	public string   _owner     	{ get; set; }
	public string   title     	{ get; set; }
	public string   code     	{ get; set; }
	public bool		isDefault 	{ get; set; }
	public string	createdOn 	{ get; set; }
}
