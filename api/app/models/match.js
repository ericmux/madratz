var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var MatchSchema = new Schema({
	_creator: {type: Schema.Types.ObjectId, ref: 'Player'},
	creatorScriptName: String,
	_enemy: {type: Schema.Types.ObjectId, ref: 'Player'},
	enemyScriptName: String,
	_file: {type: Schema.Types.ObjectId, ref: 'File'},
	date: Date
});

module.exports = mongoose.model('Match', MatchSchema);