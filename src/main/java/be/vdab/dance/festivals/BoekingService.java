package be.vdab.dance.festivals;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BoekingService {
    private final BoekingRepository boekingRepository;
    private final FestivalRepository festivalRepository;

    public BoekingService(BoekingRepository boekingRepository, FestivalRepository festivalRepository) {
        this.boekingRepository = boekingRepository;
        this.festivalRepository = festivalRepository;
    }
    @Transactional
    public void create(Boeking boeking) {
        Festival festival = festivalRepository.findAndLockById(boeking.getFestivalId())
                .orElseThrow(() -> new FestivalNietGevondenException(boeking.getFestivalId()));
        festival.boek(boeking.getAantalTickets());
        festivalRepository.update(festival);
        boekingRepository.create(boeking);
    }
    public List<BoekingMetFestival> findBoekingenMetFestivals() {
        return boekingRepository.findBoekingenMetFestivals();
    }
    @Transactional
    public void annuleer(long id) {
        Boeking boeking = boekingRepository.findAndLockById(id)
                .orElseThrow(() -> new BoekingNietGevondenException(id));
        Festival festival = festivalRepository.findAndLockById(boeking.getFestivalId())
                .orElseThrow(() -> new FestivalNietGevondenException(boeking.getFestivalId()));
        festival.annuleerBoeking(boeking.getAantalTickets());
        festivalRepository.update(festival);
        boekingRepository.delete(id);
    }
    public List<AantalBoekingenPerFestival> findAantalBoekingenPerFestival() {
        return boekingRepository.findAantalBoekingenPerFestival();
    }
}
