package com.dlovan.fullmovie.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dlovan.fullmovie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.title_detail_movie)
    TextView textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("TITLE");

        if (title.isEmpty() && title.equals("")) {
            title = "error";
        }

        textViewTitle.setText(title);

    }
}
