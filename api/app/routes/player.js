/////////////
// IMPORTS //
/////////////
var playerRoutes = {},
	Player = require('../models/player'),
	Script = require('../models/script'),
	validator = require('validator'),
	mongoose = require('mongoose'),
	localData = require('../services/local_data');

(function(playerRoutes) {
	/////////////
	// ROUTING //
	/////////////
	playerRoutes.list = function(req, res) {
		return Player.find({}, '_id name username', function(err, players) {
	        if (err)
	            return res.send(err);

	        return res.json(players);
	    });
	};

	playerRoutes.login = function(req, res) {
		var username = req.body.username;
		var password = req.body.password;

	    return Player.findOne({'username': username}, function(err, player) {
	        if(err)
	            return res.send(err);

	        if(!player || (password !== player.password))
	        	return res.json({err: "invalid_username_or_password"});

	        player.lastLogin = new Date();

	        return player.save(function(err) {
	        	if(err)
	        		return res.send(err);

	        	return res.json({'msg': 'login_ok', 'id': player._id});
	        });
	    });
	};

	playerRoutes.register = function(req, res) {
		var username = req.body.username;
		var usernameErr = sanitizeUsername(username);
		if(usernameErr)
			return res.json(usernameErr);

		var password = req.body.password;
		var passwordErr = sanitizePassword(password);
		if(passwordErr)
			return res.json(passwordErr);

		var name     = req.body.name;
		var nameErr = sanitizeName(name);
		if(nameErr)
			return res.json(nameErr);

	    return Player.findOne({'username': username}, function(err, player) {
	        if(err)
	            return res.send(err);

	        if(player)
	        	return res.json({err: "username_already_in_use"});

	        var actualDate = new Date();
	        var newPlayer = new Player({'username': username,
	        							'password': password,
	        							'name': name,
	        							'createdOn': actualDate,
	        							'lastLogin': actualDate });

	        return newPlayer.save(function(err, player) {
	        	if(err)
	        		return res.send(err);

	        	return localData.getDefaultScript(function(script) {
	        		var newScript = new Script({_owner: player.id,
						title: localData.getDefaultScriptName(),
						code: new Buffer(script).toString('base64'),
						createdOn: actualDate,
						lastUpdate: actualDate,
						isDefault: true
					});

					return newScript.save(function(err) {
						if(err)
							return res.send(err);

						return res.json({'msg': 'user_created', 'id': player._id});
					});
	        	});
	        });
	    });
	};

	playerRoutes.read = function(req, res) {
		var id = req.params.player_id;

		if(!mongoose.Types.ObjectId.isValid(id))
			return res.json({err: "invalid_player_id"});

		return Player.findById(id, function(err, player) {
			if(err)
				return res.send(err);

			return res.json(player);
		});
	};


	playerRoutes.update = function(req, res) {
		var id = req.params.player_id;

		if(!mongoose.Types.ObjectId.isValid(id))
			return res.json({err: "invalid_player_id"});

		return Player.findById(id, function(err, player) {
			if(err)
				return res.send(err);

			if(!player)
				return res.json({err: "player_not_found"});

			var name = req.body.name;

			var nameErr = sanitizeName(name);

			if(nameErr)
				return res.json(nameErr);

			player.name = name;

			return player.save(function(err) {
				if(err)
					return res.send(err);

				return res.json({message: "Player " + player.name + " updated."});
			});
		});
	};

	playerRoutes.delete = function(req, res) {
		var id = req.params.player_id;

		if(!mongoose.Types.ObjectId.isValid(id))
			return res.json({err: "invalid_player_id"});

		return Player.remove({'_id': id}, function(err, player) {
			if(err)
				return res.send();

			return res.json({ message: 'Succesfully deleted'});
		});
	};

	/////////////////////
	// DATA VALIDATION //
	/////////////////////
	function sanitizeName(name) {
		if(typeof name === "undefined")
			return { err: "name_undefined" };

		if(name.length < 4)
			return { err: "name_too_short" };

	    if(!validator.isAlphanumeric(name))
	        return { err: "name_not_alphanumeric" };
	};

	function sanitizeUsername(name) {
		if(typeof name === "undefined")
			return { err: "username_undefined" };

		if(name.length < 4)
			return { err: "username_too_short" };

	    if(!validator.isAlphanumeric(name))
	        return { err: "username_not_alphanumeric" };
	};

	function sanitizePassword(name) {
		if(typeof name === "undefined")
			return { err: "password_undefined" };

		if(name.length < 6)
			return { err: "password_too_short" };
	};
}(playerRoutes));

module.exports = playerRoutes;
