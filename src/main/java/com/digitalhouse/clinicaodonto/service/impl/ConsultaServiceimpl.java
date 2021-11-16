package com.digitalhouse.clinicaodonto.service.impl;

import com.digitalhouse.clinicaodonto.model.Consulta;
import com.digitalhouse.clinicaodonto.service.IDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultaServiceimpl implements IDao <Consulta> {

    private List<Consulta> consultas;

    public ConsultaServiceimpl() {
        this.consultas = new ArrayList<>();
    }

    @Override
    public Consulta cadastrar(Consulta consulta) {
        consultas.add(consulta);
        return consulta;
    }

    @Override
    public Optional<Consulta> buscar(Integer id) {
        return consultas.stream().filter(consulta -> consulta.getId().equals(id)).findFirst();
    }

    @Override
    public void excluir(Integer id) {
        consultas.removeIf(consulta -> consulta.getId().equals(id));
    }

    @Override
    public List<Consulta> buscarTodos() {
        return consultas;
    }

    @Override
    public Consulta atualizar(Consulta consulta) {
        excluir(consulta.getId());
        consultas.add(consulta);
        return consulta;
    }
}
