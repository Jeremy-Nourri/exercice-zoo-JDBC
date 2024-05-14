package org.example;

import org.example.DAO.AnimalDAO;
import org.example.DAO.RepasDAO;
import org.example.entity.Animal;
import org.example.entity.Repas;
import org.example.utils.DataBaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Ihm {
    private AnimalDAO animalDAO;
    private RepasDAO repasDAO;
    private Scanner scanner;

    private Connection connection;


    public Ihm() {
        scanner = new Scanner(System.in);
        try {
            connection = DataBaseManager.getConnection();
            animalDAO = new AnimalDAO(connection);
            repasDAO = new RepasDAO(connection, animalDAO);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        int entry;
        do {
            System.out.println("1/ ajouter un animal");
            System.out.println("2/ rechercher un animal par son nom");
            System.out.println("3/ afficher les repas d'un animal");
            System.out.println("4/ rechercher des animaux par leur race");
            System.out.println("5/ rechercher des animaux par leur nom");
            System.out.println("6/ rechercher des animaux par leur age");
            System.out.println("7/ donner un repas à un animal");
            System.out.println("0/ quitter le programme");
            entry = scanner.nextInt();
            scanner.nextLine();
            switch (entry) {
                case 1:
                    ajouterUnAnimal();
                    break;
                case 2:
                    animalParNom();
                    break;
                case 3:
                    animalParNomAvecRepas();
                    break;
                case 4:
                    animauxParRace();
                    break;
                case 5:
                    animauxParNom();
                    break;
                case 6:
                    animauxParAge();
                    break;
                case 7:
                    ajouterUnRepas();
                    break;
                case 0:
                    break;
                default:
                    return;
            }
        }while(entry != 0);

    }

    private void ajouterUnAnimal() {
        System.out.println("Son nom :");
        String nom = scanner.nextLine();
        System.out.println("Sa description :");
        String description = scanner.nextLine();
        System.out.println("Sa race :");
        String race = scanner.nextLine();
        System.out.println("Son age :");
        int age = scanner.nextInt();
        scanner.nextLine();

        try {
            Animal animal = animalDAO.save(Animal.builder().nom(nom).race(race).description(description).age(age).build());
            System.out.println("L'animal a bien été ajouté au zoo :" + animal);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void animalParNomAvecRepas() {
        Animal animal = animalParNom();
        try {
            if (animal == null) {
                return;
            }
            List<Repas> repas = repasDAO.getRepasByAnimal(animal.getId_animal());
            System.out.println("Voici les repas qui lui ont été servis ");
            for(Repas colation : repas) {
                System.out.println(colation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private Animal animalParNom() {
        System.out.println("Taper le nom de l'animal :");
        String nom = scanner.nextLine();
        try {
            Animal animal = animalDAO.getByNom(nom);

            if (animal != null) {
                System.out.println("L'animal est présent dans le zoo " + animal);
                return animal;
            } else {
                System.out.println("L'animal n'est pas présent dans le zoo");
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void animauxParRace() {
        System.out.println("Entrez la race des animaux que vous souhaitez afficher :");
        String race = scanner.nextLine();
        try {
            List<Animal> animaux = animalDAO.getAnimalsByRace(race);
            if (animaux == null) {
                System.out.println("Aucun animal n'a été trouvé");
                return;
            }
            for (Animal animal : animaux) {
                System.out.println(animal);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void animauxParNom() {
        System.out.println("Entrez un nom d'animaux que vous souhaitez afficher :");
        String nom = scanner.nextLine();
        try {
            List<Animal> animaux = animalDAO.getAnimalsByNom(nom);
            if (animaux == null) {
                System.out.println("Aucun animal n'a été trouvé");
                return;
            }
            for (Animal animal : animaux) {
                System.out.println(animal);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void animauxParAge() {
        System.out.println("Entrez l'age des animaux que vous souhaitez afficher :");
        int age = scanner.nextInt();
        try {
            List<Animal> animaux = animalDAO.getAnimalsByAge(age);
            if (animaux == null) {
                System.out.println("Aucun animal n'a été trouvé");
                return;
            }
            for (Animal animal : animaux) {
                System.out.println(animal);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void ajouterUnRepas() {
        Animal animal = animalParNom();
        if (animal == null) return;
        System.out.println("Les détails de la nourriture :");
        String nourriture = scanner.nextLine();
        System.out.println("Veuillez entrer la date et l'heure :");
        System.out.println("année :");
        int annee =  scanner.nextInt();
        scanner.nextLine();
        System.out.println("mois :");
        int mois = scanner.nextInt();
        scanner.nextLine();
        System.out.println("jour :");
        int jour = scanner.nextInt();
        scanner.nextLine();
        System.out.println("heure :");
        int heure = scanner.nextInt();
        scanner.nextLine();
        System.out.println("minutes :");
        int minutes = scanner.nextInt();
        scanner.nextLine();

        LocalDateTime inputDateTime = LocalDateTime.of(annee, mois, jour, heure, minutes);

        try {
            Repas repas = repasDAO.save(Repas.builder().dateHeure(inputDateTime).nourriture(nourriture).animal(animal).build());
            System.out.println("Le repas a été enregistré :" + repas);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
