var DNode = require('dnode');

var client = DNode.connect(6060, function (remote) {
  remote['moo'](function (x) {
    console.log(x);
  });
});