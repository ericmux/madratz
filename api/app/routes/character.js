/////////////
// IMPORTS //
/////////////
var characterRoutes = {},
	Player = require('../models/player'),
	Character = require('../models/character'),
	Script = require('../models/script'),
	validator = require('validator');

var levelup_table = [100];
for (i=2; i <= 15; i++){
	levelup_table.push(levelup_table[0] * i);
}

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

			return Character.find({'_owner': id}, '_id name', function(err, character) {
				if(err)
					return res.send(err);

				if(!character || character.length === 0)
					return res.json({err: 'user_has_no_characters'});

				return res.json({msg: 'character_list', list: character});
			});
		});
	};

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
												  'level': 1,
												  'exp': 0,
												  'hp': 100,
												  'createdOn': new Date()});

				return Script.findOne({'_owner': player._id, 'isDefault': true}, function(err, script) {
					if(err)
						return res.send(err);

					if(!script)
						return res.json({err: 'script_does_not_exist'});

					newCharacter.script = script._id;

					return newCharacter.save(function(err) {
						if(err)
							return res.send(err);

						return res.json({msg: 'character_created'});
					});
				})
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

				return res.json({msg: 'character_info', 'character': {'name': character.name,
																	  'level': character.level,
																	  'exp': character.exp,
																	  'hp': character.hp,
																	  'script': character.script}});
			});
		});
	};

	characterRoutes.levelup = function(req, res) {
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

				character.exp += 100;
				if (character.exp >= levelup_table[character.level - 1]) {
					character.level++;
					character.hp += 20;
					character.exp = 0;

					return character.save(function(err) {
						if(err)
							return res.send(err);

						return res.json({msg: 'character_leveled_up'});
					});
				};

				return character.save(function(err) {
					if(err)
						return res.send(err);

					return res.json({msg: 'character_gained_exp'});
				});
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