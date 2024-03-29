package be.vdab.dance.festivals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(FestivalRepository.class)
@Sql("/festivals.sql")
class FestivalRepositoryTest {

    private final FestivalRepository festivalRepository;
    private final JdbcClient jdbcClient;
    private static final String FESTIVALS_TABLE = "festivals";
    FestivalRepositoryTest(FestivalRepository festivalRepository, JdbcClient jdbcClient) {
        this.festivalRepository = festivalRepository;
        this.jdbcClient = jdbcClient;
    }

    @Test
    void findAllGeeftAlleFestivalsGesorteerdOpNaam() {
        var aantalRecords = JdbcTestUtils.countRowsInTable(jdbcClient, FESTIVALS_TABLE);
        assertThat(festivalRepository.findAll())
                .hasSize(aantalRecords)
                .extracting(Festival::getNaam)
                .isSortedAccordingTo(String::compareToIgnoreCase);
    }
    @Test
    void findUitVerkoochtGeeftUitverkochteFestivalsGesorteerdOpNaam() {
        var aantalRecords = JdbcTestUtils.countRowsInTableWhere(jdbcClient, FESTIVALS_TABLE, "ticketsBeschikbaar=0");
        assertThat(festivalRepository.findUitverkocht())
                .hasSize(aantalRecords)
                .isSortedAccordingTo((festival1, festival2) -> festival1.getNaam().compareToIgnoreCase(festival2.getNaam()))
                .extracting(Festival::getTicketsBeschikbaar)
                .allSatisfy(ticketsBeschikbaar -> assertThat(ticketsBeschikbaar).isZero());
    }
    private long idVanTestFestival1() {
        return jdbcClient.sql("select id from festivals where naam = 'test1'")
                .query(Long.class)
                .single();
    }
    @Test
    void deleteVerwijdertEenFestival() {
        long id = idVanTestFestival1();
        festivalRepository.delete(id);
        var aantalRecords = JdbcTestUtils.countRowsInTableWhere(jdbcClient, FESTIVALS_TABLE, "id="+id);
        assertThat(aantalRecords).isZero();
    }
    @Test
    void createVoegtEenMensToe() {
        long id = festivalRepository.create(new Festival(0, "test4", 5000, BigDecimal.valueOf(500000)));
        assertThat(id).isPositive();
        var aantalRecordsMetDeIdVanToegevoegdeFestival =
                JdbcTestUtils.countRowsInTableWhere(jdbcClient, FESTIVALS_TABLE, "id = " + id);
        assertThat(aantalRecordsMetDeIdVanToegevoegdeFestival).isOne();
    }
}
