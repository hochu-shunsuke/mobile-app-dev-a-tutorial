package jp.ac.meijou.android.s241205155;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import jp.ac.meijou.android.s241205155.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;
    public static final String EXTRA_TEXT_TO_SEND = "jp.ac.meijou.android.s241205155.TEXT_TO_SEND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 明示的Intent: MainActivity4へテキストを送る
        binding.buttonToMain3.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity4.class);
            String textToSend = binding.editTextForMain4.getText().toString();
            intent.putExtra(EXTRA_TEXT_TO_SEND, textToSend);
            startActivity(intent);
        });

        // 暗黙的Intent: Yahoo! JAPANを開く
        binding.buttonToYahoo.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.yahoo.co.jp"));
            startActivity(intent);
        });

        // TextView1に何か表示するサンプル（もし不要であればこの部分は削除してください）
        // 例えば、MainActivityから渡されたデータを表示する場合など
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TEXT_FROM_MAIN")) {
            String text = intent.getStringExtra("TEXT_FROM_MAIN");
            binding.textView1.setText(text);
        }
    }
}
