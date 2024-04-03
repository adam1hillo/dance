package be.vdab.dance.festivals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@JdbcTest
@Import({BoekingService.class, BoekingRepository.class, FestivalRepository.class})
@Sql("/festivals.sql")
class BoekingServiceIntegrationTest {

    private final JdbcClient jdbcClient;
    private final BoekingService boekingService;
    private static final String FESTIVALS_TABLE = "festivals";
    private static final String BOEKINGEN_TABLE = "boekingen";


    BoekingServiceIntegrationTest(JdbcClient jdbcClient, BoekingService boekingService) {
        this.jdbcClient = jdbcClient;
        this.boekingService = boekingService;
    }
    long idFestivalTest1() {
        return jdbcClient.sql("select id from festivals where naam = 'test1'")
                .query(Long.class)
                .single();
    }

    @Test
    void createVoegtEenBoekingToeEnWizigtAantalBeschikbaareTickets() {
        long festivalId = idFestivalTest1();
        boekingService.create(new Boeking(0, "testBoeking", 5, festivalId));
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, FESTIVALS_TABLE,
                "ticketsBeschikbaar = 5 and id = " + festivalId)).isOne();
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, BOEKINGEN_TABLE,
                "aantalTickets = 5 and festivalId = " + festivalId)).isOne();

    }
    @Test
    void boekingMetOnbestaandeFestivalMislukt() {
        assertThatExceptionOfType(FestivalNietGevondenException.class).isThrownBy(
                () -> boekingService.create(new Boeking(0, "testBoeking", 5, Long.MAX_VALUE)));
    }
    @Test
    void boekingMetOnvoldoendeAantalBeschikbaareTicketsMislukt() {
        assertThatExceptionOfType(OnvoldoendeTicketsBeschikbaarException.class).isThrownBy(
                () -> boekingService.create(new Boeking(0, "testBoeking", 11, idFestivalTest1())));
    }
}
