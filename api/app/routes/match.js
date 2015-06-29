/////////////
// IMPORTS //
/////////////
var matchRoutes = {};

(function (matchRoutes) {
	var _client;
	var _gridfs;

	matchRoutes.init = function(client, gridfs) {
		_client = client;
		_gridfs = gridfs;
	};

	matchRoutes.create = function(params) {
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

														writestream.write(JSON.stringify(snapshotList));
														writestream.end();

														writestream.on('close', function(file) {
															console.log("Finished writing " + file.filename + " to database.");
															console.log(JSON.stringify(file));
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
}(matchRoutes));

module.exports = matchRoutes;