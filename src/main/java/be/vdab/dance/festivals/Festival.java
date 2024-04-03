package be.vdab.dance.festivals;

import java.math.BigDecimal;

public class Festival {
    private final long id;
    private final String naam;
    private int ticketsBeschikbaar;
    private final BigDecimal reclameBudget;

    public Festival(long id, String naam, int ticketsBeschikbaar, BigDecimal reclameBudget) {
        this.id = id;
        this.naam = naam;
        this.ticketsBeschikbaar = ticketsBeschikbaar;
        this.reclameBudget = reclameBudget;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public long getTicketsBeschikbaar() {
        return ticketsBeschikbaar;
    }

    public BigDecimal getReclameBudget() {
        return reclameBudget;
    }
    public void boek(int aantalTickets) {
        if (aantalTickets <= 0) {
            throw new IllegalArgumentException("Aantal tickets moet positief zijn");
        }
        if (aantalTickets > ticketsBeschikbaar) {
            throw new OnvoldoendeTicketsBeschikbaarException();
        }
        ticketsBeschikbaar -= aantalTickets;
    }
}
