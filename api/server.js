// Requires
var express    = require('express');
var app        = express();
var mongoose   = require('mongoose');
var bodyParser = require('body-parser');

// Config
var dbConfig   = require('./app/dbconfig');

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

var playerRoutes = require('./app/routes/player')

// Player routing
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