package com.dlovan.fullmovie.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.dlovan.fullmovie.R;
import com.dlovan.fullmovie.utils.Utils;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_setting);
        setTitle(getString(R.string.action_settings));
    }

    public static class MoviePreferenceFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener {

        private boolean isLanguageChanged = false;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference orderBy = findPreference(getString(R.string.settings_language_key));
            bindPreferenceSummaryToValue(orderBy);
            orderBy.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    isLanguageChanged = true;
                    return true;
                }
            });
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }

                if (preference.getKey().equals(getString(R.string.settings_language_key)) && isLanguageChanged) {
                    getActivity().recreate();
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;

        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");

            onPreferenceChange(preference, preferenceString);
        }
    }
}
