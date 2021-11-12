package com.digitalhouse.clinicaodonto.service;

import com.digitalhouse.clinicaodonto.model.Dentista;
import com.digitalhouse.clinicaodonto.model.Paciente;

import java.util.List;
import java.util.Optional;

public class DentistaService {

    private IDao<Dentista> odontologoDao;


    public DentistaService(IDao<Dentista> odontologoDao) {
        this.odontologoDao = odontologoDao;
    }

    public Dentista cadastrar(Dentista dentista) {
        return odontologoDao.cadastrar(dentista);

    }

    public void excluir(Integer id) {
        odontologoDao.excluir(id);
    }

    public Optional<Dentista> buscar(Integer id) {
        return odontologoDao.buscar(id);
    }

    public List<Dentista> buscarTodos() {
        return odontologoDao.buscarTodos();
    }

    public Dentista atualizar (Dentista dentista){
        return odontologoDao.atualizar(dentista);
    }
}

