package jp.ac.meijou.android.s241205155;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

import jp.ac.meijou.android.s241205155.databinding.ActivityMain6Binding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity6 extends AppCompatActivity {

    private final OkHttpClient okHttpClient = new OkHttpClient();
    private ActivityMain6Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMain6Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.Main6button.setOnClickListener(view -> {
            var text = binding.Main6editTextTextPersonName.getText().toString();
            var url = Uri.parse("https://placehold.jp/500x500.png")
                    .buildUpon()
                    .appendQueryParameter("text", text)
                    .build()
                    .toString();
            getImage(url);
        });
    }

    private void getImage(String url) {
        var request = new Request.Builder()
                .url(url)
                .build();

        // 非同期で通信を実行
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // 通信失敗時
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                var bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                runOnUiThread(() -> binding.Main6imageView.setImageBitmap(bitmap));
            }
        });
    }
}