using UnityEngine;
using System.Collections;
using LitJson;

public static class JSONUtils
{
	static public bool JsonDataContainsKey(JsonData data, string key)
	{
		bool result = false;
		if(data == null)
			return result;
		if(!data.IsObject)
		{
			return result;
		}
		IDictionary tdictionary = data as IDictionary;
		if(tdictionary == null)
			return result;
		if(tdictionary.Contains(key))
		{
			result = true;
		}
		return result;
	}	
}

