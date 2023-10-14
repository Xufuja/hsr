package dev.xfj.player;

import dev.xfj.database.Database;
import dev.xfj.relic.Relic;

public class RelicPiece extends Data {
    private final Relic relic;

    public RelicPiece(int relicId) {
        super();
        this.relic = Database.getRelics().get(relicId);
    }

    public boolean levelUp(int exp) {
        return super.levelUp(relic, exp);
    }

    public Relic getRelic() {
        return relic;
    }
}
