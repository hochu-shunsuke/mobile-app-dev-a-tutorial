package jp.ac.meijou.android.s241205155;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {

    private TextView textViewResult;
    private double firstOperand = 0.0;
    private String currentOperator = "";
    private boolean isNewCalculation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_main3), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewResult = findViewById(R.id.textView_result);

        setupNumberButtons();
        setupOperatorButtons();
        setupEqualsButton();
        setupACButton();
    }

    private void setupNumberButtons() {
        int[] numberButtonIds = {
                R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
                R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7,
                R.id.button_8, R.id.button_9
        };

        for (int id : numberButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(v -> {
                String number = ((Button) v).getText().toString();
                if (isNewCalculation || textViewResult.getText().toString().equals("0")) {
                    textViewResult.setText(number);
                    isNewCalculation = false;
                } else {
                    textViewResult.append(number);
                }
            });
        }
    }

    private void setupOperatorButtons() {
        int[] operatorButtonIds = {
                R.id.button_plus, R.id.button_minus, R.id.button_multiple, R.id.button_devide
        };

        for (int id : operatorButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(v -> {
                try {
                    firstOperand = Double.parseDouble(textViewResult.getText().toString());
                    currentOperator = ((Button) v).getText().toString();
                    isNewCalculation = true;
                } catch (NumberFormatException e) {
                    // 何もしない
                }
            });
        }
    }

    private void setupEqualsButton() {
        Button buttonEqual = findViewById(R.id.button_equal);
        buttonEqual.setOnClickListener(v -> {
            try {
                double secondOperand = Double.parseDouble(textViewResult.getText().toString());
                double result = 0.0;

                switch (currentOperator) {
                    case "+":
                        result = firstOperand + secondOperand;
                        break;
                    case "-":
                        result = firstOperand - secondOperand;
                        break;
                    case "*":
                        result = firstOperand * secondOperand;
                        break;
                    case "/":
                        if (secondOperand != 0) {
                            result = firstOperand / secondOperand;
                        } else {
                            Toast.makeText(this, "0で割ることはできません", Toast.LENGTH_SHORT).show();
                            clearAll();
                            return;
                        }
                        break;
                    default:
                        return;
                }

                // 結果が整数であれば小数点以下を表示しない
                if (result == (long) result) {
                    textViewResult.setText(String.valueOf((long) result));
                } else {
                    textViewResult.setText(String.valueOf(result));
                }

                isNewCalculation = true;
                currentOperator = "";
            } catch (NumberFormatException e) {
                // 何もしない
            }
        });
    }

    private void setupACButton() {
        Button buttonAC = findViewById(R.id.button_AC);
        buttonAC.setOnClickListener(v -> clearAll());
    }

    private void clearAll() {
        textViewResult.setText("0");
        firstOperand = 0.0;
        currentOperator = "";
        isNewCalculation = true;
    }
}