package org.example.DAO;

import org.example.entity.Animal;
import org.example.entity.Repas;
import org.example.utils.DataBaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RepasDAO extends BaseDAO<Repas> {

    private AnimalDAO animalDAO;
    public RepasDAO(Connection connection, AnimalDAO animalDAO) {
        super(connection);
        this.animalDAO = animalDAO;
    }

    @Override
    public Repas save(Repas element) throws SQLException {
        try {
            _connection = DataBaseManager.getConnection();
            request = "INSERT INTO Repas (dateHeure, nourriture, id_animal) VALUE (?,?,?)";
            preparedStatement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(element.getDateHeure()));
            preparedStatement.setString(2, element.getNourriture());
            preparedStatement.setInt(3, element.getAnimal().getId_animal());
            preparedStatement.executeUpdate();
            int nbrow = preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            Repas repas = null;
            if(resultSet.next()) {
                repas = Repas.builder().dateHeure(element.getDateHeure()).nourriture(element.getNourriture()).animal(element.getAnimal()).id_repas(resultSet.getInt(1)).build();
            }
            if(nbrow != 1){
                _connection.rollback();
            }
            _connection.commit();
            return repas;

        } catch (SQLException e) {
            _connection.rollback();
            return null;
        } finally {
            close();
        }
    }

    public List<Repas> getRepasByAnimal(int id) throws SQLException {
        try {
            _connection = DataBaseManager.getConnection();
            List<Repas> repas = new ArrayList<>();
            request = "SELECT * FROM Repas WHERE id_animal = ?";
            preparedStatement = _connection.prepareStatement(request);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                repas.add(Repas.builder()
                        .nourriture(resultSet.getString("nourriture"))
                        .dateHeure(resultSet.getTimestamp("dateHeure").toLocalDateTime())
                        .build());
            }
            return repas;
        }catch (SQLException e){
            return  null;
        }finally {
            close();
        }
    }

}
