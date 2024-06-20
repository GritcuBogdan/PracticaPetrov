package org.example.praktika;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;


import java.util.function.UnaryOperator;

public interface Controller {
    int getMaxTextFieldLength(TextField textField);

    default void limitTextFieldLength(TextField textField, KeyEvent keyEvent){
        int maxLength = getMaxTextFieldLength(textField);
        if(textField.getText().length() >= maxLength){
            keyEvent.consume();
        }
    }

    void limitTextFieldLength(KeyEvent event);


    void forceDigitsOnly(TextField textField);




}