package dev.xfj.player;

public class Player {
    public PlayerData playerData;
    public LightConeData lightConeData;
    public RelicData relicData;
    public TraceData traceData;

    public Player(int avatarId) {
        this.playerData = new PlayerData(avatarId);
        this.lightConeData = new LightConeData();
        this.relicData = new RelicData();
        this.traceData = new TraceData();
    }
}
