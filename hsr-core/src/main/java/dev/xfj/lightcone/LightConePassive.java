package dev.xfj.lightcone;

import java.util.List;
import java.util.Map;

public class LightConePassive {
    private int skillId;
    private String name;
    private String description;
    private String abilityName;
    private List<Double> parameters;
    private List<Map<String, Double>> abilityProperties;

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

    public List<Double> getParameters() {
        return parameters;
    }

    public void setParameters(List<Double> parameters) {
        this.parameters = parameters;
    }

    public List<Map<String, Double>> getAbilityProperties() {
        return abilityProperties;
    }

    public void setAbilityProperties(List<Map<String, Double>> abilityProperties) {
        this.abilityProperties = abilityProperties;
    }
}
