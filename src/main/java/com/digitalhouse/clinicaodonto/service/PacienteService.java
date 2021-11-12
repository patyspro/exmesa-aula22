package com.digitalhouse.clinicaodonto.service;

import com.digitalhouse.clinicaodonto.model.Paciente;

import java.util.List;
import java.util.Optional;

public class PacienteService {

    private IDao<Paciente> pacienteIDao;

    public PacienteService(IDao<Paciente> pacienteIDao) {
        this.pacienteIDao = pacienteIDao;
    }

    public Paciente cadastrar(Paciente p) {
        pacienteIDao.cadastrar(p);
        return p;
    }

    public Optional<Paciente> buscar(Integer id) {
        return pacienteIDao.buscar(id);
    }

    public List<Paciente> buscarTodos() {
        return pacienteIDao.buscarTodos();
    }

    public void excluir(Integer id) {
        pacienteIDao.excluir(id);
    }

    public Paciente atualizar (Paciente paciente){
        return pacienteIDao.atualizar(paciente);
    }
}

