var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var MatchSchema = new Schema({
	_creator: {type: Schema.Types.ObjectId, ref: 'Player'},
	_character: {type: Schema.Types.ObjectId, ref: 'Character'},
	characterScriptName: String,
	_enemies: [{type: Schema.Types.ObjectId, ref: 'Character'}],
	enemyScriptName: String,
	_file: {type: Schema.Types.ObjectId, ref: 'File'},
	status: String,
	_winner: {type: Schema.Types.ObjectId, ref: 'Character'},
	date: Date,
	type: String
});

module.exports = mongoose.model('Match', MatchSchema);