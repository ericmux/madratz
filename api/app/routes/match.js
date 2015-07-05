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
	fs = require('fs');

(function (matchRoutes) {
	var _client;
	var _gridfs;

	matchRoutes.init = function(client, gridfs) {
		_client = client;
		_gridfs = gridfs;
	};

	matchRoutes.create = function(req, res) {
		var playerId = req.params.player_id;
		var idErr = sanitizeId(playerId, 'player');
		if(idErr)
			return res.json(idErr);

		var charId = req.body.character;
		var idErr = sanitizeId(charId, 'character');
		if(idErr)
			return res.json(idErr);

		var scriptId = req.body.script;
		var idErr = sanitizeId(scriptId, 'script');
		if(idErr)
			return res.json(idErr);

		var enemyId = req.body.enemy;
		var idErr = sanitizeId(enemyId, 'script');
		if(idErr)
			return res.json(idErr);

		var enemyScriptId = req.body.enemy_script;
		var idErr = sanitizeId(enemyScriptId, 'script');
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

				return Character.findById(enemyId, function(err, enemy) {
					if(err)
						return res.send(err);

					if(!enemy)
						return res.json({err: "enemy_character_does_not_exist"});

					return Script.findById(scriptId, function(err, script) {
						if(err)
							return res.send(err);

						if(!script)
							return res.json({err: "script_does_not_exist"});

						return Script.findById(enemyScriptId, function(err, enemyScript) {
							if(err)
								return res.send(err);

							if(!enemyScript || (enemy._owner.toString() !== enemyScript._owner.toString()))
								return res.json({err: "enemy_script_does_not_exist"});

							console.log('Creating new game...');
							createMatch(character, script, enemy, enemyScript);
							return res.json({msg: 'match_created', obs: 'Created by ' + player.username + '. ' + character.name + '(' + script.title + ') vs ' + enemy.name +'(' + enemyScript.title + ')'});
						});
					});
				});
			});
		});
	};

	matchRoutes.createold = function(params) {
		return function(req, res) {
			console.log('Creating new game...');
			console.log(params);
			_client.startMatch(params, function(err, result) {
			  	if (err) {
			  		console.log(err);
			    	return res.json({error: err});
			  	}

		    	console.log("Match created #" + result);
		    	var checkFunction = setInterval(function() {
				  	_client.isMatchFinished(result, function(matchId) {
				  		return function(err, finished) {
					  		console.log("Verifying match status...");
						    if (err) {
						      	console.error(err);
						    } else {
						      	if(finished == true)
						      	{
						        	clearInterval(checkFunction);
						        	setTimeout(function() {
						        		_client.result(matchId, function(err, result) {
							          		if(err) {
							            		console.error(err);
							          		} else {
							            		console.log('Match #' + matchId + ' winnerId #' + result.winnerId);
							            		_client.snapshots(matchId, function(err, snapshotList) {
									          		if(err) {
									            		console.error(err);
									          		} else {
									          			if(!_gridfs)
									          				return console.log("Grid not available.");
														var writestream = _gridfs.createWriteStream({
														    filename: 'match' + matchId + '.txt'
														});
														writestream.on('open', function(file) {
															console.log("Stream opened");
														});
														var data = JSON.stringify(snapshotList);
														writestream.write(data);
														writestream.end();

														writestream.on('close', function(file) {
															console.log("Finished writing " + file.filename + " to database.");
															console.log(JSON.stringify(file));
														});

														fs.writeFile('match'+matchId+'.txt', data, function (err) {
															if (err)
																return console.log(err);
															console.log('finished');
														});
									          		}
								        		});
							          		}
						        		});
						        	}, 1000);
						      	}
						    }
						};
				  	}(result));
				}, 1000);
			});
			return res.json({message: 'Game created!'});
		};
	};

	///////////////////
	// HELPER METHOD //
	///////////////////

	function createMatch(character, characterScript, enemy, enemyScript)
	{
		var characterScriptCode = new Buffer(characterScript.code, 'base64').toString('utf8');
		console.log(characterScriptCode);
		var enemyScriptCode = new Buffer(enemyScript.code, 'base64').toString('utf8');
		console.log(enemyScriptCode);
		var params = new ttypes.MatchParams({'players': [
												new ttypes.PlayerInfo({'id': 1, 'script': characterScriptCode}),
												new ttypes.PlayerInfo({'id': 2, 'script': enemyScriptCode})
											]});

		return _client.startMatch(params, function(err, result) {
		  	if (err) {
		  		return console.log(err);
		  	}
		  	console.log("Match created #" + result);
		  	var checkFunction = setInterval(function() {
				  	_client.isMatchFinished(result, function(matchId) {
				  		return function(err, finished) {
					  		console.log("Verifying match status...");
						    if (err) {
						      	return console.error(err);
						    }

					      	if(finished == true)
					      	{
					        	clearInterval(checkFunction);
					        	setTimeout(function() {
					        		_client.result(matchId, function(err, result) {
						          		if(err) {
						            		return console.error(err);
						          		}
						          		console.log(result);
					            		console.log('Match #' + matchId + ' winnerId #' + result.winnerId);
					            		_client.snapshots(matchId, function(err, snapshotList) {
							          		if(err) {
							            		return console.error(err);
							          		}

						          			if(!_gridfs)
						          				return console.log("Grid not available.");
											var writestream = _gridfs.createWriteStream({
											    filename: 'match' + matchId + '.txt'
											});
											var data = JSON.stringify(snapshotList);
											writestream.on('open', function(file) {
												console.log("Stream opened");
											});

											writestream.write(data);
											writestream.end();

											writestream.on('close', function(file) {
												console.log("Finished writing " + file.filename + " to database.");
												console.log(JSON.stringify(file));

												var match = new Match({	_creator: character._id,
													creatorScriptName: characterScript.title,
													_enemy: enemy._id,
													enemyScriptName: enemyScript.title,
													_file: file._id,
													date: new Date()});

												return match.save(function(err) {
													if(err)
														return console.log(err);

													return console.log('Match entry created!');
												})
											});

											fs.writeFile('match'+matchId+'.txt', snapshotList, function (err) {
												if (err)
													return console.log(err);
												console.log('finished');
											});
						        		});
					        		});
					        	}, 1000);
					      	}
						};
				  	}(result));
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