/**
*   ToneGenerator.java
*
*   A Java Class for the Cordova Tone Generator Plugin
*
*   @by Steven de Salas (desalasworks.com | github/sdesalas)
*   @licence MIT
*
*   @see https://github.com/sdesalas/cordova-plugin-tonegenerator
*   @see https://audioprograming.wordpress.com/2012/10/18/a-simple-synth-in-android-step-by-step-guide-using-the-java-sdk/
*   @see http://stackoverflow.com/questions/2413426/playing-an-arbitrary-tone-with-android
*   @see http://stackoverflow.com/questions/25684821/how-create-a-50hz-square-wave-on-android-and-play-it
*/

package org.apache.cordova.tonegenerator;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import java.lang.Thread;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;


public class ToneGenerator extends CordovaPlugin  {

    public static int STOPPED = 0;
    public static int RUNNING = 1;

    boolean isRunning = false; 
    Thread t;
    int sampleRate = 8000;
    int frequency;
    int volume;
    int waveType;

    public ToneGenerator() {

    }

    public void onDestroy() {
        this.stop();
    }

    public void onReset() {
        this.stop();
    }

    //--------------------------------------------------------------------------
    // Cordova Plugin Methods
    //--------------------------------------------------------------------------

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("play")) {
            this.frequency = args.getInt(0);
            this.volume = args.getInt(1);
            this.waveType = args.getInt(2);
            this.play();
        }
        else if (action.equals("stop")) {
            this.stop();
        } 
        else if (action.equals("frequency")) {
            this.frequency = args.getInt(0);
        }
        else if (action.equals("volume")) {
            this.volume = args.getInt(0);
        } else {
            // Unsupported action
            return false;
        }
        return true;
    }

    //--------------------------------------------------------------------------
    // Local Methods
    //--------------------------------------------------------------------------

    /**
     * Generates a tone at desired frequency and volume.
     *
     * @return  1 if successful, 0 if not
     */
    public int play() {

        try {
            isRunning = true;
            t = new Thread() {
                public void run() {
                    // set process priority
                    setPriority(Thread.MAX_PRIORITY);
                    int buffsize = AudioTrack.getMinBufferSize(sampleRate,
                            AudioFormat.CHANNEL_OUT_MONO,
                            AudioFormat.ENCODING_PCM_16BIT);
                    // create an audiotrack object
                    AudioTrack audioTrack = new AudioTrack(
                            AudioManager.STREAM_MUSIC, sampleRate,
                            AudioFormat.CHANNEL_OUT_MONO,
                            AudioFormat.ENCODING_PCM_16BIT, buffsize,
                            AudioTrack.MODE_STREAM);

                    short samples[] = new short[buffsize];
                    double twopi = 8. * Math.atan(1.);
                    double ph = 0.0;
                    // start audio
                    audioTrack.play();

                    while (isRunning) {
                        int amp = volume * 128;
                        double fr = frequency * 1.0;
                        for (int i = 0; i < buffsize; i++) {
                            samples[i] = (short) (amp * Math.sin(ph));
                            ph += twopi * fr / sampleRate;
                        }
                        audioTrack.write(samples, 0, buffsize);
                    }
                    audioTrack.stop();
                    audioTrack.release();
                }
            };
            t.start();

        }

        // If error notify consumer
        catch (Exception ex) {
            return 0;
        }

        return 1;
    }

    /**
     * Stop tone.
     */
    public void stop() {
        isRunning = false;
        try {
            t.join();
        } catch (Exception ex) {}
        t = null;
    }

}
