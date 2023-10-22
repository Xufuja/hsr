package dev.xfj.character;

public class Character {
    public CharacterData characterData;
    public LightConeData lightConeData;
    public RelicData relicData;
    public TraceData traceData;

    public Character(int avatarId, int lightConeId) {
        this.characterData = new CharacterData(avatarId);
        this.lightConeData = new LightConeData(lightConeId);
        this.relicData = new RelicData();
        this.traceData = new TraceData();
    }
}
