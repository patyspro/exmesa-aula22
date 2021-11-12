package com.digitalhouse.clinicaodonto.service.impl;

import com.digitalhouse.clinicaodonto.configuration.ConfigurationJDBC;
import com.digitalhouse.clinicaodonto.model.Endereco;
import com.digitalhouse.clinicaodonto.model.Paciente;
import com.digitalhouse.clinicaodonto.service.IDao;
import com.digitalhouse.clinicaodonto.util.Util;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PacienteServiceImpl implements IDao<Paciente> {

    private ConfigurationJDBC configurationJDBC;
    private EnderecoServiceImpl enderecoServiceImpl;
    final static Logger log = Logger.getLogger(PacienteServiceImpl.class);

    public PacienteServiceImpl(EnderecoServiceImpl enderecoServiceImpl) {
        this.configurationJDBC = new ConfigurationJDBC();
        this.enderecoServiceImpl = enderecoServiceImpl;
    }

    @Override
    public Paciente cadastrar(Paciente paciente) {
        log.debug("Registrando paciente : " + paciente.toString());
        Connection connection = configurationJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        paciente.setEndereco(enderecoServiceImpl.cadastrar(paciente.getEndereco()));
        String query = String.format("INSERT INTO PACIENTE  (NOME, SOBRENOME, RG, DATA_CADASTRO, ENDERECO_ID) VALUES ('%s','%s','%s','%s','%s')",
                paciente.getNome(),
                paciente.getSobrenome(),
                paciente.getRg(),
                Util.dateToTimestamp(paciente.getDataCadastro()),
                paciente.getEndereco().getId());
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next())
                paciente.setId(keys.getInt(1));
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return paciente;
    }

    @Override
    public Optional<Paciente> buscar(Integer id) {
        log.debug("Buscando paciente com id  : " + id);
        Connection connection = configurationJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = String.format("SELECT ID, NOME, SOBRENOME, RG, DATA_CADASTRO, ENDERECO_ID  FROM PACIENTE WHERE ID = '%s'", id);
        Paciente paciente = null;
        try {
            stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {

                paciente = criarPaciente(result);
            }

            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return paciente != null ? Optional.of(paciente) : Optional.empty();
    }

    @Override
    public void excluir(Integer id) {
        log.debug("Eliminando paciente con id  : " + id);
        Connection connection = configurationJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = String.format("DELETE FROM PACIENTE WHERE ID = %s", id);
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Paciente> buscarTodos() {
        log.debug("Buscando todos os pacientes");
        Connection connection = configurationJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = "SELECT *  FROM PACIENTE";
        List<Paciente> pacientes = new ArrayList<>();
        try {
            stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {

                pacientes.add(criarPaciente(result));

            }

            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return pacientes;
    }

    @Override
    public Paciente atualizar (Paciente paciente) {
        log.debug("Atualizando um paciente: " + paciente.toString());
        Connection connection = configurationJDBC.conectarComBancoDeDados();
        if(paciente.getEndereco() != null && paciente.getId() != null)
            enderecoServiceImpl.atualizar(paciente.getEndereco());

        String query = String.format("UPDATE PACIENTE SET NOME = '%s', SOBRENOME = '%s', RG = '%s' WHERE ID = '%s'",
                paciente.getNome(), paciente.getSobrenome(), paciente.getRg(), paciente.getId());
        execute(connection, query);
        return paciente;
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

    private Paciente criarPaciente(ResultSet result) throws SQLException {

        Integer idPaciente = result.getInt("ID");
        String nome = result.getString("NOME");
        String sobrenome = result.getString("SOBRENOME");
        String rg = result.getString("RG");
        Date dataCadastro = result.getDate("DATA_CADASTRO");
        Endereco endereco = enderecoServiceImpl.buscar(result.getInt("ENDERECO_ID")).orElse(null);
        return new Paciente(idPaciente, nome, sobrenome, rg, dataCadastro, endereco);

    }

}
