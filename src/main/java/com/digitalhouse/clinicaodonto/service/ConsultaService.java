package com.digitalhouse.clinicaodonto.service;

import com.digitalhouse.clinicaodonto.model.Consulta;

import java.util.List;
import java.util.Optional;

public class ConsultaService {



    private IDao<Consulta> consultaIDao;

    public ConsultaService(IDao<Consulta> consultaIDao) {
        this.consultaIDao = consultaIDao;
    }

    public Consulta cadastrar(Consulta consulta){
        return consultaIDao.cadastrar(consulta);

    }

    public List<Consulta> buscartodos(){
        return consultaIDao.buscarTodos();
    }
    public void excluir(Integer id){
        consultaIDao.excluir(id);
    }
    public Consulta atualizar (Consulta consulta){
        return consultaIDao.atualizar(consulta);
    }
    public Optional<Consulta> buscar(Integer id){
        return consultaIDao.buscar(id);
    }
}
