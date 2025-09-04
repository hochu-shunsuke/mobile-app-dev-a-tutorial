package jp.ac.meijou.android.s241205155;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
            String textSize = binding.Main6TextSizeInput.getText().toString();
            String textColor = binding.Main6TextColorInput.getText().toString();
            String bgColor = binding.Main6BgColorInput.getText().toString();
            String widthStr = binding.Main6WidthInput.getText().toString();
            String heightStr = binding.Main6HeightInput.getText().toString();
            String text = binding.Main6TextInput.getText().toString();
            int width = 500;
            int height = 500;
            int size = 234;
            try {
                width = Integer.parseInt(widthStr);
                height = Integer.parseInt(heightStr);
                size = Integer.parseInt(textSize);
            } catch (NumberFormatException ignored) {}
            // URLエンコード
            String encodedText = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);
            }
            String url = "https://placehold.jp/" + size + "/" + textColor + "/" + bgColor + "/" + width + "x" + height + ".png?text=" + encodedText;
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