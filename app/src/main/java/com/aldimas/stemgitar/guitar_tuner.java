package com.aldimas.stemgitar;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;


public class guitar_tuner extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.guitar_tuner);

        //Deklarasi input mikrofon
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050,1024,0);
        final TextView possibleHertz = findViewById(R.id.possibleHertzGuitar);
        final TextView possibleString = findViewById(R.id.possibleStringGuitar);
        final TextView suggestion = findViewById(R.id.suggestionGuitar);

        //Mengambil package TarsosDSP utk ambil sound input dan konversi ke hertz
        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult inputSound, AudioEvent e) {
                final int inputHertz = (int) inputSound.getPitch();
                if (inputHertz != -1) {
                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            possibleHertz.setText("" + inputHertz);
                            possibleString.setText("" + checkHertz(inputHertz, 'g'));
                            suggestion.setText("" + suggestion(checkHertz(inputHertz, 'g'), inputHertz, 'g'));
                        }
                    });
                }
            }
        };

        AudioProcessor p = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(p);
        new Thread(dispatcher).start();
    }
}

