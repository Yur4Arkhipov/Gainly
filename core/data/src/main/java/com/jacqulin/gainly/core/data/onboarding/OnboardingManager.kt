package com.jacqulin.gainly.core.data.onboarding

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnboardingManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "onboarding_preferences")
        private val ONBOARDING_SHOWN_KEY = booleanPreferencesKey("onboarding_shown")
    }

    suspend fun isOnboardingShown(): Boolean {
        return context.dataStore.data.first()[ONBOARDING_SHOWN_KEY] == true
    }

    suspend fun setOnboardingShown() {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_SHOWN_KEY] = true
        }
    }
}