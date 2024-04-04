package be.vdab.dance;

import be.vdab.dance.festivals.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;


@Component
public class MyRunner implements CommandLineRunner {

    private final BoekingService boekingService;

    public MyRunner(BoekingService boekingService) {
        this.boekingService = boekingService;
    }

    @Override
    public void run(String... args) {
        /*Scanner scanner = new Scanner(System.in);
        System.out.println("Typ uw naam:");
        String naam = scanner.nextLine();
        System.out.println("Typ aantal gewenste tickets:");
        int aantalTickets = scanner.nextInt();
        System.out.println("Typ id van een festival:");
        var festivalId = scanner.nextInt();
        try {
            Boeking boeking = new Boeking(0, naam, aantalTickets, festivalId);
            boekingService.create(boeking);
            System.out.println(aantalTickets + " tickets geboekt van festival met id: " + festivalId);
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
        } catch (FestivalNietGevondenException ex) {
            System.err.println("Festival " + ex.getId() + " niet gevonden.");
        } catch (OnvoldoendeTicketsBeschikbaarException ex) {
            System.err.println("Er zijn onvoldoende beschikbaare tickets");
        }*/

        /*Scanner scanner = new Scanner(System.in);
        System.out.println("Id van de boeking:");
        long id = scanner.nextLong();
        try {
            boekingService.annuleer(id);
            System.out.println("Boeking geannuleerd");
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
        } catch (BoekingNietGevondenException ex) {
            System.err.println("Boeking niet gevonden, id: " + ex.getId());
        } catch (FestivalNietGevondenException ex) {
            System.err.println("Festival niet gevonden, id: " + ex.getId());
        }*/
        boekingService.findAantalBoekingenPerFestival().forEach(System.out::println);
    }
}
