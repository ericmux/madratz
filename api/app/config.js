var config = {};
var thrift = require('thrift');

(function(config) {
	config.getDatabaseURL = function() {
		var dbUser =  "api-master";
		var dbPassword = "madratz";
		var dbUrl = "ds041177.mongolab.com:41177/madratz-api";

		return "mongodb://" + dbUser + ":" + dbPassword + "@" + dbUrl;
	};

	config.getSimulationServerInfo = function() {
		var ip = "54.207.86.108";
		var port = 1199;
		var thriftConfig = {protocol: thrift.TCompactProtocol};

		return {'ip': ip, 'port': port, 'config': thriftConfig};
	};

	config.getDefaultDir = function() {
		var dir = './defaults/';
		return dir;
	};

}(config));

module.exports = config;