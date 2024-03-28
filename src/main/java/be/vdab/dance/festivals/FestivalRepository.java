package be.vdab.dance.festivals;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FestivalRepository {
    private final JdbcClient jdbcClient;

    public FestivalRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Festival> finaAll() {
        String sql = """
                select id, naam, ticketsBeschikbaar, reclameBudget
                from festivals
                order by naam
                """;
        return jdbcClient.sql(sql)
                .query(Festival.class)
                .list();
    }

    public List<Festival> findUitverkocht() {
        String sql = """
                select id, naam, ticketsBeschikbaar, reclameBudget
                from festivals
                where ticketsBeschikbaar = 0
                order by naam
                """;
        return jdbcClient.sql(sql)
                .query(Festival.class)
                .list();
    }
    public void delete (long id) {
        String sql = """
                delete from festivals
                where id = ?
                """;
        jdbcClient.sql(sql)
                .param(id)
                .update();

    }
    public long create (Festival festival) {
        String sql = """
                insert into festivals (naam, ticketsBeschikbaar, reclameBudget)
                values (?, ?, ?)
                """;
        var keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sql)
                .params(festival.getNaam(), festival.getTicketsBeschikbaar(), festival.getReclameBudget())
                .update(keyHolder);
        return keyHolder.getKey().longValue();
    }
}
