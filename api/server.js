/*
 * server.js
 */


// Requires
var express    = require('express');
var app        = express();
var mongoose   = require('mongoose');
var bodyParser = require('body-parser');
var thrift 	   = require('thrift');

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

// Thrift
var SimulationServices = require('./thrift/SimulationService.js');
var ttypes             = require('./thrift/simulation_service_types');

var connection         = thrift.createConnection('54.207.1.36', 1199, {protocol: thrift.TCompactProtocol});
var client             = thrift.createClient(SimulationServices, connection);

var mp = new ttypes.MatchParams({'players': [new ttypes.Player({'id': 1, 'script': 'lala'}), new ttypes.Player({'id': 2, 'script': 'lolo'})]});

var matchId;
var check;

connection.on('error', function(err) {
  console.error(err);
});
// Routing Requests
var router = express.Router();

router.use(function(req, res, next) {
	console.log("Routing...");
	return next();
});

router.get('/', function(req, res) {
    return res.json({ message: 'hello world!' });
});

var check;
router.route('/creategame')
	.post(function(req, res) {
		console.log('Creating new game...');

		client.startMatch(mp, function(err, result) {
		  	if (err) {
		  		console.log(err);
		    	return res.json({error: err});
		  	} else {
		    	matchId = result;
		    	console.log("Match created #" + result);
		    	check = setInterval(function() {
				  client.isMatchFinished(matchId, function(err, finished) {
				    if (err) {
				      console.error(err);
				    } else {
				      console.log("Verifying match status: " + finished);
				      if(finished == true)
				      {
				        clearInterval(check);
				        setTimeout(function() {
				        	client.result(matchId, function(err, result) {
					          if(err) {
					            console.error(err);
					          }  else {
					            console.log('Match #' + matchId + ' winnerId #' + result.winnerId);
					          }
				        	});
				        }, 1000);
				      }
				    }
				  });
				}, 1000);
		  	}
		});
		return res.json({message: 'Game created!'});
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

router.route('/login/:player_name').get(playerRoutes.login);

router.route('/player/:player_id').get(playerRoutes.read)
								  .put(playerRoutes.update)
								  .delete(playerRoutes.delete);

// API pre-routing
app.use('/api', router);

// Run application
app.listen(port);
console.log('Madratz API listening on port ' + port + '.');

var thrift = require('thrift');

var UserStorage = require('./thrift/UserStorage.js'),
    ttypes = require('./thrift/user_types');

var users = {};

var server = thrift.createServer(UserStorage, {
	store: function(user, result) {
		console.log("server stored:", user.uid);
		users[user.uid] = user;
		result(null);
	},

	retrieve: function(uid, result) {	
		console.log("server retrieved:", uid);
		result(null, users[uid]);
	},
});

server.listen(9090);
console.log("Thrift Server listening on 9090");
