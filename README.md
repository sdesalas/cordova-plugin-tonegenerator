![Apache Cordova Tone Generator Plugin](https://raw.githubusercontent.com/sdesalas/cordova-plugin-tonegenerator/master/image.jpg)

An arbitrary tone generation plugin for cordova apps.

It generates raw tones at specific frequencies and amplitudes, this can be used to produce an interactive pitch response to various user actions, or to generate an electrical waveform through the headphone output jack to drive low-voltage AC electrical motors.

See [this page](https://github.com/sdesalas/cordova-magnetometer-app/blob/master/www/index.html) for a basic sample cordova app using this plugin. 

Installation
------------

<code> cordova plugin add https://github.com/sdesalas/cordova-plugin-tonegenerator </code>


Methods
-------
- cordova.plugins.tonegenerator.play
- cordova.plugins.tonegenerator.frequency
- cordova.plugins.tonegenerator.volume
- cordova.plugins.tonegenerator.stop


cordova.plugins.tonegenerator.play()
-------------------------------------------

Starts playing a tone. 

<pre>
<code>
  cordova.plugins.tonegenerator.play(frequency, amplitude, waveType)
</code>
</pre>

Default values as follows: 

- frequency: 440 (hz)
- volume: 255 (max)
- waveType: 0 (sine)

cordova.plugins.magnetometer.frequency()
-------------------------------------------

Sets the frequency of the generated tone (in Hertz). The human audible range is 60hz to 16,000hz.

This method can be used to update the frequency while a tone is playing.

<pre>
<code>
  cordova.plugins.magnetometer.frequency(hertz)
</code>
</pre>


cordova.plugins.magnetometer.volume()
-------------------------------------------

Sets the volume (amplitude) of the generated tone. This should be a value between 0 and 255.

This method can be used to update the frequency while a tone is playing.

<pre>
<code>
  cordova.plugins.magnetometer.volume(volume)
</code>
</pre>


cordova.plugins.magnetometer.stop()
--------------------------------

Stops playing the generated tone.

<pre>
<code>
  cordova.plugins.magnetometer.stop()
</code>
</pre>

Supported Platforms
-------------------

- iOS (TODO)
- Android 

