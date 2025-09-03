package jp.ac.meijou.android.s241205155;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import jp.ac.meijou.android.s241205155.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View Bindingを初期化
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        // ルートビューを設定
        setContentView(binding.getRoot());

        // bindingオブジェクトを使ってボタンにアクセスし、クリックリスナーを設定
        binding.buttonToMain3.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity3.class);
            startActivity(intent);
        });
    }
}