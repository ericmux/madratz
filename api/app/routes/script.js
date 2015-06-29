/////////////
// IMPORTS //
/////////////
var scriptRoutes = {},
	Player = require('../models/player'),
	Script = require('../models/script');

(function(scriptRoutes) {
	/////////////
	// ROUTING //
	/////////////

	scriptRoutes.list = function(req, res) {
		Player.findById(req.params.player_id, function(err, player) {
			if(err)
				return res.send(err);
			Script.find({'_creator': req.params.player_id}, function(err, script) {
				if(err)
					return res.send(err);
				return res.json(script);
			});
		});
	};

	scriptRoutes.create = function(req, res) {
		Player.findById(req.params.player_id, function(err, player) {
			if(err)
				return res.send(err);

			if(!player)
				return res.json({err: "Player not found."});

			var title = req.body.title;
			var code = req.body.code;

			if(typeof title === "undefined")
				return res.json({error: "Undefined title."});

			if(typeof code === "undefined")
				return res.json({error: "Undefined code."});

			var script = new Script({_creator: player._id,
				creatorName: player.name,
				title: title,
				code: code,
				date: new Date()
			});

			return script.save(function(err) {
				if(err)
					return res.send(err);

				return res.json({message: 'Script created: ' + title});
			});
		});
	};

	scriptRoutes.read = function(req, res) {
		Player.findById(req.params.player_id, function(err, player) {
			if(err)
				return res.send(err);

			Script.findOne({'_id': req.params.script_id}, function(err, script) {
			//Player.find({'scripts._id': req.params.script_id}, function(err, script) {
				if(err)
					return res.send(err);
				return res.json(script);
			});
		});
	};

	scriptRoutes.update = function(req, res) {
		Player.findById(req.params.player_id, function(err, player) {
			if(err)
				return res.send(err);

			Script.findOne({'_id': req.params.script_id}, function(err, script) {
				if(err)
					return res.send(err);

				script.title = req.body.title;
				script.code = req.body.code;
				script.date = new Date();

				return script.save(function(err) {
					if(err)
						return res.send(err);

					return res.json({message: "Script " + script.title + " updated."});
				});
			});
		});
	};

	scriptRoutes.delete = function(req, res) {
		Script.remove({ '_id': req.params.script_id }, function(err, script) {
			if(err)
				return res.send();

			return res.json({ message: 'Script succesfully deleted'});
		});
	};
}(scriptRoutes));

module.exports = scriptRoutes;