var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var CharacterSchema = new Schema({
	_owner: {type: Schema.Types.ObjectId, ref: 'Player'},
	name: String,
	level: Number,
	exp: Number,
	hp: Number,
	isDefault: Boolean,
	script: {type: Schema.Types.ObjectId, ref: 'Script'},
	createdOn: Date
});

module.exports = mongoose.model('Character', CharacterSchema);