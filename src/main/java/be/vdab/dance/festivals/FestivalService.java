package be.vdab.dance.festivals;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional (readOnly = true)
public class FestivalService {
    private final FestivalRepository festivalRepository;

    public FestivalService(FestivalRepository festivalRepository) {
        this.festivalRepository = festivalRepository;
    }
    public List<Festival> findAll() {
        return festivalRepository.findAll();
    }

    public List<Festival> findUitverkocht() {
        return festivalRepository.findUitverkocht();
    }
    @Transactional
    public void delete(long id) {
        festivalRepository.delete(id);
    }
    @Transactional
    public long create(Festival festival) {
        return festivalRepository.create(festival);
    }
    @Transactional
    public void anuleer(long id) {
        Festival festival = festivalRepository.findAndLockById(id)
                .orElseThrow(() -> new FestivalNietGevondenException(id));
        festivalRepository.delete(id);
        BigDecimal teVerdelenReclameBudget = festival.getReclameBudget();
        BigDecimal aantalResterendeFestivals = BigDecimal.valueOf(festivalRepository.findAantal());
        BigDecimal extraReclameBudgetPerFestival = teVerdelenReclameBudget.divide(aantalResterendeFestivals,2, RoundingMode.HALF_UP);
        festivalRepository.verhoogBudget(extraReclameBudgetPerFestival);
    }
}
