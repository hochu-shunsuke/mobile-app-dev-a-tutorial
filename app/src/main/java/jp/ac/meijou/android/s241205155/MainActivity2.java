package jp.ac.meijou.android.s241205155;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView; // このimportは元のコードから残していますが、直接TextView型変数を使用しない場合は削除可能です
import androidx.appcompat.app.AppCompatActivity;
import jp.ac.meijou.android.s241205155.databinding.ActivityMain2Binding;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import java.util.Optional;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;
    public static final String EXTRA_TEXT_TO_SEND = "jp.ac.meijou.android.s241205155.TEXT_TO_SEND";

    private final ActivityResultLauncher<Intent> getActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                // 結果を binding.textView1 に表示
                switch (result.getResultCode()) {
                    case RESULT_OK:
                        Optional.ofNullable(result.getData())
                                .map(data -> data.getStringExtra("ret")) // MainActivity4から返される結果のキーを "ret" と仮定
                                .map(text -> "Result: " + text)
                                .ifPresent(text -> binding.textView1.setText(text));
                        break;
                    case RESULT_CANCELED:
                        binding.textView1.setText("Result: Canceled");
                        break;
                    default:
                        binding.textView1.setText("Result: Unknown(" + result.getResultCode() + ")");
                        break;
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 明示的Intent: MainActivity4へテキストを送り、結果を受け取る
        binding.buttonToMain3.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity4.class);
            String textToSend = binding.editTextForMain4.getText().toString();
            intent.putExtra(EXTRA_TEXT_TO_SEND, textToSend);
            getActivityResult.launch(intent); // startActivity(intent) から変更
        });

        // 暗黙的Intent: Yahoo! JAPANを開く
        binding.buttonToYahoo.setOnClickListener(v -> {
            var intent = new Intent(); // 提供されたコードのスタイルに合わせる
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.yahoo.co.jp"));
            startActivity(intent);
        });

        // MainActivityから渡されたデータを表示する処理
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TEXT_FROM_MAIN")) {
            String text = intent.getStringExtra("TEXT_FROM_MAIN");
            // ActivityResultLauncher の結果表示と競合する可能性があるが、
            // onCreate 時の初期表示として機能する
            if (binding.textView1.getText().toString().isEmpty()) { // 結果がまだセットされていなければセット
                 binding.textView1.setText(text);
            }
        }
    }
}
