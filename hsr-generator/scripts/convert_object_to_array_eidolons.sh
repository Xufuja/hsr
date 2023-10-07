#!/bin/bash

input_file="/mnt/c/Dev/StarRailData/ExcelOutput/AvatarRankConfig.json"

jq 'to_entries | map(
  .value.SkillAddLevelList |= if . == {} then [] else to_entries | map({SkillId: (.key | tonumber), Value: .value}) end
) | from_entries' "$input_file" > "$input_file.tmp" && mv "$input_file.tmp" "$input_file"

echo "Done"
