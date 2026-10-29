package com.example.practice5;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Municipality extends AppCompatActivity {

    public static final String EXTRA_MUNICIPALITY =
            "com.example.practice5.EXTRA_MUNICIPALITY";

    private String websiteUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_municipality);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.municipalityRoot),
                (view, windowInsets) -> {
                    Insets bars = windowInsets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    view.setPadding(
                            bars.left,
                            bars.top,
                            bars.right,
                            bars.bottom
                    );

                    return windowInsets;
                }
        );

        TextView cityName = findViewById(R.id.cityName);
        ImageView cityImage = findViewById(R.id.cityImage);
        TextView cityInformation = findViewById(R.id.cityInformation);
        Button visitSiteButton = findViewById(R.id.visitSiteButton);

        String receivedName = getIntent().getStringExtra(
                EXTRA_MUNICIPALITY
        );

        String municipality =
                TextProcessor.processMunicipality(receivedName);

        if (municipality == null) {
            Toast.makeText(
                    this,
                    R.string.error_invalid,
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        int informationIndex;
        int imageResource;
        int colorResource;

        switch (municipality) {

            case "Beijing":
                informationIndex = 0;
                imageResource = R.drawable.beijing;
                colorResource = R.color.municipality_beijing;
                websiteUrl = "https://english.beijing.gov.cn/";
                break;

            case "Shanghai":
                informationIndex = 1;
                imageResource = R.drawable.shanghai;
                colorResource = R.color.municipality_shanghai;
                websiteUrl = "https://english.shanghai.gov.cn/";
                break;

            case "Chongqing":
                informationIndex = 2;
                imageResource = R.drawable.chongqing;
                colorResource = R.color.municipality_chongqing;
                websiteUrl = "https://english.cq.gov.cn/";
                break;

            case "Tianjin":
                informationIndex = 3;
                imageResource = R.drawable.tianjin;
                colorResource = R.color.municipality_tianjin;
                websiteUrl = "https://en.tj.gov.cn/";
                break;

            default:
                Toast.makeText(
                        this,
                        R.string.error_invalid,
                        Toast.LENGTH_SHORT
                ).show();

                finish();
                return;
        }

        String[] information = getResources().getStringArray(
                R.array.municipalities_array
        );

        int cityColor = ContextCompat.getColor(
                this,
                colorResource
        );

        // 橙色背景使用深色文字，其他背景使用浅色文字。
        int foregroundResource = municipality.equals("Chongqing")
                ? R.color.color_text_dark
                : R.color.color_text_light;

        int foregroundColor = ContextCompat.getColor(
                this,
                foregroundResource
        );

        cityName.setText(municipality);
        cityName.setBackgroundColor(cityColor);
        cityName.setTextColor(foregroundColor);

        cityImage.setImageResource(imageResource);
        cityImage.setContentDescription(
                getString(
                        R.string.city_image_description,
                        municipality
                )
        );

        cityInformation.setText(information[informationIndex]);
        cityInformation.setTextColor(cityColor);

        visitSiteButton.setBackgroundTintList(
                ColorStateList.valueOf(cityColor)
        );
        visitSiteButton.setTextColor(foregroundColor);

        visitSiteButton.setOnClickListener(view -> openWebsite());
    }

    private void openWebsite() {

        Intent websiteIntent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(websiteUrl)
        );

        try {
            startActivity(websiteIntent);
        } catch (ActivityNotFoundException exception) {
            Toast.makeText(
                    this,
                    R.string.error_no_browser,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}