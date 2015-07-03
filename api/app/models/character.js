var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var CharacterSchema = new Schema({
	_owner: {type: Schema.Types.ObjectId, ref: 'Player'},
	name: String,
	createdOn: Date
});

module.exports = mongoose.model('Character', CharacterSchema);