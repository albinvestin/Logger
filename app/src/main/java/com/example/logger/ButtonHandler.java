package com.example.logger;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ButtonHandler {
    // Pattern: Name,Value
    private Pattern loadPattern = Pattern.compile("(.+),(.+)");
    private int numberOfAddedButtons = 0;
    private Context parentContext;

    public ButtonHandler(Context context) {
        parentContext = context;
    }

    // TODO Add write method and load from savedpreferences.
    public Button[] LoadButtonsFromString(String input) {
        String[] buttonStrings = input.split("\n");
        Button[] loadedButtons = new Button[buttonStrings.length];
        String currentName;
        String currentValue;
        int currentLoop = 0;
        for (String iButton: buttonStrings) {
            Matcher matcher = loadPattern.matcher(iButton);
            if (matcher.find()) {
                MatchResult result = matcher.toMatchResult();
                currentName = result.group(1);
                currentValue = result.group(2);
                Button newButton = AddNewButton(currentName, currentValue);
                loadedButtons[currentLoop] = newButton;
            }
            currentLoop++;
        }
        return loadedButtons;
    }

    public Button AddNewButton(String name, String value) {
        if (numberOfAddedButtons >= 5) {
            return null;
        }
        Button newButton = new Button(parentContext);
        newButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        newButton.setText(name);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)parentContext).addToLog(value);
            }
        });
        numberOfAddedButtons++;
        newButton.setId(numberOfAddedButtons);
        return newButton;
    }
}
