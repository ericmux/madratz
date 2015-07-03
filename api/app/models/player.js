var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var PlayerSchema = new Schema({
	name: String,
	username: String,
	password: String,
	createdOn: Date,
	lastLogin: Date,
});

module.exports = mongoose.model('Player', PlayerSchema);