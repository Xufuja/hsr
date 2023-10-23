package dev.xfj.character;

import java.util.Optional;

public class RelicData {
    private RelicPiece head;
    private RelicPiece hand;
    private RelicPiece body;
    private RelicPiece feet;
    private RelicPiece planarSphere;
    private RelicPiece linkRope;

    public enum PieceType {
        HEAD("Head"),
        HAND("Hands"),
        BODY("Body"),
        FEET("Feet"),
        PLANAR_SPHERE("Planar Sphere"),
        LINK_ROPE("Link Rope");

        public final String type;

        PieceType(String type){
            this.type = type;
        }
    }

    public Optional<RelicPiece> getPiece(PieceType type) {
        return switch (type) {
            case HEAD -> Optional.ofNullable(head);
            case HAND -> Optional.ofNullable(hand);
            case BODY -> Optional.ofNullable(body);
            case FEET -> Optional.ofNullable(feet);
            case PLANAR_SPHERE -> Optional.ofNullable(planarSphere);
            case LINK_ROPE -> Optional.ofNullable(linkRope);
        };
    }

    public void setHead(RelicPiece head) {
        if (head.getRelic().type().equals(PieceType.HEAD.type)) {
            this.head = head;
        }
    }

    public void setHand(RelicPiece hand) {
        if (hand.getRelic().type().equals(PieceType.HAND.type)) {
            this.hand = hand;
        }
    }

    public void setBody(RelicPiece body) {
        if (body.getRelic().type().equals(PieceType.BODY.type)) {
            this.body = body;
        }
    }

    public void setFeet(RelicPiece feet) {
        if (feet.getRelic().type().equals(PieceType.FEET.type)) {
            this.feet = feet;
        }
    }

    public void setPlanarSphere(RelicPiece planarSphere) {
        if (planarSphere.getRelic().type().equals(PieceType.PLANAR_SPHERE.type)) {
            this.planarSphere = planarSphere;
        }
    }

    public void setLinkRope(RelicPiece linkRope) {
        if (linkRope.getRelic().type().equals(PieceType.LINK_ROPE.type)) {
            this.linkRope = linkRope;
        }
    }

    @Override
    public String toString() {
        return "RelicData{" +
                "head=" + head +
                ", hand=" + hand +
                ", body=" + body +
                ", feet=" + feet +
                ", planarSphere=" + planarSphere +
                ", linkRope=" + linkRope +
                '}';
    }
}
