package org.example.DAO;

import org.example.entity.Animal;
import org.example.utils.DataBaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class AnimalDAO extends BaseDAO<Animal> {
    public AnimalDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Animal save(Animal element) throws SQLException {
        try {
            _connection = DataBaseManager.getConnection();
            request = "INSERT INTO Animal (nom, race, description, age) VALUE (?,?,?,?)";
            preparedStatement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, element.getNom());
            preparedStatement.setString(2, element.getRace());
            preparedStatement.setString(3, element.getDescription());
            preparedStatement.setInt(4, element.getAge());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            Animal animal = null;
            if (resultSet.next()) {
                animal = Animal.builder().nom(element.getNom()).race(element.getRace()).description(element.getDescription()).age(element.getAge()).build();
            }
            _connection.commit();
            return animal;
        } catch (SQLException e) {
            _connection.rollback();
            return null;
        } finally {
            close();
        }

    }

    public Animal getByNom(String nom) throws SQLException {
        try{
            _connection = DataBaseManager.getConnection();
            request = "SELECT * FROM Animal WHERE nom = ?";
            preparedStatement = _connection.prepareStatement(request);
            preparedStatement.setString(1, nom);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return Animal.builder().nom(resultSet.getString("nom"))
                        .id_animal(resultSet.getInt("id_animal"))
                        .race(resultSet.getString("race"))
                        .description(resultSet.getString("description"))
                        .age(resultSet.getInt("age")).build();
            }
            return null;
        }catch (SQLException e){
            return null;
        }finally {
            close();
        }
    }

    public List<Animal> getAnimalsByRace(String race) throws SQLException {
            try {
                _connection = DataBaseManager.getConnection();
                List<Animal> animals = new ArrayList<>();
                request = "SELECT * FROM Animal WHERE race = ?";
                preparedStatement = _connection.prepareStatement(request);
                preparedStatement.setString(1, race);
                resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    animals.add(Animal.builder().nom(resultSet.getString("nom"))
                            .race(resultSet.getString("race"))
                            .description(resultSet.getString("description"))
                            .age(resultSet.getInt("age")).build());
                }
                return animals;
            }catch (SQLException e){
                return  null;
            }finally {
                close();
            }
    }

    public List<Animal> getAnimalsByNom(String nom) throws SQLException {
        try {
            _connection = DataBaseManager.getConnection();
            List<Animal> animals = new ArrayList<>();
            request = "SELECT * FROM Animal WHERE nom = ?";
            preparedStatement = _connection.prepareStatement(request);
            preparedStatement.setString(1, nom);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                animals.add(Animal.builder().nom(resultSet.getString("nom"))
                        .race(resultSet.getString("race"))
                        .description(resultSet.getString("description"))
                        .age(resultSet.getInt("age")).build());
            }
            return animals;
        }catch (SQLException e){
            return  null;
        }finally {
            close();
        }
    }

    public List<Animal> getAnimalsByAge(int age) throws SQLException {
        try {
            _connection = DataBaseManager.getConnection();
            List<Animal> animals = new ArrayList<>();
            request = "SELECT * FROM Animal WHERE age = ?";
            preparedStatement = _connection.prepareStatement(request);
            preparedStatement.setInt(1, age);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                animals.add(Animal.builder().nom(resultSet.getString("nom"))
                        .race(resultSet.getString("race"))
                        .description(resultSet.getString("description"))
                        .age(resultSet.getInt("age")).build());
            }
            return animals;
        }catch (SQLException e){
            return  null;
        }finally {
            close();
        }
    }



}
