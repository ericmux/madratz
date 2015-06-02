/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
var thrift = require('thrift');
    tprotocol = require('./node_modules/thrift/lib/thrift/protocol.js');

var SimulationServices = require('./thrift/SimulationService.js'),
    ttypes = require('./thrift/simulation_service_types');

var connection = thrift.createConnection('54.207.1.36', 1199, {protocol: tprotocol.TCompactProtocol}),
    client = thrift.createClient(SimulationServices, connection);

var mp = new ttypes.MatchParams({players: [new ttypes.Player({id: 1, script: 'lala'}), new ttypes.Player({id: 2, script: 'lolo'})]}); //

connection.on('error', function(err) {
  console.error(err);
});

var matchId;

client.startMatch(mp, function(err, result) {
  if (err) {
    console.error(err);
  } else {
    matchId = result;
    console.log("match stored " + result);
  }
});

var check = setInterval(function() {
  client.isMatchFinished(matchId, function(err, finished) {
    if (err) {
      console.error(err);
    } else {
      console.log("match finished? " + finished);
      if(finished == true)
      {
        clearInterval(check);
        client.result(matchId, function(err, result) {
          if(err) {
            console.error(err);
          }  else {
            console.log('match result: ' + result.winnerId);
            setTimeout(function() { process.exit(0); }, 100)
          }
        });
      }
    }
  });
}, 1000);