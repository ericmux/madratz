var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var ScriptSchema = new Schema({
	_creator: {type: Schema.Types.ObjectId, ref: 'Player'},
	title: String,
	code: String,
	date: Date
});

module.exports = mongoose.model('Script', ScriptSchema);