import model.*;
import service.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Main_2 {
    public static void main(String[] args) {
        // Initializare DAO-uri
        AnimalDAO animalDAO = new AnimalDAO();
        AdoptantDAO adoptantDAO = new AdoptantDAO();
        AdoptieDAO adoptieDAO = new AdoptieDAO();
        DonatieDAO donatieDAO = new DonatieDAO();
        AngajatDAO angajatDAO = new AngajatDAO();

        // Creare tabele (Etapa II - Persistență)
        animalDAO.createTable();
        adoptantDAO.createTable();
        adoptieDAO.createTable();
        donatieDAO.createTable();
        angajatDAO.createTable();

        // Exemplu de inițializare obiecte (Etapa I - Definirea sistemului)
        Animal rex = new Caine("A_" + UUID.randomUUID().toString().substring(0, 8), "Rex", 5, "Ciobanesc");
        rex.setStareSanatate("Buna");
        rex.setEsteVaccinat(true);

        Animal mia = new Pisica("A_" + UUID.randomUUID().toString().substring(0, 8), "Mia", 2, "Europeana");
        mia.setStareSanatate("Foarte buna");
        mia.setEsteVaccinat(true);

        Animal leo = new Caine("A_" + UUID.randomUUID().toString().substring(0, 8), "Leo", 3, "Labrador");
        leo.setStareSanatate("Mediocra");
        leo.setEsteVaccinat(false);

        Adoptant maria = new Adoptant("P_" + UUID.randomUUID().toString().substring(0, 8), "Maria Popescu", "0741234567", "Str. Florilor nr. 5");
        Donatie donatie = new Donatie("D_" + UUID.randomUUID().toString().substring(0, 8), "hrana", 300.0, "Ion Vasilescu");
        Angajat angajat = new Angajat("E_" + UUID.randomUUID().toString().substring(0, 8), "Andrei Ionescu", "0755555555", "Veterinar", 4500);

        // Persistare în baza de date (Etapa II)
        animalDAO.adaugaAnimal(rex);
        animalDAO.adaugaAnimal(mia);
        animalDAO.adaugaAnimal(leo);
        AuditService.getInstance().log("adaugaAnimal");

        adoptantDAO.adaugaAdoptant(maria);
        AuditService.getInstance().log("adaugaAdoptant");

        donatieDAO.adaugaDonatie(donatie);
        AuditService.getInstance().log("adaugaDonatie");

        angajatDAO.adaugaAngajat(angajat);
        AuditService.getInstance().log("adaugaAngajat");

        // Simulare adoptie: Maria adoptă pe Rex
        Adoptie adoptie = new Adoptie("AD_" + UUID.randomUUID().toString().substring(0, 8), rex, maria);
        adoptieDAO.adaugaAdoptie(adoptie);
        AuditService.getInstance().log("adaugaAdoptie");

        // Interogări ilustrative (Etapa I)
        System.out.println("Lista animale disponibile (neadoptate):");
        List<Animal> animale = animalDAO.getAnimale();
        List<Adoptie> adoptii = adoptieDAO.getAdoptii();

        List<String> iduriAdoptate = adoptii.stream()
                .filter(a -> a.getAnimal() != null)
                .map(a -> a.getAnimal().getId())
                .collect(Collectors.toList());

        for (Animal a : animale) {
            if (!iduriAdoptate.contains(a.getId())) {
                System.out.println("Animal: " + a.getNume() + ", specie: " + a.getSpecie());
            }
        }
        AuditService.getInstance().log("listareAnimaleDisponibile");

        System.out.println("Lista donatii:");
        List<Donatie> donatii = donatieDAO.getDonatii();
        for (Donatie d : donatii) {
            System.out.println("Donatie: " + d.getTipDonatie() + " - " + d.getValoare() + " lei de la " + d.getDonatorNume());
        }
        AuditService.getInstance().log("listareDonatii");

        System.out.println("Lista angajati:");
        List<Angajat> angajati = angajatDAO.getAngajati();
        for (Angajat e : angajati) {
            System.out.println("Angajat: " + e.getNume() + " - " + e.getFunctie());
        }
        AuditService.getInstance().log("listareAngajati");
    }
}
