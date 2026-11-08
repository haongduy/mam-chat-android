#!/usr/bin/env python3
"""Regenerate MÂM Chat launcher and in-app logos from the approved 1024px icon.

The source pixels are not recolored. Near-white canvas connected to the image
corners is made transparent so the circular mark can sit on a white adaptive
background. Run from the repository root:

    python3 tools/branding/generate_launcher_icons.py
"""

from __future__ import annotations

import os
from collections import deque

from PIL import Image

ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), "..", ".."))
SOURCE = os.path.join(ROOT, "docs", "branding", "mam-icon-1024.png")


def punch_white_canvas(image: Image.Image) -> Image.Image:
    image = image.convert("RGBA")
    width, height = image.size
    pixels = image.load()

    def near_white(x: int, y: int) -> bool:
        red, green, blue, alpha = pixels[x, y]
        return alpha > 0 and red >= 248 and green >= 248 and blue >= 248

    seen = [[False] * width for _ in range(height)]
    queue: deque[tuple[int, int]] = deque()
    for x, y in ((0, 0), (width - 1, 0), (0, height - 1), (width - 1, height - 1)):
        if near_white(x, y):
            queue.append((x, y))
            seen[y][x] = True
    while queue:
        x, y = queue.popleft()
        red, green, blue, _alpha = pixels[x, y]
        pixels[x, y] = (red, green, blue, 0)
        for nx, ny in ((x - 1, y), (x + 1, y), (x, y - 1), (x, y + 1)):
            if 0 <= nx < width and 0 <= ny < height and not seen[ny][nx] and near_white(nx, ny):
                seen[ny][nx] = True
                queue.append((nx, ny))
    return image


def save_png(image: Image.Image, path: str) -> None:
    os.makedirs(os.path.dirname(path), exist_ok=True)
    image.save(path, "PNG", optimize=True)


def main() -> None:
    logo = punch_white_canvas(Image.open(SOURCE))
    res = os.path.join(ROOT, "appicon", "element", "src", "main", "res")
    legacy = {
        "mipmap-mdpi": 48,
        "mipmap-hdpi": 72,
        "mipmap-xhdpi": 96,
        "mipmap-xxhdpi": 144,
        "mipmap-xxxhdpi": 192,
    }
    for folder, size in legacy.items():
        resized = logo.resize((size, size), Image.Resampling.LANCZOS)
        for name in ("ic_launcher.png", "ic_launcher_round.png"):
            save_png(resized, os.path.join(res, folder, name))

    foreground = {
        "mipmap-mdpi": 108,
        "mipmap-hdpi": 162,
        "mipmap-xhdpi": 216,
        "mipmap-xxhdpi": 324,
        "mipmap-xxxhdpi": 432,
    }
    for folder, size in foreground.items():
        resized = logo.resize((size, size), Image.Resampling.LANCZOS)
        save_png(resized, os.path.join(res, folder, "ic_launcher_foreground.png"))
        mono = Image.new("RGBA", (size, size), (0, 0, 0, 0))
        source_pixels = resized.load()
        mono_pixels = mono.load()
        for y in range(size):
            for x in range(size):
                red, green, blue, alpha = source_pixels[x, y]
                if alpha < 16:
                    continue
                luminance = 0.2126 * red + 0.7152 * green + 0.0722 * blue
                if luminance > 180:
                    strength = int(min(255, alpha * (luminance - 140) / 115))
                    mono_pixels[x, y] = (255, 255, 255, max(0, strength))
        save_png(mono, os.path.join(res, folder, "ic_launcher_monochrome.png"))

    mark = logo.resize((660, 660), Image.Resampling.LANCZOS)
    designsystem = os.path.join(ROOT, "libraries", "designsystem", "src", "main", "res")
    small = mark.resize((330, 330), Image.Resampling.LANCZOS)
    for folder in ("drawable-xxhdpi", "drawable-night-xxhdpi"):
        save_png(small, os.path.join(designsystem, folder, "element_logo.png"))
    save_png(mark, os.path.join(ROOT, "app", "src", "main", "res", "drawable-nodpi", "onboarding_logo.png"))
    print("Wrote launcher and in-app logos from", SOURCE)


if __name__ == "__main__":
    main()
