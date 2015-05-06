using UnityEngine;
using System.Collections.Generic;
using LitJson;

public class PlayerData {
	public static string Name { get; set;}
	public static List<RatData> Rats { get; set;}
	

	public static void setDataFromFile(string fileName) {

	}

	private void setDataFromJSON(JsonData jsonData) {
		
	}

	public static void loadDataFromServer() {
		string json = @"{
				""name"": ""Samuel"",
				""rats"": [
					{
						""name"": ""Madmax"",
						""class"": ""slayer"",
						""level"": 10
					}, 

					{
						""name"": ""Legolas"",
						""class"": ""shooter"",
						""level"": 5
					}
				]
		}";

		// JsonMapper.ToObject<PlayerData> (json);
		JsonData data = JsonMapper.ToObject (json);

		Name = (string) data ["name"];
		Rats = jsonArrayToRats (data ["rats"]);
	}

	private static List<RatData> jsonArrayToRats(JsonData data) {
		List<RatData> rats = new List<RatData>();
		RatData ratData;
		for (int i = 0; i < data.Count; i ++) {
			ratData = new RatData();
			ratData.Name = (string) data[i]["name"];
			ratData.Class = (string) data[i]["class"];

			rats.Add(ratData);
		}

		return rats;
	}

}
