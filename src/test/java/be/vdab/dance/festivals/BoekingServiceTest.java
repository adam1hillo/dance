package be.vdab.dance.festivals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BoekingServiceTest {

    private BoekingService boekingService;
    @Mock
    private FestivalRepository festivalRepository;
    @Mock
    private BoekingRepository boekingRepository;
    private Festival festival;
    private Boeking boeking;

    @BeforeEach
    void beforeEach() {
        boekingService = new BoekingService(boekingRepository, festivalRepository);
        festival = new Festival(1, "test1", 10, BigDecimal.TEN);
        boeking = new Boeking(1, "testBoeking1", 10, 1);

    }
    @Test
    void createVoegtEenBoekingToeEnWizigtAantalBeschikbaareTickets() {
        when(festivalRepository.findAndLockById(1)).thenReturn(Optional.of(festival));
        Boeking boeking = new Boeking(0, "testNaam", 1, 1);
        boekingService.create(boeking);
        assertThat(festival.getTicketsBeschikbaar()).isEqualTo(9);
        verify(festivalRepository).findAndLockById(1);
        verify(festivalRepository).update(festival);
        verify(boekingRepository).create(boeking);
    }
    @Test
    void boekingMetOnbestaandeFestivalMislukt() {
        assertThatExceptionOfType(FestivalNietGevondenException.class).isThrownBy(
                () -> boekingService.create(new Boeking(0, "test", 10, 1)));
    }
    @Test
    void boekingMetOnvoldoendeBeschikbaareTicketsMislukt() {
        when(festivalRepository.findAndLockById(1)).thenReturn(Optional.of(festival));
        assertThatExceptionOfType(OnvoldoendeTicketsBeschikbaarException.class).isThrownBy(
                () -> boekingService.create(new Boeking(0, "test", 11, 1)));
    }
    @Test
    void annuleerVerwijdertDeBoekingEnWijzigtHetFestival() {
        when(boekingRepository.findAndLockById(1)).thenReturn(Optional.of(boeking));
        when(festivalRepository.findAndLockById(1)).thenReturn(Optional.of(festival));
        boekingService.annuleer(1);
        assertThat(festival.getTicketsBeschikbaar()).isEqualTo(20);
        verify(festivalRepository).findAndLockById(1);
        verify(festivalRepository).update(festival);
        verify(boekingRepository).findAndLockById(1);
        verify(boekingRepository).delete(1);
    }
    @Test
    void annuleerMetOnbestandeBoekingIdMislukt() {
        assertThatExceptionOfType(BoekingNietGevondenException.class).isThrownBy(
                () -> boekingService.annuleer(Long.MAX_VALUE));
    }
}
