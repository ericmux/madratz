var simulation = {};
var thrift 	   = require('thrift');
var config     = require("../config.js");

var SimulationServices = require('../../thrift/SimulationService.js');
var ttypes             = require('../../thrift/simulation_service_types');

(function(simulation) {
	var _connection;
	var _client;
	var _info;

	simulation.connect = function(callback) {
		_info = config.getSimulationServerInfo();
		_connection = thrift.createConnection(_info.ip, _info.port, _info.config);
		_client = thrift.createClient(SimulationServices, _connection);

		_connection.on('connect', onConnected(callback));
		_connection.on('error', onError());
	};

	simulation.getParams = function() {
		return new ttypes.MatchParams({'players': [	new ttypes.PlayerInfo({'id': 1, 'script': "import math\nimport random\n\ntarget_id = None\nt = 0\n\ndef execute(sensor, actor):\n    global target_id, t\n    ops = sensor.opponents()\n    target = None\n    if target_id is not None:\n        targets = filter(lambda p: p.id == target_id, ops)\n        target = targets[0] if len(targets) &gt; 0 else None\n        if target != None and target.hp == 0:\n            target = None\n    if target is None:\n        target = ops[random.randint(0, len(ops) - 1)]\n        target_id = target.id\n\n    me = sensor.self()\n    op_angle = math.atan2(target.position.y - me.position.y, target.position.x - me.position.x)\n    curr_angle = math.atan2(me.direction.y, me.direction.x)\n\n    actor.rotate(5 * (op_angle - curr_angle))\n    actor.set_velocity(1.0)\n\n    t += 1\n    if t % 10 == 0:\n        actor.shoot()\n        t = 0\n"}),
												new ttypes.PlayerInfo({'id': 2, 'script': "t = 0\n\ndef execute(sensor, actor):\n    global t\n    if t == 10:\n        actor.shoot()\n        t = 0\n    actor.set_velocity(1.0)\n    actor.rotate(0.1)\n    t += 1\n"})]
											});
	};

	simulation.getClient = function() {
		return _client;
	};

	simulation.verifyScript = function(script, callback) {
		console.log('Verifying script...');
		return _client.compileScript(script, function(err, result) {
		  	if (err) {
		  		return callback(err);
		  	}
		  	if(result.success === true)
		  	{
		  		return callback({msg: 'success'});
		  	}
		  	return callback({err: 'script_error', err_type: result.errorType, err_msg: result.errorMsg});
		});
	};

	function onConnected(callback) {
		return function() {
			console.log("Connected to simulation server at: " + _info.ip + ":" + _info.port + ".");
			return callback();
		};
	};

	function onError() {
		return function() {
			console.log('Error when connecting to: ' + _info.ip + ":" + _info.port + ".");
			console.log('Finishing process.');
			return process.exit(1);
		};
	};
}(simulation));

module.exports = simulation;