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

### Relic Sets

When loading Relic set data, Planar Sphere and Link Ropes are not included.

To fix this:

1. Open `StarRailData\ExcelOutput\RelicDataInfo.json`
2. Go to the first set
3. Add dummy entries for the Planer Sphere and Link Rope types

For example, the first entry is as follows:

```json
{
  "101": {
    "HEAD": {
      "SetID": 101,
      "Type": "HEAD",
      "IconPath": "SpriteOutput/ItemIcon/RelicIcons/IconRelic_101_1.png",
      "ItemFigureIconPath": "SpriteOutput/RelicFigures/IconRelic_101_1.png",
      "RelicName": "RelicName_31011",
      "ItemBGDesc": "ItemBGDesc_31011",
      "BGStoryTitle": "RelicStoryTitle_31011",
      "BGStoryContent": "RelicStoryContent_31011"
    },
    "HAND": {
      "SetID": 101,
      "Type": "HAND",
      "IconPath": "SpriteOutput/ItemIcon/RelicIcons/IconRelic_101_2.png",
      "ItemFigureIconPath": "SpriteOutput/RelicFigures/IconRelic_101_2.png",
      "RelicName": "RelicName_31012",
      "ItemBGDesc": "ItemBGDesc_31012",
      "BGStoryTitle": "RelicStoryTitle_31012",
      "BGStoryContent": "RelicStoryContent_31012"
    },
    "BODY": {
      "SetID": 101,
      "Type": "BODY",
      "IconPath": "SpriteOutput/ItemIcon/RelicIcons/IconRelic_101_3.png",
      "ItemFigureIconPath": "SpriteOutput/RelicFigures/IconRelic_101_3.png",
      "RelicName": "RelicName_31013",
      "ItemBGDesc": "ItemBGDesc_31013",
      "BGStoryTitle": "RelicStoryTitle_31013",
      "BGStoryContent": "RelicStoryContent_31013"
    },
    "FOOT": {
      "SetID": 101,
      "Type": "FOOT",
      "IconPath": "SpriteOutput/ItemIcon/RelicIcons/IconRelic_101_4.png",
      "ItemFigureIconPath": "SpriteOutput/RelicFigures/IconRelic_101_4.png",
      "RelicName": "RelicName_31014",
      "ItemBGDesc": "ItemBGDesc_31014",
      "BGStoryTitle": "RelicStoryTitle_31014",
      "BGStoryContent": "RelicStoryContent_31014"
    }
  }
}
```

After the change:

```json
{
  "101": {
    "HEAD": {
      "SetID": 101,
      "Type": "HEAD",
      "IconPath": "SpriteOutput/ItemIcon/RelicIcons/IconRelic_101_1.png",
      "ItemFigureIconPath": "SpriteOutput/RelicFigures/IconRelic_101_1.png",
      "RelicName": "RelicName_31011",
      "ItemBGDesc": "ItemBGDesc_31011",
      "BGStoryTitle": "RelicStoryTitle_31011",
      "BGStoryContent": "RelicStoryContent_31011"
    },
    "HAND": {
      "SetID": 101,
      "Type": "HAND",
      "IconPath": "SpriteOutput/ItemIcon/RelicIcons/IconRelic_101_2.png",
      "ItemFigureIconPath": "SpriteOutput/RelicFigures/IconRelic_101_2.png",
      "RelicName": "RelicName_31012",
      "ItemBGDesc": "ItemBGDesc_31012",
      "BGStoryTitle": "RelicStoryTitle_31012",
      "BGStoryContent": "RelicStoryContent_31012"
    },
    "BODY": {
      "SetID": 101,
      "Type": "BODY",
      "IconPath": "SpriteOutput/ItemIcon/RelicIcons/IconRelic_101_3.png",
      "ItemFigureIconPath": "SpriteOutput/RelicFigures/IconRelic_101_3.png",
      "RelicName": "RelicName_31013",
      "ItemBGDesc": "ItemBGDesc_31013",
      "BGStoryTitle": "RelicStoryTitle_31013",
      "BGStoryContent": "RelicStoryContent_31013"
    },
    "FOOT": {
      "SetID": 101,
      "Type": "FOOT",
      "IconPath": "SpriteOutput/ItemIcon/RelicIcons/IconRelic_101_4.png",
      "ItemFigureIconPath": "SpriteOutput/RelicFigures/IconRelic_101_4.png",
      "RelicName": "RelicName_31014",
      "ItemBGDesc": "ItemBGDesc_31014",
      "BGStoryTitle": "RelicStoryTitle_31014",
      "BGStoryContent": "RelicStoryContent_31014"
    },
    "NECK": {
      "SetID": 9999999,
      "Type": "",
      "IconPath": "",
      "ItemFigureIconPath": "",
      "RelicName": "",
      "ItemBGDesc": "",
      "BGStoryTitle": "",
      "BGStoryContent": ""
    },
    "OBJECT": {
      "SetID": 9999999,
      "Type": "",
      "IconPath": "",
      "ItemFigureIconPath": "",
      "RelicName": "",
      "ItemBGDesc": "",
      "BGStoryTitle": "",
      "BGStoryContent": ""
    }
  }
}
```

### Character Stats

Just like with Light Cones, the Equilibrium level requirement is not present for the ascension 0 entries and thus is excluded.

To fix this:

1. Open `StarRailData\ExcelOutput\AvatarPromotionConfig.json`
2. Go to the first `PlayerLevelRequire` property you see
3. Add a new line and set it to `"WorldLevelRequire": 0,`

For example, the first entry is as follows:

```json
{
  "1001": {
    "0": {
      "AvatarID": 1001,
      "Promotion": 0,
      "PromotionCostList": [
        {
          "ItemID": 2,
          "ItemNum": 3200
        },
        {
          "ItemID": 111011,
          "ItemNum": 4
        }
      ],
      "MaxLevel": 20,
      "PlayerLevelRequire": 15,
      "AttackBase": {
        "Value": 69.6000000005588
      },
      "AttackAdd": {
        "Value": 3.480000000447035
      },
      "DefenceBase": {
        "Value": 78
      },
      "DefenceAdd": {
        "Value": 3.9000000008381903
      },
      "HPBase": {
        "Value": 144
      },
      "HPAdd": {
        "Value": 7.2000000001862645
      },
      "SpeedBase": {
        "Value": 101
      },
      "CriticalChance": {
        "Value": 0.05000000004656613
      },
      "CriticalDamage": {
        "Value": 0.5000000004656613
      },
      "BaseAggro": {
        "Value": 150
      }
    }
  }
}
```

After the change:

```json
{
  "1001": {
    "0": {
      "AvatarID": 1001,
      "Promotion": 0,
      "PromotionCostList": [
        {
          "ItemID": 2,
          "ItemNum": 3200
        },
        {
          "ItemID": 111011,
          "ItemNum": 4
        }
      ],
      "MaxLevel": 20,
      "PlayerLevelRequire": 15,
      "WorldLevelRequire": 0,
      "AttackBase": {
        "Value": 69.6000000005588
      },
      "AttackAdd": {
        "Value": 3.480000000447035
      },
      "DefenceBase": {
        "Value": 78
      },
      "DefenceAdd": {
        "Value": 3.9000000008381903
      },
      "HPBase": {
        "Value": 144
      },
      "HPAdd": {
        "Value": 7.2000000001862645
      },
      "SpeedBase": {
        "Value": 101
      },
      "CriticalChance": {
        "Value": 0.05000000004656613
      },
      "CriticalDamage": {
        "Value": 0.5000000004656613
      },
      "BaseAggro": {
        "Value": 150
      }
    }
  }
}
```