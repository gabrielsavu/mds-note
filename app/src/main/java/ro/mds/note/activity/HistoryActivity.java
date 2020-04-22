package ro.mds.note.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import ro.mds.note.R;

public class HistoryActivity extends AppCompatActivity {
    private EditText siteText;
    private Button accesSite;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        accesSite=findViewById(R.id.accesSite);
        siteText=findViewById(R.id.siteText);
        webView=findViewById(R.id.webView);

        accesSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String siteURL=siteText.getText().toString();
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(siteURL);
                WebSettings webSettings=webView.getSettings();
                webSettings.setJavaScriptEnabled(true);

            }

        });


        }
    @Override
    public void onBackPressed(){
        if (webView.canGoBack()){
            webView.goBack();
        }
        else
            super.onBackPressed();
    }
    }



