package dev.xfj.system;

import dev.xfj.database.Database;
import dev.xfj.player.RelicPiece;
import dev.xfj.relic.Relic;

import java.util.*;

public class RelicGen {
    public List<Relic> getRelicsBySet(String set) {
        List<Relic> relics = new ArrayList<>();

        for (Map.Entry<Integer, Relic> relic : Database.getRelics().entrySet()) {
            if (relic.getValue().setData().setName().equals(set)) {
                relics.add(relic.getValue());
            }
        }

        return relics;
    }

    public RelicPiece createRelic(int relicId) {
        RelicPiece relicPiece = new RelicPiece(relicId);
        relicPiece.setMainStat(relicPiece.getRelic().getPossibleMainStats().get(new Random().nextInt(relicPiece.getRelic().getPossibleMainStats().size())));

        Map<String, List<Double>> subStats = new HashMap<>();
        List<String> possible = relicPiece.getRelic().getPossibleSubStats();

        int subStatCount = new Random().nextInt(3, 5);
        possible.remove(relicPiece.getMainStat());

        for (int i = 0; i < subStatCount; i++) {
            String selected = possible.get(new Random().nextInt(possible.size()));
            List<Double> list = new ArrayList<>();
            list.add(relicPiece.doRoll(selected));
            subStats.put(selected, list);
            possible.remove(selected);
        }

        relicPiece.setSubStats(subStats);

        return relicPiece;
    }
}
