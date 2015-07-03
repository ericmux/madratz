var localData 	= {},
	config     	= require("../config.js"),
	fs 			= require('fs');


(function(localData) {
	var _defaultScriptFileName = 'follow_and_shoot.py';
	var _defaultScriptName = 'FollowAndShoot';

	localData.getDefaultScriptName = function() {
		return _defaultScriptName;
	};

	localData.getDefaultScript = function(callback) {
		fs.readFile(config.getDefaultDir() + _defaultScriptFileName, 'utf8', function (err, data) {
			if (err) {
				return console.log(err);
			}

			return callback(data);
		});
	};
}(localData));

module.exports = localData;