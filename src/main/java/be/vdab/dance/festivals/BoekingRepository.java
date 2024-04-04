package be.vdab.dance.festivals;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BoekingRepository {
    private final JdbcClient jdbcClient;

    public BoekingRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void create(Boeking boeking) {
        String sql = """
                insert into boekingen (naam, aantalTickets, festivalId)
                values (?, ?, ?)
                """;
        jdbcClient.sql(sql)
                .params(boeking.getNaam(), boeking.getAantalTickets(), boeking.getFestivalId())
                .update();
    }
    public List<BoekingMetFestival> findBoekingenMetFestivals() {
        String sql = """
                select boekingen.id, boekingen.naam as boekingNaam, festivals.naam as festivalNaam, aantalTickets
                from boekingen inner join festivals
                on boekingen.festivalId = festivals.id
                order by boekingen.id
                """;
        return jdbcClient.sql(sql)
                .query(BoekingMetFestival.class)
                .list();
    }
    public Optional<Boeking> findAndLockById(long id) {
        String sql = """
                select id, naam, aantalTickets, festivalId
                from boekingen
                where id = ?
                for update
                """;
        return jdbcClient.sql(sql)
                .param(id)
                .query(Boeking.class)
                .optional();
    }
    public void delete(long id) {
        String sql = """
                delete from boekingen
                where id = ?
                """;
        jdbcClient.sql(sql)
                .param(id)
                .update();
    }
    public List<AantalBoekingenPerFestival> findAantalBoekingenPerFestival() {
        String sql = """
                select festivalId as id, festivals.naam as naam, count(*) as aantalBoekingen
                from boekingen
                inner join festivals
                on boekingen.festivalId = festivals.id
                group by festivalId
                order by id
                """;
        return jdbcClient.sql(sql)
                .query(AantalBoekingenPerFestival.class)
                .list();
    }
}
