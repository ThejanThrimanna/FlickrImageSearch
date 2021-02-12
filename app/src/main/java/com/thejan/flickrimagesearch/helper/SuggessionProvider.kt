package com.thejan.flickrimagesearch.helper

import android.content.SearchRecentSuggestionsProvider


class SuggestionProvider : SearchRecentSuggestionsProvider() {
    companion object {
        const val AUTHORITY = "com.thejan.flickrimagesearch.helper.SuggestionProvider"
        const val MODE = DATABASE_MODE_QUERIES
    }

    init {
        setupSuggestions(
            AUTHORITY,
            MODE
        )
    }
}