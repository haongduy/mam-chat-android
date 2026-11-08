/*
 * Copyright (c) 2025 Element Creations Ltd.
 * Copyright 2025 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package config

object BuildTimeConfig {
    /**
     * Reverse-DNS id for mam.info.vn. Distinct from io.element.android.x so this app
     * installs beside official Element. Segments are valid Java identifiers.
     */
    const val APPLICATION_ID = "vn.info.mam.chat"
    const val APPLICATION_NAME = "MÂM Chat"

    /** OAuth redirect scheme if a future MAS deployment is added. Not used by the current homeserver. */
    val METADATA_HOST_REVERSED: String? = "vn.info.mam.chat"
    val OAUTH_CLIENT_URL_PATH: String? = "apps/android"
    val URL_WEBSITE: String? = null
    val URL_LOGO: String? = null
    val URL_COPYRIGHT: String? = null
    val URL_ACCEPTABLE_USE: String? = null
    val URL_PRIVACY: String? = null
    val URL_POLICY: String? = null
    val SERVICES_MAPTILER_BASE_URL: String? = null
    val SERVICES_MAPTILER_APIKEY: String? = null
    val SERVICES_MAPTILER_LIGHT_MAPID: String? = null
    val SERVICES_MAPTILER_DARK_MAPID: String? = null
    val SERVICES_POSTHOG_HOST: String? = null
    val SERVICES_POSTHOG_APIKEY: String? = null
    val SERVICES_SENTRY_DSN: String? = null
    val SERVICES_SENTRY_DSN_RUST: String? = null
    val BUG_REPORT_URL: String? = null
    val BUG_REPORT_APP_NAME: String? = null
    /**
     * Firebase Cloud Messaging is not included. Element's vector-alpha Firebase project and API keys
     * must not be shipped as MÂM Chat's push credentials. There is no MÂM FCM project.
     * UnifiedPush stays compiled so the app still has a push provider, but no custom gateway is configured.
     */
    const val PUSH_CONFIG_INCLUDE_FIREBASE: Boolean = false
    const val PUSH_CONFIG_INCLUDE_UNIFIED_PUSH: Boolean = true

    /**
     * Pusher app id owned by this application. Do not fall back to Element's im.vector.app.android.
     * Registration still will not deliver notifications until a push gateway is configured for this id.
     */
    val PUSHER_APP_ID_RELEASE: String? = "vn.info.mam.chat"
    val PUSHER_APP_ID_DEBUG: String? = "vn.info.mam.chat.debug"
    val PUSHER_APP_ID_NIGHTLY: String? = "vn.info.mam.chat.nightly"
}
