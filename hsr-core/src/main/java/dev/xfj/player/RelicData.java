package dev.xfj.player;

public class RelicData {
    private RelicPiece head;
    private RelicPiece hand;
    private RelicPiece body;
    private RelicPiece feet;
    private RelicPiece planarSphere;
    private RelicPiece linkRope;

    public RelicPiece getHead() {
        return head;
    }

    public void setHead(RelicPiece head) {
        if (head.getRelic().type().equals("HEAD")) {
            this.head = head;
        }
    }

    public RelicPiece getHand() {
        return hand;
    }

    public void setHand(RelicPiece hand) {
        if (head.getRelic().type().equals("HAND")) {
            this.hand = hand;
        }
    }

    public RelicPiece getBody() {
        return body;
    }

    public void setBody(RelicPiece body) {
        if (head.getRelic().type().equals("BODY")) {
            this.body = body;
        }
    }

    public RelicPiece getFeet() {
        return feet;
    }

    public void setFeet(RelicPiece feet) {
        if (head.getRelic().type().equals("FEET")) {
            this.feet = feet;
        }
    }

    public RelicPiece getPlanarSphere() {
        return planarSphere;
    }

    public void setPlanarSphere(RelicPiece planarSphere) {
        if (head.getRelic().type().equals("NECK")) {
            this.planarSphere = planarSphere;
        }
    }

    public RelicPiece getLinkRope() {
        return linkRope;
    }

    public void setLinkRope(RelicPiece linkRope) {
        if (head.getRelic().type().equals("NECK")) {
            this.linkRope = linkRope;
        }
    }
}
