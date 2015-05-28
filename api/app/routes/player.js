/////////////
// IMPORTS //
/////////////
var Player = require('../models/player');

var validator = require('validator');

/////////////
// ROUTING //
/////////////

exports.list = function(req, res) {
	Player.find(function(err, players) {
        if (err)
            return res.send(err);

        return res.json(players);
    });
};

exports.create = function(req, res) {
	var player = new Player();
	var name = req.body.name;

	var nameErr = sanitizeName(name);

	if(nameErr)
		return res.json(nameErr);

	player.name = name;

	return player.save(function(err) {
		if(err)
			return res.send(err);

		return res.json({message: 'Player created: ' + player.name});
	});
};

exports.read = function(req, res) {
	Player.findById(req.params.player_id, function(err, player) {
		if(err)
			return res.send(err);

		return res.json(player);
	});
};

exports.update = function(req, res) {
	Player.findById(req.params.player_id, function(err, player) {
		if(err)
			return res.send(err);

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

exports.delete = function(req, res) {
	Player.remove({ '_id': req.params.player_id }, function(err, player) {
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

	if(!validator.isAlphanumeric())
		return { err: "name_not_alphanumeric" };
}