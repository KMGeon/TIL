#!/bin/bash

IMG_DIR="./Spring/Batch"
TARGET_DIR="./TIL/img"

mkdir -p "$TARGET_DIR"

find "$IMG_DIR" -type f \( -name "*.png" -o -name "*.jpg" -o -name "*.jpeg" \) | while read image; do
   filename=$(basename "$image")
   extension="${filename##*.}"
   new_name="$(uuidgen).$extension"
   mv "$image" "$TARGET_DIR/$new_name"
   echo "Copied: $filename -> $new_name"
done