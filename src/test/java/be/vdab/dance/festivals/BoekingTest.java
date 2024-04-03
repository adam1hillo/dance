package be.vdab.dance.festivals;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class BoekingTest {

    @Test
    void eenBoekingDieLukt() {
        new Boeking(5,"Adam", 50, 3);
    }
    @Test
    void deNaamMagNietLeegZijn() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Boeking(5, "", 50, 30));
    }
    @Test
    void deNaamMagNietEnkelBlankSPaceBevatten() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Boeking(5, " ", 50, 30));
    }
    @Test
    void deNaamMagNietNullZijn() {
        assertThatNullPointerException().isThrownBy(() -> new Boeking(5, null, 50, 30));
    }
    @Test
    void nulTicketsBoekenMislukt() {
        assertThatIllegalArgumentException().isThrownBy(()-> new Boeking(5, "Adam", 0, 30));
    }
    @Test
    void eenNegatiefAantalTicketsBoekenMislukt() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Boeking(5, "Adam", -10, 30));
    }
    @Test
    void deFestivalIdMagNietNulZijn() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Boeking(5, "Adam", 50, 0));
    }
    @Test
    void deFestivalIdMagNietNegatiefZijn() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Boeking(5, "Adam", 50, -5));
    }
}
