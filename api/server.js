// Requires
var express    = require('express');
var app        = express();
var mongoose   = require('mongoose');
var bodyParser = require('body-parser');

// Config
var dbConfig   = require('./app/dbconfig');

// Models
var Player = require('./app/models/player');
var Script = require('./app/models/script');

// Global Definitions
var port = process.env.PORT || 8080;

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// Database
mongoose.connect(dbConfig.getURL());

// Routing Requests
var router = express.Router();

router.use(function(req, res, next) {
	console.log("Routing...");
	return next();
});

router.get('/', function(req, res) {
    return res.json({ message: 'hello world!' });
});

router.route('/player/:player_id/script')
	.post(function(req, res) {

		Player.findById(req.params.player_id, function(err, player) {
			if(err)
				return res.send(err);

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
	})
	.get(function(req, res) {
		Player.findById(req.params.player_id, function(err, player) {
			if(err)
				return res.send(err);
			Script.find({'_creator': req.params.player_id}, function(err, script) {
				if(err)
					return res.send(err);
				return res.json(script);
			});
		});
	});

router.route('/player/:player_id/script/:script_id')
	.get(function(req, res) {
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
	})
	.put(function(req, res) {
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
	})
	.delete(function(req, res) {
		Script.remove({ '_id': req.params.script_id }, function(err, script) {
			if(err)
				return res.send();

			return res.json({ message: 'Script succesfully deleted'});
		});
	});

// Player routing
var playerRoutes = require('./app/routes/player');

router.route('/player').post(playerRoutes.create)
					   .get(playerRoutes.list);

router.route('/player/:player_id').get(playerRoutes.read)
								  .put(playerRoutes.update)
								  .delete(playerRoutes.delete);

// API pre-routing
app.use('/api', router);

// Run application
app.listen(port);
console.log('Madratz API listening at port ' + port + '.');