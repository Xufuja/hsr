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
        if (head.getRelic().type().equals("Head")) {
            this.head = head;
        }
    }

    public RelicPiece getHand() {
        return hand;
    }

    public void setHand(RelicPiece hand) {
        if (hand.getRelic().type().equals("Hands")) {
            this.hand = hand;
        }
    }

    public RelicPiece getBody() {
        return body;
    }

    public void setBody(RelicPiece body) {
        if (body.getRelic().type().equals("Body")) {
            this.body = body;
        }
    }

    public RelicPiece getFeet() {
        return feet;
    }

    public void setFeet(RelicPiece feet) {
        if (feet.getRelic().type().equals("Feet")) {
            this.feet = feet;
        }
    }

    public RelicPiece getPlanarSphere() {
        return planarSphere;
    }

    public void setPlanarSphere(RelicPiece planarSphere) {
        if (planarSphere.getRelic().type().equals("Planar Sphere")) {
            this.planarSphere = planarSphere;
        }
    }

    public RelicPiece getLinkRope() {
        return linkRope;
    }

    public void setLinkRope(RelicPiece linkRope) {
        if (linkRope.getRelic().type().equals("Link Rope")) {
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
