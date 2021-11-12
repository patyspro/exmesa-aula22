package com.digitalhouse.clinicaodonto.service;

import com.digitalhouse.clinicaodonto.model.Endereco;

import java.util.List;
import java.util.Optional;

public class EnderecoService {

    private IDao<Endereco> enderecoIDao;

    public EnderecoService(IDao<Endereco> domicilioDao) {
        this.enderecoIDao = domicilioDao;
    }

    public Endereco guardar(Endereco d) {
        enderecoIDao.cadastrar(d);
        return d;
    }

    public Optional<Endereco> buscar(Integer id) {
        return enderecoIDao.buscar(id);
    }

    public List<Endereco> buscarTodos() {
        return enderecoIDao.buscarTodos();
    }

    public void eliminar(Integer id) {
        enderecoIDao.excluir(id);
    }

}
