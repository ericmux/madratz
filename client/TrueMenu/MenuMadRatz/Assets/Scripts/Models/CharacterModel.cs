using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class CharacterList
{	
	public string 				msg { get; set; }
	public List<CharacterModel> list { get; set; }
}

public class CharacterModel
{
	public string   _id     { get; set; }
	public string   _owner     { get; set; }
	public string 	script 	{ get; set; }
	public string   name     { get; set; }
	public int   	level     { get; set; }
	public int   	exp     { get; set; }
	public int   	hp     { get; set; }
}

