package be.vdab.dance.festivals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(BoekingRepository.class)
@Sql({"/festivals.sql", "/boekingen.sql"})
class BoekingRepositoryTest {

    private final JdbcClient jdbcClient;
    private final BoekingRepository boekingRepository;
    private static final String BOEKINGEN_TABLE = "boekingen";

     BoekingRepositoryTest(JdbcClient jdbcClient, BoekingRepository boekingRepository) {
        this.jdbcClient = jdbcClient;
        this.boekingRepository = boekingRepository;
    }

    private long idVanTestFestival1() {
        return jdbcClient.sql("select id from festivals where naam = 'test1'")
                .query(Long.class)
                .single();
    }

    @Test
    void createVoegtEenBoekingToe() {
         long idTest1 = idVanTestFestival1();
         boekingRepository.create(new Boeking(0, "Test", 5, idTest1));
         var aantalRecords = JdbcTestUtils.countRowsInTableWhere(jdbcClient, BOEKINGEN_TABLE,
                 "aantalTickets = 5 and naam = 'Test' and festivalId = " + idTest1);
         assertThat(aantalRecords).isOne();
    }
    @Test
    void findBoekingMetFestivalsVindtJuisteData() {
        List<BoekingMetFestival> boekingen = boekingRepository.findBoekingenMetFestivals();
        var aantalRecords = JdbcTestUtils.countRowsInTable(jdbcClient, BOEKINGEN_TABLE);
        assertThat(boekingen).hasSize(aantalRecords)
                .extracting(BoekingMetFestival::id).isSorted();
        assertThat(boekingen).anySatisfy(eenItem -> {
            assertThat(eenItem.boekingNaam()).isEqualTo("boekingTest1");
            assertThat(eenItem.festivalNaam()).isEqualTo("test1");
            assertThat(eenItem.aantalTickets()).isOne();
        });
        assertThat(boekingen).anySatisfy(eenItem -> {
           assertThat(eenItem.boekingNaam()).isEqualTo("boekingTest2");
           assertThat(eenItem.festivalNaam()).isEqualTo("test2");
           assertThat(eenItem.aantalTickets()).isEqualTo(2);
        });
        //andere manier
        var rijMetTest1Boeking =
                boekingen.stream()
                        .filter(boeking -> boeking.boekingNaam().equals("boekingTest1"))
                        .findFirst()
                        .get();
        assertThat(rijMetTest1Boeking.aantalTickets()).isOne();
        assertThat(rijMetTest1Boeking.festivalNaam()).isEqualTo("test1");
    }
}
