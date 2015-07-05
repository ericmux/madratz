/////////////
// IMPORTS //
/////////////
var characterRoutes = {},
	Player = require('../models/player'),
	Character = require('../models/character'),
	validator = require('validator');

(function(characterRoutes) {
	/////////////
	// ROUTING //
	/////////////

	characterRoutes.list = function(req, res) {
		var id = req.params.player_id;
		var idErr = sanitizeId(id)
		if(idErr)
			return res.json(idErr);

		return Player.findById(id, function(err, player) {
			if(err)
				return res.send(err);

			if(!player)
				return res.json({err: "user_does_not_exist"});

			return Character.find({'_owner': id}, function(err, character) {
				if(err)
					return res.send(err);

				return res.json({msg: 'character_list', list: character});
			});
		});
	};

	characterRoutes.list_random = function(req,res) {
		var numChars = req.params.num_chars;

		var randChars = [];
		return Character.find({ _owner: { $ne: req.params.player_id }}, '_id name', function(err, chars) {
	        if (err) return err;

	        var allChars = chars;
	        if(allChars.length === 0) return res.json({err: "no_foreign_avatars_to_fight"});

	        var i = 0;
	        while(randChars.length < numChars){
				var roll = Math.floor(2*Math.random());
				if(roll) randChars.push(allChars[i]);

				if(++i === allChars.length) i=0;
			}

			return res.json(randChars);
		});
	}

	characterRoutes.create = function(req, res) {
		var id = req.params.player_id;
		var idErr = sanitizeId(id)
		if(idErr)
			return res.json(idErr);

		var name = req.body.name;
		var nameErr = sanitizeName(name);
		if(nameErr)
			return res.json(nameErr);

		return Player.findById(id, function(err, player) {
			if(err)
				return res.send(err);

			if(!player)
				return res.json({err: "user_does_not_exist"});

			return Character.findOne({'name': name}, function(err, character) {
				if(err)
					return res.send(err);

				if(character)
					return res.json({err: 'name_already_in_use'});

				var newCharacter = new Character({'_owner': id,
												  'name': name,
												  'createdOn': new Date()});

				return newCharacter.save(function(err) {
					if(err)
						return res.send(err);

					return res.json({msg: 'character_created'});
				});
			});
		});
	};

	characterRoutes.read = function(req, res) {
		var playerId = req.params.player_id;
		var idErr = sanitizeId(playerId, 'player')
		if(idErr)
			return res.json(idErr);

		var charId = req.params.char_id;
		var idErr = sanitizeId(charId, 'character')
		if(idErr)
			return res.json(idErr);

		return Player.findById(playerId, function(err, player) {
			if(err)
				return res.send(err);

			if(!player)
				return res.json({err: "user_does_not_exist"});

			return Character.findById(charId, function(err, character) {
				if(err)
					return res.send(err);

				if(!character || (character._owner != playerId))
					return res.json({err: 'character_does_not_exist'});

				return res.json({msg: 'character_info', 'character': {'name': character.name}});
			});
		});
	};

	characterRoutes.delete = function(req, res) {
		var playerId = req.params.player_id;
		var idErr = sanitizeId(playerId, 'player')
		if(idErr)
			return res.json(idErr);

		var charId = req.params.char_id;
		var idErr = sanitizeId(charId, 'char')
		if(idErr)
			return res.json(idErr);

		return Player.findById(playerId, function(err, player) {
			if(err)
				return res.send(err);

			if(!player)
				return res.json({err: "user_does_not_exist"});

			return Character.findById(charId, function(err, character) {
				if(err)
					return res.send(err);

				if(!character || (character._owner != playerId))
					return res.json({err: 'character_does_not_exist'});

				return Character.remove({ '_id': charId }, function(err) {
					if(err)
						return res.send();

					return res.json({msg: 'character_deleted'});
				});
			});
		});
	};

	/////////////////////
	// DATA VALIDATION //
	/////////////////////
	function sanitizeId(id, type) {
		if(!type)
			type = '';
		if(typeof id === "undefined")
			return { err: type + "id_undefined" };

		if(!validator.isMongoId(id))
			return { err: type + "invalid_id" };
	};

	function sanitizeName(name) {
		if(typeof name === "undefined")
			return { err: "name_undefined" };

		if(name.length < 3)
			return { err: "name_too_short" };

	    if(!validator.isAlphanumeric(name))
	        return { err: "name_not_alphanumeric" };
	};
}(characterRoutes));

module.exports = characterRoutes;