var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var RatSchema = new Schema({
	name: String,
	class: String
});

module.exports = mongoose.model('Rat', RatSchema);
