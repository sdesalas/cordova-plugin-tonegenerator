var argscheck = require('cordova/argscheck'),
    utils = require('cordova/utils'),
    exec = require('cordova/exec'),
    timers = {};

var ToneGenerator = function(){};

ToneGenerator.prototype = {
  play: function(frequency, volume, waveType) {
    frequency = frequency || 440;
    volume = volume || 255;
    if (volume > 255) volume = 255;
    if (volume < 0) volume = 0;
    waveType = waveType || 1;
    return cordova.exec(function() {
      console.log('Generating tone at ' + frequency + 'Hz..');
    }, function() {
      throw "Error generating tone!";
    }, "ToneGenerator", "play", [frequency, volume, waveType]);
  },
  frequency: function(hz) {
    return cordova.exec(function() {}, function() {throw "Error updating tone"}, "ToneGenerator", "frequency", [hz || 0]);
  },
  volume: function(vol) {
    if (volume > 255) volume = 255;
    if (volume < 0) volume = 0;
    return cordova.exec(function() {}, function() {throw "Error updating tone"}, "ToneGenerator", "volume", [vol || 0]);
  },
  stop: function() {
    return cordova.exec(function() {}, function() {throw "Error stopping tone"}, "ToneGenerator", "stop", []);
  }
};

module.exports = new ToneGenerator();
