package be.vdab.dance.festivals;

public class Boeking {
    private final int id;
    private final String naam;
    private final int aantalTickets;
    private final long festivalId;

    public Boeking(int id, String naam, int aantalTickets, long festivalId) {
        if (naam.isBlank()) {
            throw new IllegalArgumentException("Naam mag niet leeg zijn");
        }
        if (aantalTickets <= 0) {
            throw new IllegalArgumentException("Aantal tickets moet positief zijn");
        }
        if (festivalId <= 0) {
            throw new IllegalArgumentException("Festival id moet positief zijn");
        }
        this.id = id;
        this.naam = naam;
        this.aantalTickets = aantalTickets;
        this.festivalId = festivalId;
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public int getAantalTickets() {
        return aantalTickets;
    }

    public long getFestivalId() {
        return festivalId;
    }
}
