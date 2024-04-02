package be.vdab.dance;

import be.vdab.dance.festivals.FestivalNietGevondenException;
import be.vdab.dance.festivals.FestivalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;


@Component
public class MyRunner implements CommandLineRunner {

    private final FestivalService festivalService;

    public MyRunner(FestivalService festivalService) {
        this.festivalService = festivalService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Geef id om een festival te annuleren:");
        long id = scanner.nextLong();
        try {
            festivalService.annuleer(id);
            System.out.println("Festival geanuleerd (id: " + id + ")");
        } catch (FestivalNietGevondenException ex) {
            System.err.println("Festival niet gevonden, id: " + ex.getId());
        }
    }
}
