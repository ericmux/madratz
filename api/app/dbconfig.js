module.exports = {
	getURL: function() {
		var dbUser =  "api-master";
		var dbPassword = "madratz";
		var dbUrl = "ds041177.mongolab.com:41177/madratz-api";

		return "mongodb://" + dbUser + ":" + dbPassword + "@" + dbUrl;
	}
}