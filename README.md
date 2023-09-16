# HSR

## Description

I recently discovered [Dimbreath's](https://github.com/Dimbreath) StarRailData's repository and figured it would be interesting to create a parser for it.

Heavily work in progress.

## Notes

Ideally, I have this work out of the box, but the class generator needs some assistance.

### Light Cone Skills

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
}
```

If you now run the generator, it will be treated as a `Double`.

### Light Cone Stats

The Equilibrium level requirement for Light Cones is not present for the ascension 0 entries and thus is excluded.

To fix this:

1. Open `StarRailData\ExcelOutput\EquipmentPromotionConfig.json`
2. Go to the first `PlayerLevelRequire` property you see
3. Add a new line and set it to `"WorldLevelRequire": 0,`

For example, the first entry is as follows:

```json
{
  "20000": {
    "0": {
      "EquipmentID": 20000,
      "Promotion": 0,
      "PromotionCostList": [
        {
          "ItemID": 2,
          "ItemNum": 3000
        },
        {
          "ItemID": 111011,
          "ItemNum": 4
        }
      ],
      "PlayerLevelRequire": 15,
      "MaxLevel": 20,
      "BaseHP": {
        "Value": 38.40000000037253
      },
      "BaseHPAdd": {
        "Value": 5.760000000707805
      },
      "BaseAttack": {
        "Value": 14.400000000372529
      },
      "BaseAttackAdd": {
        "Value": 2.1600000001490116
      },
      "BaseDefence": {
        "Value": 12
      },
      "BaseDefenceAdd": {
        "Value": 1.800000000745058
      }
    }
  }
}
```

After the change:

```json
{
  "20000": {
    "0": {
      "EquipmentID": 20000,
      "Promotion": 0,
      "PromotionCostList": [
        {
          "ItemID": 2,
          "ItemNum": 3000
        },
        {
          "ItemID": 111011,
          "ItemNum": 4
        }
      ],
      "PlayerLevelRequire": 15,
      "WorldLevelRequire": 0,
      "MaxLevel": 20,
      "BaseHP": {
        "Value": 38.40000000037253
      },
      "BaseHPAdd": {
        "Value": 5.760000000707805
      },
      "BaseAttack": {
        "Value": 14.400000000372529
      },
      "BaseAttackAdd": {
        "Value": 2.1600000001490116
      },
      "BaseDefence": {
        "Value": 12
      },
      "BaseDefenceAdd": {
        "Value": 1.800000000745058
      }
    }
  }
}
```