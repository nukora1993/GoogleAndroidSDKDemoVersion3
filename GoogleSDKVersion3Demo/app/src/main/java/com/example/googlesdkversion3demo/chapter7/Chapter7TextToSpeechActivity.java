package com.example.googlesdkversion3demo.chapter7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.googlesdkversion3demo.R;

import java.util.Locale;

public class Chapter7TextToSpeechActivity extends AppCompatActivity {
    private TextToSpeech tts;
    private EditText editText;
    private Button button;

    //Utterance是语言的意思
    private TextToSpeech.OnUtteranceCompletedListener onUtteranceCompletedListener=new TextToSpeech.OnUtteranceCompletedListener() {
        @Override
        public void onUtteranceCompleted(String utteranceId) {

        }
    };

    private TextToSpeech.OnInitListener ttsInitListener=new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            Locale locale=new Locale("zho","TWN","");
            if(tts.isLanguageAvailable(locale)==TextToSpeech.LANG_AVAILABLE){
                tts.setLanguage(locale);
            }

            tts.setOnUtteranceCompletedListener(onUtteranceCompletedListener);
        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter7_text_to_speech);
        tts=new TextToSpeech(this,ttsInitListener);

        editText=findViewById(R.id.edit_text);
        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().length()>0){
                    tts.speak(editText.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
                }else {
                    tts.speak("没啥可说的",TextToSpeech.QUEUE_FLUSH,null);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        tts.shutdown();
        super.onDestroy();
    }
}
