package com.example.myapplication.domain.utils

import android.net.Uri
import java.net.URI
import androidx.core.net.toUri

fun appendQueryParam(url: String, key: String, value: String): String {
    return try {
        val uri = URI(url)
        val existingQuery = uri.query
        val newQuery = if (existingQuery.isNullOrEmpty()) {
            "${key}=${value}"
        } else {
            "${existingQuery}&${key}=${value}"
        }

        URI(
            uri.scheme,
            uri.authority,
            uri.path,
            newQuery,
            uri.fragment
        ).toString()
    } catch (e: Exception) {
        url // fallback if URI parsing fails
    }
}

fun isValidUri(uri: String): Boolean {
    return try {
        // Prepend "https://" if missing, then parse the URI
        val parsedUri = "https://$uri".toUri()
        parsedUri.scheme != null && parsedUri.host != null
    } catch (e: Exception) {
        false
    }
}