package com.ajs.bloknot;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

public class ThemedActivity extends AppCompatActivity {
    public void updateTheme() {
        try {
            int theme = getPackageManager().getActivityInfo(getComponentName(), 0).getThemeResource();
            if (theme == R.style.Matrix) {
                setTheme(R.style.Aether);
            } else {
                setTheme(R.style.Matrix);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
