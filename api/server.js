var express    = require('express');
var app        = express();

var port = process.env.PORT || 8080;

var router = express.Router();

router.get('/', function(req, res) {
    res.json({ message: 'hello world!' });   
});

app.use('/', router);

app.listen(port);
console.log('Madratz API listening at port ' + port + '.');