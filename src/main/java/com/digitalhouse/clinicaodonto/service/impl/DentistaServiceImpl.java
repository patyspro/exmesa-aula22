package com.digitalhouse.clinicaodonto.service.impl;

import com.digitalhouse.clinicaodonto.configuration.ConfigurationJDBC;
import com.digitalhouse.clinicaodonto.model.Dentista;
import com.digitalhouse.clinicaodonto.service.IDao;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DentistaServiceImpl implements IDao<Dentista> {

    private ConfigurationJDBC configurationJDBC;
    final static Logger log = Logger.getLogger(DentistaServiceImpl.class);

    public DentistaServiceImpl() {
        this.configurationJDBC = new ConfigurationJDBC();
    }
    

    @Override
    public Dentista cadastrar(Dentista dentista) {
        log.debug("Registrando novo dentista: " + dentista.toString());
        Connection connection = configurationJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = String.format("INSERT INTO DENTISTA (NOME, SOBRENOME, MATRICULA) VALUES ('%s', '%s', '%s')",
                dentista.getNome(), dentista.getSobrenome(), dentista.getMatricula());
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next())
                dentista.setId(keys.getInt(1));
            stmt.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return dentista;
    }

    @Override
    public Optional<Dentista> buscar(Integer id) {
        log.debug("Buscando dentista com id : " + id);
        Connection connection = configurationJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = String.format("SELECT ID, NOME, SOBRENOME, MATRICULA FROM DENTISTA WHERE ID = '%s'", id);
        Dentista dentista = null;
        try {
            stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                dentista = criaDentista(result);
            }

            stmt.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return dentista != null ? Optional.of(dentista) : Optional.empty();
    }

    @Override
    public void excluir(Integer id) {
        log.debug("Excluinudo dentista com id : " + id);
        Connection connection = configurationJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = String.format("DELETE FROM DENTISTA WHERE ID = %s", id);
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public List<Dentista> buscarTodos() {
        log.debug("Buscando todos os dentistas");
        Connection connection = configurationJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = "SELECT *  FROM DENTISTA";
        List<Dentista> dentistas = new ArrayList<>();
        try {
            stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {

                dentistas.add(criaDentista(result));

            }

            stmt.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return dentistas;
    }

    @Override
    public Dentista atualizar(Dentista dentista) {
            log.debug("Atualizando dentista: " + dentista.toString());
            Connection connection = configurationJDBC.conectarComBancoDeDados();
            Statement stmt = null;
            String query = String.format("UPDATE DENTISTA SET NOME = '%s', SOBRENOME = '%s', MATRICULA = '%s' WHERE ID = '%s'",
                    dentista.getNome(), dentista.getSobrenome(), dentista.getMatricula(), dentista.getId());
      execute(connection,query);
            return dentista;
        }


    private void execute(Connection connection, String query) {
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private Dentista criaDentista(ResultSet resultSet) throws SQLException {

        return new Dentista(resultSet.getInt("ID"),
                resultSet.getString("NOME"),
                resultSet.getString("SOBRENOME"),
                resultSet.getInt("MATRICULA"));
    }

}
