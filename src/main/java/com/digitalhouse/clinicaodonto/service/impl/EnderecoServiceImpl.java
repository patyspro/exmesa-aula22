package com.digitalhouse.clinicaodonto.service.impl;

import com.digitalhouse.clinicaodonto.configuration.ConfigurationJDBC;
import com.digitalhouse.clinicaodonto.model.Endereco;
import com.digitalhouse.clinicaodonto.service.IDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnderecoServiceImpl implements IDao<Endereco> {

    private ConfigurationJDBC configuracionJDBC;

    public EnderecoServiceImpl() {
        this.configuracionJDBC = new ConfigurationJDBC();
    }

    @Override
    public Endereco cadastrar(Endereco endereco) {
        Connection connection = configuracionJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = String.format("INSERT INTO ENDERECO(RUA, NUMERO, CIDADE, ESTADO) VALUES ('%s','%s','%s','%s')",
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getCidade(),
                endereco.getEstado());
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next())
                endereco.setId(rs.getInt(1));
            stmt.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return endereco;
    }

    @Override
    public Optional<Endereco> buscar(Integer id) {
        Connection connection = configuracionJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = String.format("SELECT ID, RUA, NUMERO, CIDADE, ESTADO FROM ENDERECO WHERE ID = '%s'", id);
        Endereco endereco = null;
        try {
            stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                endereco = criarEndereco(result);
            }

            stmt.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  endereco != null ? Optional.of(endereco) : Optional.empty();
    }

    @Override
    public void excluir(Integer id) {
        Connection connection = configuracionJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = String.format("DELETE FROM ENDERCO WHERE ID = %s", id);
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
    public List<Endereco> buscarTodos() {
        Connection connection = configuracionJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = "SELECT *  FROM ENDERECO";
        List<Endereco> domicilios = new ArrayList<>();
        try {
            stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                domicilios.add(criarEndereco(result));
            }

            stmt.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return domicilios;
    }

    @Override
    public Endereco atualizar (Endereco endereco){
        Connection connection = configuracionJDBC.conectarComBancoDeDados();
        String query = String.format("UPDATE ENDERECO SET RUA = '%s', NUMERO = '%s', CIDADE = '%s', ESTADO = '%s' WHERE ID = '%s'",
                endereco.getRua(), endereco.getNumero(), endereco.getCidade(), endereco.getEstado(), endereco.getId());
        execute(connection, query);
        return endereco;
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

    private Endereco criarEndereco(ResultSet result) throws SQLException {
        Integer id = result.getInt("ID");
        String rua = result.getString("RUA");
        String numero = result.getString("NUMERO");
        String cidade = result.getString("CIDADE");
        String estado = result.getString("ESTADO");
        return new Endereco(id, rua, numero, cidade, estado);

    }

}
