/////////////
// IMPORTS //
/////////////
var matchRoutes = {},
	Player = require('../models/player'),
	Character = require('../models/character'),
	Script = require('../models/script'),
	Match = require('../models/match'),
	validator = require('validator'),
	ttypes = require('../../thrift/simulation_service_types'),
	fs = require('fs'),
	util = require('util');

var thrift = require('thrift');

require('nodedump');

(function (matchRoutes) {
	var _client;
	var _gridfs;

	matchRoutes.init = function(client, gridfs) {
		_client = client;
		_gridfs = gridfs;
	};

	matchRoutes.list = function(req, res) {
		var playerId = req.params.player_id;
		var idErr = sanitizeId(playerId, 'player');
		if(idErr)
			return res.json(idErr);
		return Player.findById(playerId, function(err, player) {
			if(err)
				return res.send(err);

			if(!player)
				return res.json({err: "user_does_not_exist"});

			return Match.find({_creator: playerId}, function(err, matches) {
				if(err)
					return res.send(err);

				return res.json({msg: 'match_list', list: matches});
			})
		});
	};

	matchRoutes.getSnapshots = function(req, res) {
		var playerId = req.params.player_id;
		var idErr = sanitizeId(playerId, 'player');
		if(idErr)
			return res.json(idErr);

		var matchId = req.params.match_id;
		var idErr = sanitizeId(matchId, 'match');
		if(idErr)
			return res.json(idErr);

		return Player.findById(playerId, function(err, player) {
			if(err)
				return res.send(err);

			if(!player)
				return res.json({err: "user_does_not_exist"});

			return Match.findById(matchId, function(err, match) {
				if(err)
					return res.send(err);

				if(!match)
					return res.json({err: "match_does_not_exist"});

				if(match.status === 'finished')
				{
					if(!_gridfs)
						return console.log("Grid not available.");

					return _gridfs.exist({_id: match._file}, function (err, found) {
						if (err)
							return handleError(err);

					  	if(!found)
					  		return res.json({err: 'match_file_not_found'});

					  	return res.sendfile(matchId + '.out');
					  	/*return _gridfs.createReadStream({_id: match._file})
									  	.on('error', function() {
								            res.send(500, {err: 'failed_to_retrieve_file'});
								        })
								        .pipe(res);*/
					});
				}
				else
					return res.json({err: 'match_not_finished'});
			})
		});
	};

	matchRoutes.createOnevsOne = function(req, res) {
		var playerId = req.params.player_id;
		var idErr = sanitizeId(playerId, 'player');
		if(idErr)
			return res.json(idErr);

		var charId = req.body.character;
		var idErr = sanitizeId(charId, 'character');
		if(idErr)
			return res.json(idErr);

		var enemyId = req.body.enemy;
		var idErr = sanitizeId(enemyId, 'script');
		if(idErr)
			return res.json(idErr);

		return Player.findById(playerId, function(err, player) {
			if(err)
				return res.send(err);

			if(!player)
				return res.json({err: "user_does_not_exist"});

			return Character.findById(charId, function(err, character) {
				if(err)
					return res.send(err);

				if(!character || (character._owner != playerId))
					return res.json({err: "character_does_not_exist"});

				if(!character.script)
					return res.json({err: "character_no_active_script"});

				return Character.findById(enemyId, function(err, enemy) {
					if(err)
						return res.send(err);

					if(!enemy)
						return res.json({err: "enemy_character_does_not_exist"});

					if(!enemy.script)
						return res.json({err: "enemy_no_active_script"});

					return Script.findById(character.script, function(err, script) {
						if(err)
							return res.send(err);

						if(!script)
							return res.json({err: "character_script_does_not_exist"});

						return Script.findById(enemy.script, function(err, enemyScript) {
							if(err)
								return res.send(err);

							if(!enemyScript || (enemy._owner.toString() !== enemyScript._owner.toString()))
								return res.json({err: "enemy_script_does_not_exist"});

							var newMatch = new Match({
								_creator: playerId,
								_character: character,
								characterScriptName: script.name,
								_enemies: [enemy],
								enemyScriptName: enemyScript.name,
								status: 'created',
								date: new Date(),
								type: '1v1'
							});

							return newMatch.save(function(err, match) {
								if(err)
									return res.send(err);
								console.log('Creating new game...');
								createMatchOneToOne(match._id, character, script, enemy, enemyScript);
								res.json({msg: 'match_created', obs: 'Created by ' + player.username + '. ' + character.name + '(' + script.title + ') vs ' + enemy.name +'(' + enemyScript.title + ')'});
							});
						});
					});
				});
			});
		});
	};

	///////////////////
	// HELPER METHOD //
	///////////////////

	function createMatchOneToOne(matchId, character, characterScript, enemy, enemyScript)
	{
		var characterScriptCode = new Buffer(characterScript.code, 'base64').toString('utf8');
		var enemyScriptCode = new Buffer(enemyScript.code, 'base64').toString('utf8');
		var params = new ttypes.MatchParams({'matchId': matchId.toString(),
											'players': [
												new ttypes.PlayerInfo({'id': character._id.toString(), 'script': characterScriptCode}),
												new ttypes.PlayerInfo({'id': enemy._id.toString(), 'script': enemyScriptCode})
											]});

		return _client.startMatch(params, function(err) {
		  	if (err) {
		  		return console.log(err);
		  	}
		  	console.log("Match created #" + matchId.toString());
		  	var checkFunction = setInterval(function() {
				  	_client.isMatchFinished(matchId.toString(), function(id) {
				  		return function(err, finished) {
					  		console.log("Verifying match status...");
						    if (err) {
						      	return console.error(err);
						    }

					      	if(finished == true)
					      	{
					        	clearInterval(checkFunction);
					        	setTimeout(function() {
					        		return _client.result(id, function(err, result) {
						          		if(err) {
						            		return console.error(err);
						          		}
						          		console.log(result);
					            		console.log('Match #' + id + ' winnerId #' + result.winnerId);

					            		return Match.findOne({'_id': id}, function(err, match) {
					            			if(err) {
									            return console.error(err);
									        }

									        if(!match)
									        	return console.log('Match not found: ' + matchId);

				            				return _client.snapshots(id, function(err, snapshots) {
								          		if(err) {
								            		return console.error(err);
								          		}

							          			if(!_gridfs)
							          				return console.log("Grid not available.");

							          			var buffer = new Buffer(snapshots);
						            			var transport = new thrift.TFramedTransport(buffer, function(msg) {
							            			var writestream = _gridfs.createWriteStream({
													    'filename': id
													});
						            				writestream.write(msg);
													writestream.end();

													fs.writeFile(id + '.out', msg, function(err) {
													    if(err) {
													        return console.log(err);
													    }
													    console.log("The file was saved!");
													});

													return writestream.on('close', function(file) {
														console.log("Finished writing " + file.filename + " to database.");
														if(result.winnerId)
							            					match._winner = result.winnerId;

							            				match.status = 'finished';
							            				match._file = file._id;
							            				return match.save(function(err) {
						            						console.log('Match #' + id + ' updated.');
						            					});
													});
						            			});

						            			var protocol = new thrift.TCompactProtocol(transport);

						            			snapshots.write(protocol);

						            			return transport.flush();
							        		});
					            		});
					        		});
					        	}, 1000);
					      	}
						};
				  	}(matchId.toString()));
				}, 1000);
		});
	};

	/////////////////////
	// DATA VALIDATION //
	/////////////////////

	function sanitizeId(id, type) {
		if(!type)
			type = '';
		else
			type = type + '_';

		if(typeof id === "undefined")
			return { err: type + "id_undefined" };

		if(!validator.isMongoId(id))
			return { err: type + "id_invalid" };
	};
}(matchRoutes));

module.exports = matchRoutes;