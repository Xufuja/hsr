# HSR

## Description

Parser for [Dimbreath's](https://github.com/Dimbreath) StarRailData repository

## Usage

Ideally, I have this work out of the box, but the class generator needs some assistance.

Light Cone ability parameters always get interpreted as `Integer` while they need to be `Double`.

To fix this:

1. Open `StarRailData\ExcelOutput\EquipmentSkillConfig.json`
2. Go to the first `ParamList` array you see
3. Add a `.0` to any value without a decimal

For example, the first entry is as follows:

```json
{
  "20000": {
    "1": {
      "SkillID": 20000,
      "SkillName": {
        "Hash": -1480616705
      },
      "SkillDesc": {
        "Hash": 1777866215
      },
      "Level": 1,
      "AbilityName": "Ability20000",
      "ParamList": [
        {
          "Value": 0.12000000011175871
        },
        {
          "Value": 3
        }
      ],
      "AbilityProperty": []
    }
  }
```

Change the `3` -> `3.0`:

```json
{
  "20000": {
    "1": {
      "SkillID": 20000,
      "SkillName": {
        "Hash": -1480616705
      },
      "SkillDesc": {
        "Hash": 1777866215
      },
      "Level": 1,
      "AbilityName": "Ability20000",
      "ParamList": [
        {
          "Value": 0.12000000011175871
        },
        {
          "Value": 3.0
        }
      ],
      "AbilityProperty": []
    }
  }
```

If you now run the generator, it will be treated as a `Double`.