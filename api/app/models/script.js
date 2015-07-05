var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var ScriptSchema = new Schema({
	_owner: {type: Schema.Types.ObjectId, ref: 'Player'},
	title: String,
	code: String,
	createdOn: Date,
	isDefault: Boolean,
	lastUpdated: Date,
});

module.exports = mongoose.model('Script', ScriptSchema);