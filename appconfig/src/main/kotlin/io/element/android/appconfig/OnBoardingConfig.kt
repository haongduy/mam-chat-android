/*
 * Copyright (c) 2025 Element Creations Ltd.
 * Copyright 2024, 2025 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.appconfig

object OnBoardingConfig {
    /**
     * Public registration is disabled on chat.mam.info.vn. Accounts are provisioned out of band.
     * The app must not offer in-app sign-up against that homeserver.
     */
    const val CAN_CREATE_ACCOUNT = false

    /**
     * QR sign-in in Element X depends on Matrix Authentication Service / OIDC.
     * This homeserver is standalone Synapse with m.login.password only, so the QR entry point is hidden.
     * Password login stays available.
     */
    const val CAN_LOGIN_WITH_QR_CODE = false
}
