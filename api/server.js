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

	router.route('/player/:player_id/match/create/onevsone')
		.post(matchRoutes.createOnevsOne);
	router.route('/player/:player_id/match/createold')
		.post(matchRoutes.createold(simulation.getParams()));
	router.route('/player/:player_id/match/list')
		.get(matchRoutes.list);
	// Player routing
	var playerRoutes = require('./app/routes/player');

	router.route('/player').get(playerRoutes.list);

	router.route('/login').post(playerRoutes.login);

	router.route('/register').post(playerRoutes.register);

	router.route('/player/:player_id').get(playerRoutes.read)
									  .put(playerRoutes.update)
									  .delete(playerRoutes.delete);

	// Script Calls routing
	var scriptRoutes = require('./app/routes/script');

	router.route('/player/:player_id/script/list').get(scriptRoutes.list);
	router.route('/player/:player_id/script/create').post(scriptRoutes.create(simulation));
	router.route('/player/:player_id/script/verify').post(scriptRoutes.verify(simulation));

	router.route('/player/:player_id/script/:script_id/info').get(scriptRoutes.read);
	router.route('/player/:player_id/script/:script_id/update').post(scriptRoutes.update(simulation));
	router.route('/player/:player_id/script/:script_id/setDefault').get(scriptRoutes.setDefault(simulation));
	router.route('/player/:player_id/script/:script_id/delete').get(scriptRoutes.delete);

    // Character routing
    var characterRoutes = require('./app/routes/character');

    router.route('/player/:player_id/character/list').get(characterRoutes.list);
	router.route('/player/:player_id/random/:num_chars').get(characterRoutes.list_random);

    router.route('/player/:player_id/character/create').post(characterRoutes.create);

    router.route('/player/:player_id/character/:char_id/info').get(characterRoutes.read);
    router.route('/player/:player_id/character/:char_id/script/:script_id/changeScript').get(characterRoutes.changeScript);
    router.route('/player/:player_id/character/:char_id/levelup').get(characterRoutes.levelup);
    router.route('/player/:player_id/character/:char_id/delete').get(characterRoutes.delete);


	// API pre-routing
	app.use('/api', router);

	// Run application
	app.listen(port);
	console.log('Madratz API listening on port ' + port + '.');
};
