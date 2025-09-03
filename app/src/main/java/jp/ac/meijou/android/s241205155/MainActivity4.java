package jp.ac.meijou.android.s241205155;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import jp.ac.meijou.android.s241205155.databinding.ActivityMain4Binding;

public class MainActivity4 extends AppCompatActivity {

    private ActivityMain4Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity2.EXTRA_TEXT_TO_SEND)) {
            String receivedText = intent.getStringExtra(MainActivity2.EXTRA_TEXT_TO_SEND);
            binding.textView.setText(receivedText);
        }
    }
}
