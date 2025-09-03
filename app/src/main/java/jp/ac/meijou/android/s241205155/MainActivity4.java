package jp.ac.meijou.android.s241205155;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Optional;

import jp.ac.meijou.android.s241205155.databinding.ActivityMain4Binding;

public class MainActivity4 extends AppCompatActivity {

    private ActivityMain4Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMain4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 受け取ったテキストを表示
        Optional.ofNullable(getIntent().getStringExtra(MainActivity2.EXTRA_TEXT_TO_SEND))
                .ifPresent(text -> binding.textViewMain4.setText(text));

        // OKボタン
        binding.button2.setOnClickListener(view -> {
            var resultIntent = new Intent();
            resultIntent.putExtra("ret", "OK");
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        // Cancelボタン
        binding.button3.setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
