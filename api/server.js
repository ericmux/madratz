// Requires
var express    = require('express');
var app        = express();
var database   = require('./app/services/database.js');
var simulation = require('./app/services/simulation.js');
var bodyParser = require('body-parser');


// Global Definitions
var port = process.env.PORT || 8080;

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

database.connect(onConnectedToDatabase);

function onConnectedToDatabase() {
	simulation.connect(onConnectedToSimulationServer);
};

function onConnectedToSimulationServer() {
	// Routing Requests
	var router = express.Router();

	router.use(function(req, res, next) {
		console.log("Routing...");
		return next();
	});

	router.get('/', function(req, res) {
	    return res.json({ message: 'hello world!' });
	});

	// Match handling routing
	var matchRoutes = require('./app/routes/match');
	matchRoutes.init(simulation.getClient(), database.getGridFs());

	router.route('/creategame')
		.post(matchRoutes.create(simulation.getParams()));

	// Script Calls routing
	var scriptRoutes = require('./app/routes/script');

	router.route('/player/:player_id/script')
		.post(scriptRoutes.create)
		.get(scriptRoutes.list);

	router.route('/player/:player_id/script/:script_id')
		.get(scriptRoutes.read)
		.put(scriptRoutes.update)
		.delete(scriptRoutes.delete);

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
};
