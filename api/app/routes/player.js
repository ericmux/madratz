/////////////
// IMPORTS //
/////////////
var playerRoutes = {},
	Player = require('../models/player'),
	validator = require('validator'),
	mongoose = require('mongoose');

(function(playerRoutes) {
	/////////////
	// ROUTING //
	/////////////
	playerRoutes.list = function(req, res) {
		return Player.find({}, '_id name', function(err, players) {
	        if (err)
	            return res.send(err);

	        return res.json(players);
	    });
	};

	playerRoutes.login = function(req, res) {
	    return Player.findOne({name: req.params.player_name}, function(err, player) {
	        if(err)
	            return res.send(err);

	        return res.json(player);
	    });
	};

	playerRoutes.create = function(req, res) {
		var name = req.body.name;

		return Player.findOne({"name": name}, function(err, player) {
	        if(err)
	            return res.send(err);
	        if(player)
	        	return res.json({err: "player_already_exists"});

	        var nameErr = sanitizeName(name);

			if(nameErr)
				return res.json(nameErr);

			player = new Player({"name": name});

			return player.save(function(err) {
				if(err)
					return res.send(err);

				return res.json({message: 'Player created: ' + player.name});
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
}(playerRoutes));

module.exports = playerRoutes;
