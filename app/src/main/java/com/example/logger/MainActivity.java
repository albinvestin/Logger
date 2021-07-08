package com.example.logger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private TextView outputLog;
    private StringList totalLog = new StringList();
    private ButtonHandler buttonHandler = new ButtonHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outputLog = (TextView) findViewById(R.id.logText);
        outputLog.setMovementMethod(new ScrollingMovementMethod());
        sharedPref = getPreferences(MODE_PRIVATE);
        editor = sharedPref.edit();

        String loadedString = sharedPref.getString(getString(R.string.key_totalLog), "");
        totalLog.loadFromString(loadedString);
        outputLog.setText(loadedString);
    }

    public void ButtonAddToLog(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextAddToLog);
        String message = editText.getText().toString();
        addToLog(message);
    }

    /** Called when the user taps the addToLog button */
    public void addToLog(String value) {
        String updatedLog = totalLog.addLine(value);
        // Update UI
        outputLog.setText(updatedLog);
    }

    public void deleteLastLog(View view) {
        //String totalLog = sharedPref.getString(getString(R.string.key_totalLog), "");
        String removedLastLog = totalLog.deleteLastRow();
        if (removedLastLog.isEmpty()) {
                removedLastLog = "Empty";
        }
        // put to screen
        TextView logView = (TextView) findViewById(R.id.logText);
        logView.setText(removedLastLog);
    }

    public void AddNewButton(View view) {
        EditText editTextNewButtonName = (EditText) findViewById(R.id.editTextNewButtonName);
        String newButtonName = editTextNewButtonName.getText().toString();
        EditText editTextNewButtonValue = (EditText) findViewById(R.id.editText_NewButtonValue);
        String newButtonValue = editTextNewButtonValue.getText().toString();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearLayout_AddedButtons);
        Button newButton = buttonHandler.AddNewButton(newButtonName, newButtonValue);
        linearLayout.addView(newButton);
    }

    public void RemoveLastButton(View view) {
        // TODO: Move this to ButtonHandler!
        if (numberOfAddedButtons == 0) {
            return;
        }
        LinearLayout addedButtons = (LinearLayout) findViewById(R.id.LinearLayout_AddedButtons);
        View foundButton = findViewById(numberOfAddedButtons);
        numberOfAddedButtons--;
        addedButtons.removeView(foundButton);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // save to file
        editor.putString(getString(R.string.key_totalLog), totalLog.GetString());
        editor.commit();
    }
}
