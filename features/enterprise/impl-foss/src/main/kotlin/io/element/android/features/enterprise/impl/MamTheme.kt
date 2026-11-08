/*
 * Copyright (c) 2026 Element Creations Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.features.enterprise.impl

import androidx.compose.ui.graphics.Color
import io.element.android.compound.colors.SemanticColorsLightDark
import io.element.android.compound.tokens.generated.SemanticColors
import io.element.android.compound.tokens.generated.compoundColorsDark
import io.element.android.compound.tokens.generated.compoundColorsLight

/**
 * Pink UI aligned with the MÂM Chat web theme.
 * The blue/white logo is not recolored; only semantic UI colors change.
 *
 * Light values match the web tokens exactly:
 * primary #BE185D, hover #9D174D, pressed #831843, pale #FFF1F7, selection #FCE7F3.
 * Dark mode keeps those button colors and uses lighter pink for text and icons on dark surfaces.
 */
internal object MamTheme {
    private val primary = Color(0xFFBE185D)
    private val hover = Color(0xFF9D174D)
    private val pressed = Color(0xFF831843)
    private val pale = Color(0xFFFFF1F7)
    private val selection = Color(0xFFFCE7F3)
    private val text = Color(0xFF2A1520)
    private val textSecondary = Color(0xFF725565)
    private val separator = Color(0xFFFBCFE8)
    private val disabled = Color(0xFFF9A8D4)
    private val onDarkAccent = Color(0xFFF472B6)
    private val darkSubtle = Color(0xFF2A1520)
    private val darkSelection = Color(0xFF4A1D32)

    val semanticColors: SemanticColorsLightDark = SemanticColorsLightDark(
        light = compoundColorsLight.mamLight(),
        dark = compoundColorsDark.mamDark(),
    )

    private fun SemanticColors.mamLight(): SemanticColors = copy(
        bgAccentHovered = hover,
        bgAccentPressed = pressed,
        bgAccentRest = primary,
        bgAccentSelected = selection,
        bgAccentSubtle = pale,
        bgActionPrimaryDisabled = disabled,
        bgActionPrimaryHovered = hover,
        bgActionPrimaryPressed = pressed,
        bgActionPrimaryRest = primary,
        bgActionTertiarySelected = selection,
        bgBadgeAccent = selection,
        bgSubtleSecondary = pale,
        bgSubtleSecondaryLevel0 = pale,
        borderAccentPrimary = primary,
        borderInteractiveHovered = hover,
        borderInteractivePrimary = primary,
        gradientActionStop1 = disabled,
        gradientActionStop2 = primary,
        gradientActionStop3 = hover,
        gradientActionStop4 = pressed,
        gradientSubtleStop1 = Color(0x80BE185D),
        gradientSubtleStop2 = Color(0x66FCE7F3),
        gradientSubtleStop3 = Color(0x4DFFF1F7),
        gradientSubtleStop4 = Color(0x33FCE7F3),
        gradientSubtleStop5 = Color(0x1AFFF1F7),
        iconAccentPrimary = primary,
        iconAccentTertiary = hover,
        iconOnSolidPrimary = Color.White,
        separatorSecondary = separator,
        textActionAccent = primary,
        textBadgeAccent = pressed,
        textLinkExternal = primary,
        textOnSolidPrimary = Color.White,
        textPrimary = text,
        textSecondary = textSecondary,
    )

    private fun SemanticColors.mamDark(): SemanticColors = copy(
        bgAccentHovered = hover,
        bgAccentPressed = pressed,
        bgAccentRest = primary,
        bgAccentSelected = darkSelection,
        bgAccentSubtle = darkSubtle,
        bgActionPrimaryHovered = hover,
        bgActionPrimaryPressed = pressed,
        bgActionPrimaryRest = primary,
        bgActionTertiarySelected = darkSelection,
        bgBadgeAccent = darkSelection,
        borderAccentPrimary = onDarkAccent,
        borderInteractiveHovered = onDarkAccent,
        borderInteractivePrimary = primary,
        gradientActionStop1 = disabled,
        gradientActionStop2 = primary,
        gradientActionStop3 = hover,
        gradientActionStop4 = pressed,
        iconAccentPrimary = onDarkAccent,
        iconAccentTertiary = primary,
        iconOnSolidPrimary = Color.White,
        textActionAccent = onDarkAccent,
        textBadgeAccent = onDarkAccent,
        textLinkExternal = onDarkAccent,
        textOnSolidPrimary = Color.White,
    )
}
