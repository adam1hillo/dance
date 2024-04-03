package be.vdab.dance.festivals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class FestivalTest {

    private Festival festival;
    @BeforeEach
    void beforeEach() {
        festival = new Festival(1, "Test", 100, BigDecimal.valueOf(10000));
    }

    @Test
    void boekenWijzigdHetAantalBeschikbareTickets() {
        festival.boek(100);
        assertThat(festival.getTicketsBeschikbaar()).isZero();
    }
    @Test
    void boekenMisluktBijNulGevraagdeTickets() {
        assertThatIllegalArgumentException().isThrownBy(() -> festival.boek(0));
    }
    @Test
    void boekenMisluktBijNegatiefAantalGevraagdeTickets() {
        assertThatIllegalArgumentException().isThrownBy(() -> festival.boek(-5));
    }
    @Test
    void boekenMisluktBijOnvoldoendeBeschikbaarTickets() {
        assertThatExceptionOfType(OnvoldoendeTicketsBeschikbaarException.class).isThrownBy(() -> festival.boek(101));
    }

}
