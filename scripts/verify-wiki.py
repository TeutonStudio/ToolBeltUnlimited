#!/usr/bin/env python3
"""Check bilingual wiki navigation and the feature-to-screenshot inventory (stdlib only)."""
import hashlib
import json
from pathlib import Path
import re
import struct
import sys
from urllib.parse import unquote, urlsplit
import zlib

ROOT = Path(__file__).resolve().parents[1]
WIKI = ROOT / "wiki"
MEDIA_URL = "https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/"
LINK = re.compile(r"(!?)\[([^\]]*)\]\(([^\s)]+)\)")
errors = []


def check(condition, message):
    if not condition:
        errors.append(message)


def png_size(path):
    """Validate the PNG signature and all chunk checksums, then return dimensions."""
    data = path.read_bytes()
    if data[:8] != b"\x89PNG\r\n\x1a\n":
        raise ValueError("not a PNG")
    offset, size, image_data = 8, None, bytearray()
    while offset < len(data):
        length = struct.unpack_from(">I", data, offset)[0]
        kind = data[offset + 4:offset + 8]
        body = data[offset + 8:offset + 8 + length]
        crc = struct.unpack_from(">I", data, offset + 8 + length)[0]
        if zlib.crc32(kind + body) & 0xFFFFFFFF != crc:
            raise ValueError("PNG checksum mismatch")
        if kind == b"IHDR":
            size = struct.unpack_from(">II", body)
        if kind == b"IDAT":
            image_data.extend(body)
        offset += length + 12
        if kind == b"IEND":
            if offset != len(data) or not size or not all(size) or not image_data:
                raise ValueError("invalid PNG structure")
            if not zlib.decompress(image_data):
                raise ValueError("empty PNG pixels")
            return size
    raise ValueError("missing IEND")


def anchors(text):
    result, seen = set(), {}
    for heading in re.findall(r"^#{1,6}\s+(.+?)\s*#*\s*$", text, re.M):
        anchor = re.sub(r"[^\w\- ]", "", heading.lower()).replace(" ", "-")
        n = seen.get(anchor, 0)
        seen[anchor] = n + 1
        result.add(f"{anchor}-{n}" if n else anchor)
    return result


def main():
    manifest = json.loads((ROOT / "docs/wiki-media-coverage.json").read_text())
    pages = {p.stem: p.read_text() for p in WIKI.glob("*.md")}
    images = manifest["images"]
    used_images = {name: set() for name in images}
    check(bool(pages), "No wiki pages")
    for slug, text in pages.items():
        check(text.count("```") % 2 == 0, f"{slug}: unclosed code fence")
        # Ignore code blocks when resolving actual links.
        prose = re.sub(r"```.*?```", "", text, flags=re.S)
        for image, label, target in LINK.findall(prose):
            if image:
                check(bool(label.strip()), f"{slug}: image needs alt text")
                check(target.startswith(MEDIA_URL), f"{slug}: unexpected image URL {target}")
                relative = unquote(target.removeprefix(MEDIA_URL))
                check(relative in images, f"{slug}: image absent from inventory: {relative}")
                if relative in used_images:
                    used_images[relative].add(slug)
                continue
            parsed = urlsplit(target)
            if parsed.scheme:
                check(parsed.scheme == "https", f"{slug}: unexpected link scheme {target}")
                continue
            dest = unquote(parsed.path).removesuffix(".md") or slug
            check(dest in pages, f"{slug}: missing page {dest}")
            if parsed.fragment and dest in pages:
                check(unquote(parsed.fragment) in anchors(pages[dest]), f"{slug}: missing anchor {target}")

    paired = set()
    for de, en in manifest["language_pairs"]:
        for slug, partner in [(de, en), (en, de)]:
            check(slug not in paired, f"Duplicate language pair: {slug}")
            paired.add(slug)
            check(slug in pages, f"Missing translation: {slug}")
            if slug not in pages:
                continue
            check(f"]({partner})" in pages[slug], f"{slug}: missing language switch")
            check(f"]({slug})" in pages.get("_Sidebar", ""), f"{slug}: absent from sidebar")
            check(bool(re.search(r"!\[[^\]]+\]\(", pages[slug])), f"{slug}: no screenshot")
            check(len(pages[slug].split()) > 55, f"{slug}: translation is only a stub")
    check(paired == set(pages) - {"_Sidebar", "_Footer"}, "Unpaired wiki pages")
    check(pages.get("_Sidebar", "").find("**Deutsch**") < pages.get("_Sidebar", "").find("**English**"), "German must be primary")

    for relative, entry in images.items():
        path = WIKI / relative
        check(path.is_file(), f"Missing screenshot: {relative}")
        check(bool(used_images[relative]), f"Unreferenced screenshot: {relative}")
        check(bool(entry.get("origin")) and bool(entry.get("scope_de")) and bool(entry.get("scope_en")), f"Missing provenance: {relative}")
        if path.is_file():
            check(hashlib.sha256(path.read_bytes()).hexdigest() == entry["sha256"], f"Changed screenshot: {relative}")
            try:
                check(list(png_size(path)) == entry["dimensions"], f"Incorrect PNG dimensions: {relative}")
            except (ValueError, struct.error, zlib.error) as exc:
                errors.append(f"{relative}: {exc}")
    actual_images = {str(p.relative_to(WIKI)) for p in (WIKI / "assets").rglob("*") if p.is_file()}
    check(actual_images == set(images), "Screenshot files and inventory differ")

    feature_ids = set()
    for feature in manifest["features"]:
        fid = feature["id"]
        check(fid not in feature_ids, f"Duplicate feature: {fid}")
        feature_ids.add(fid)
        check(feature.get("status") == "shown", f"{fid}: feature needs a screenshot")
        check(bool(feature["images"]), f"{fid}: missing media assignment")
        check(feature["pages"] in manifest["language_pairs"], f"{fid}: invalid language pair")
        for relative in feature["images"]:
            check(relative in images, f"{fid}: unknown screenshot {relative}")
            for slug in feature["pages"]:
                check(slug in used_images.get(relative, set()), f"{fid}: {relative} not embedded in {slug}")
        check(bool(feature["sources"]), f"{fid}: missing implementation reference")
        for source in feature["sources"]:
            check(source in manifest["source_sha256"], f"{fid}: source lacks baseline hash: {source}")
    expected = {"expanded-belt", "original-upgrades", "sewing-upgrades", "preserve-contents", "maximum-boundary", "configurable-limit", "common-tools", "tool-toggle"}
    check(feature_ids == expected, "Feature inventory incomplete or verifier needs updating")
    sources = (list((ROOT / "src/main").rglob("*.kt"))
               + list((ROOT / "src/main").rglob("*.java"))
               + list((ROOT / "src/main").rglob("*.json"))
               + [ROOT / "src/main/resources/META-INF/neoforge.mods.toml", ROOT / "gradle.properties", ROOT / "build.gradle"])
    check({str(p.relative_to(ROOT)) for p in sources} == set(manifest["source_sha256"]),
          "Implementation file inventory changed; review wiki coverage")
    for relative, digest in manifest["source_sha256"].items():
        path = ROOT / relative
        check(path.is_file(), f"Missing source: {relative}")
        if path.is_file():
            check(hashlib.sha256(path.read_bytes()).hexdigest() == digest, f"Implementation changed; review wiki coverage: {relative}")

    readme = (ROOT / "README.md").read_text()
    check(len(readme.split()) <= 110 and len(readme.splitlines()) <= 12, "README must stay brief")
    check(not re.search(r"\b(ist|erweitert|Werkzeuggürtel|Funktionen|Abhängigkeiten|Einstellungen)\b", readme), "README must be English")
    for target in ["/wiki)", "/wiki/English)", "/releases)", "/issues)"]:
        check(target in readme, f"README missing {target}")
    if errors:
        print("\n".join(f"ERROR: {e}" for e in errors), file=sys.stderr)
        return 1
    print(f"OK: {len(pages)} pages, {len(manifest['language_pairs'])} language pairs, {len(images)} screenshots, {len(feature_ids)}/{len(expected)} features shown; README is brief and English.")
    return 0


if __name__ == "__main__":
    sys.exit(main())
