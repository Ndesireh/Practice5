package com.example.practice5;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editMunicipality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (view, windowInsets) -> {
                    Insets bars = windowInsets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    view.setPadding(
                            bars.left,
                            bars.top,
                            bars.right,
                            bars.bottom
                    );

                    return windowInsets;
                }
        );

        editMunicipality = findViewById(R.id.editMunicipality);

        editMunicipality.setOnEditorActionListener(
                (textView, actionId, event) -> {

                    boolean enterPressed = event != null
                            && event.getKeyCode()
                            == KeyEvent.KEYCODE_ENTER;

                    boolean submitAction =
                            actionId == EditorInfo.IME_ACTION_GO
                                    || actionId == EditorInfo.IME_ACTION_DONE
                                    || enterPressed;

                    if (!submitAction) {
                        return false;
                    }

                    // 避免实体键盘按下、松开时重复跳转。
                    if (event == null
                            || (event.getAction() == KeyEvent.ACTION_DOWN
                            && event.getRepeatCount() == 0)) {

                        openMunicipality();
                    }

                    return true;
                }
        );
    }

    private void openMunicipality() {

        String input = editMunicipality.getText().toString();

        String municipality =
                TextProcessor.processMunicipality(input);

        if (municipality == null) {

            if (input.trim().isEmpty()) {
                editMunicipality.setError(
                        getString(R.string.error_empty)
                );
            } else {
                editMunicipality.setError(
                        getString(R.string.error_invalid)
                );
            }

            editMunicipality.requestFocus();
            return;
        }

        editMunicipality.setError(null);

        Intent intent = new Intent(
                MainActivity.this,
                Municipality.class
        );

        intent.putExtra(
                Municipality.EXTRA_MUNICIPALITY,
                municipality
        );

        startActivity(intent);
    }
}