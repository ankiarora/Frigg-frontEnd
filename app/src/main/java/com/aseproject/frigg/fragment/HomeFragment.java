package com.aseproject.frigg.fragment;

import static com.aseproject.frigg.activity.VoiceRecognitionActivity.RecordAudioRequestCode;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.aseproject.frigg.R;
import com.aseproject.frigg.activity.NewFoodItemActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private ImageView ivMicBtn;
    private Context context;
    private SpeechRecognizer speechRecognizer;
    private AppCompatEditText etFoodItem;
    private AppCompatEditText etFoodQuantity;
    private CheckBox cbFridgeList;
    private CheckBox cbGroceryList;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivMicBtn = view.findViewById(R.id.ivMicBtn);
        etFoodItem = view.findViewById(R.id.etFoodItem);
        etFoodQuantity = view.findViewById(R.id.etFoodQuantity);
        cbFridgeList = view.findViewById(R.id.cbFridgeList);
        cbGroceryList = view.findViewById(R.id.cbGroceryList);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);

        ivMicBtn.setOnClickListener(view1 -> {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                requestPermission();
            } else {
                setMicToText();
            }
        });
    }

    private void setMicToText() {
        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                Log.d("", "");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {
                ivMicBtn.setImageResource(R.drawable.mic_off_icon);
                Toast.makeText(context, "Please hold the mic and then speak clearly!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResults(Bundle bundle) {
                ivMicBtn.setImageResource(R.drawable.mic_off_icon);
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String spokenData = data.get(0);

                List<String> spokenDataList = new ArrayList<>(Arrays.asList(spokenData.split(" ")));
                List<String> formatList = new ArrayList<>(Arrays.asList(context.getString(R.string.add_item_format).split(" ")));
                if (spokenDataList.size() == 6 ) {
                    if (!formatList.get(0).equalsIgnoreCase(spokenDataList.get(0)) ||
                            !formatList.get(3).equalsIgnoreCase(spokenDataList.get(3))) {
                        Toast.makeText(context, "Please follow the correct format like mentioned...", Toast.LENGTH_LONG).show();
                    } else {
                        etFoodQuantity.setText(spokenDataList.get(1));
                        etFoodItem.setText(spokenDataList.get(2));
                        String list = spokenDataList.get(4)+" "+spokenDataList.get(5);
                        if(list.equals(context.getString(R.string.grocery_title))) {
                            cbGroceryList.setChecked(true);
                        } else if(list.equals(context.getString(R.string.fridge_title))){
                            cbFridgeList.setChecked(true);
                        }
                    }
                } else {
                    Toast.makeText(context, "Please follow the correct format like mentioned...", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        ivMicBtn.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                MediaPlayer song = MediaPlayer.create(context, R.raw.recording_end);
                song.start();
                speechRecognizer.stopListening();
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                MediaPlayer song = MediaPlayer.create(context, R.raw.recording_start);
                song.start();
                ivMicBtn.setImageResource(R.drawable.mic_on_icon);
                speechRecognizer.startListening(speechRecognizerIntent);
            }
            return false;
        });
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions((NewFoodItemActivity) context, new String[]{Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
        } else {
            Toast.makeText(context, "Your android version does not support recording. Please add an item manually.", Toast.LENGTH_SHORT).show();
        }
    }
}