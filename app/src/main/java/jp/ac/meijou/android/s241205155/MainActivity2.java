package jp.ac.meijou.android.s241205155;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Optional;

import jp.ac.meijou.android.s241205155.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {
    private ActivityMain2Binding binding;
    public static final String EXTRA_TEXT_TO_SEND = "jp.ac.meijou.android.s241205155.TEXT_TO_SEND";

    private final ActivityResultLauncher<Intent> getActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                switch (result.getResultCode()) {
                    case RESULT_OK -> {
                        Optional.ofNullable(result.getData())
                                .map(data -> data.getStringExtra("ret"))
                                .map(text -> "Result: " + text)
                                .ifPresent(text -> binding.textView1.setText(text));
                    }
                    case RESULT_CANCELED -> {
                        binding.textView1.setText(getString(R.string.result_canceled));
                    }
                    default -> {
                        binding.textView1.setText(getString(R.string.result_unknown, result.getResultCode()));
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 明示的Intent: MainActivity4へ遷移
        binding.buttonToMain3.setOnClickListener(view -> {
            var intent = new Intent(this, MainActivity4.class);
            startActivity(intent);
        });

        // カメラ起動
        if (binding.buttonCamera != null) {
            binding.buttonCamera.setOnClickListener(view -> {
                var intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                startActivity(intent);
            });
        }

        // 暗黙的Intent: Yahoo! JAPANを開く
        binding.buttonToYahoo.setOnClickListener(view -> {
            var intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.yahoo.co.jp"));
            startActivity(intent);
        });

        // 文字を送信
        if (binding.buttonSendText != null && binding.editTextForMain4 != null) {
            binding.buttonSendText.setOnClickListener(view -> {
                var intent = new Intent(this, MainActivity4.class);
                intent.putExtra(EXTRA_TEXT_TO_SEND, binding.editTextForMain4.getText().toString());
                startActivity(intent);
            });
        }

        // 結果を取得
        if (binding.buttonGetResult != null) {
            binding.buttonGetResult.setOnClickListener(view -> {
                var intent = new Intent(this, MainActivity4.class);
                getActivityResult.launch(intent);
            });
        }

        // MainActivityから渡されたデータを表示する処理
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TEXT_FROM_MAIN")) {
            String text = intent.getStringExtra("TEXT_FROM_MAIN");
            if (binding.textView1.getText().toString().isEmpty()) {
                binding.textView1.setText(text);
            }
        }
    }
}
