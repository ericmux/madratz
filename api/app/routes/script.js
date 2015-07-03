/////////////
// IMPORTS //
/////////////
var scriptRoutes = {},
	Player = require('../models/player'),
	Script = require('../models/script'),
	validator = require('validator');

(function(scriptRoutes) {

	/////////////
	// ROUTING //
	/////////////

	scriptRoutes.list = function(req, res) {
		var id = req.params.player_id;
		var idErr = sanitizeId(id)
		if(idErr)
			return res.json(idErr);

		return Player.findById(id, function(err, player) {
			if(err)
				return res.send(err);

			if(!player)
				return res.json({err: "user_does_not_exist"});

			return Script.find({'_owner': id}, '_id title code date', function(err, script) {
				if(err)
					return res.send(err);

				if(!script || script.length === 0)
					return res.json({err: 'user_has_no_scripts'});

				return res.json({msg: 'script_list', 'list': script});
			});
		});
	};

	scriptRoutes.create = function(simulationService)
	{
		return function(req, res) {
			var id = req.params.player_id;
			var idErr = sanitizeId(id)
			if(idErr)
				return res.json(idErr);

			var title = req.body.title;
			var titleErr = sanitizeTitle(title);
			if(titleErr)
				return res.json(titleErr);

			var code = req.body.code;
			var codeErr = sanitizeCode(code);
			if(codeErr)
				return res.json(codeErr);

			return Player.findById(id, function(err, player) {
				if(err)
					return res.send(err);

				if(!player)
					return res.json({err: "user_does_not_exist"});

				var actualDate = new Date();
				var newScript = new Script({_owner: id,
					title: title,
					code: code,
					createdOn: actualDate,
					lastUpdate: actualDate,
				});

				var temp = new Buffer(code, 'base64').toString('utf8')
				console.log(temp);
				simulationService.verifyScript(temp);

				return newScript.save(function(err) {
					if(err)
						return res.send(err);

					return res.json({msg: 'script_created'});
				});
			});
		};
	};

	scriptRoutes.read = function(req, res) {
		var playerId = req.params.player_id;
		var idErr = sanitizeId(playerId, 'player')
		if(idErr)
			return res.json(idErr);

		var scriptId = req.params.script_id;
		var idErr = sanitizeId(scriptId, 'script')
		if(idErr)
			return res.json(idErr);

		return Player.findById(playerId, function(err, player) {
			if(err)
				return res.send(err);

			if(!player)
				return res.json({err: "user_does_not_exist"});

			return Script.findById(scriptId, function(err, script) {
				if(err)
					return res.send(err);

				if(!script || (script._owner != playerId))
					return res.json({err: 'script_does_not_exist'});

				return res.json({msg: 'script_info', 'script': script});
			});
		});
	};

	scriptRoutes.update = function(simulationService) {
		return function(req, res) {
			var playerId = req.params.player_id;
			var idErr = sanitizeId(playerId, 'player')
			if(idErr)
				return res.json(idErr);

			var scriptId = req.params.script_id;
			var idErr = sanitizeId(scriptId, 'script')
			if(idErr)
				return res.json(idErr);

			var title = req.body.title;
			var titleErr = sanitizeTitle(title);
			if(titleErr)
				return res.json(titleErr);

			var code = req.body.code;
			var codeErr = sanitizeCode(code);
			if(codeErr)
				return res.json(codeErr);

			return Player.findById(playerId, function(err, player) {
				if(err)
					return res.send(err);

				if(!player)
					return res.json({err: "user_does_not_exist"});

				return Script.findById(scriptId, function(err, script) {
					if(err)
						return res.send(err);

					if(!script || (script._owner != playerId))
						return res.json({err: 'script_does_not_exist'});

					script.title = title;
					script.code = code;
					var temp = new Buffer(code, 'base64').toString('utf8')
					console.log(temp);
					simulationService.verifyScript(temp);
					script.lastUpdate = new Date();

					return script.save(function(err) {
						if(err)
							return res.send(err);

						return res.json({msg: 'script_updated'});
					});
				});
			});
		};
	};


	scriptRoutes.delete = function(req, res) {
		var playerId = req.params.player_id;
		var idErr = sanitizeId(playerId, 'player')
		if(idErr)
			return res.json(idErr);

		var scriptId = req.params.script_id;
		var idErr = sanitizeId(scriptId, 'script')
		if(idErr)
			return res.json(idErr);

		return Player.findById(playerId, function(err, player) {
			if(err)
				return res.send(err);

			if(!player)
				return res.json({err: "user_does_not_exist"});

			return Script.findById(scriptId, function(err, script) {
				if(err)
					return res.send(err);

				if(!script || (script._owner != playerId))
					return res.json({err: 'script_does_not_exist'});

				return Script.remove({ '_id': scriptId }, function(err) {
					if(err)
						return res.send();

					return res.json({msg: 'script_deleted'});
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
		else
			type = type + '_';

		if(typeof id === "undefined")
			return { err: type + "id_undefined" };

		if(!validator.isMongoId(id))
			return { err: type + "id_invalid" };
	};

	function sanitizeTitle(title) {
		if(typeof title === "undefined")
			return { err: "title_undefined" };

		if(title.length < 3)
			return { err: "title_too_short" };

	    if(!validator.isAlphanumeric(title))
	        return { err: "title_not_alphanumeric" };
	};

	function sanitizeCode(code) {
		if(typeof code === "undefined")
			return { err: "code_undefined" };
	};
}(scriptRoutes));

module.exports = scriptRoutes;