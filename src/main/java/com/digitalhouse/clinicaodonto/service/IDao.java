package com.digitalhouse.clinicaodonto.service;

import java.util.List;
import java.util.Optional;

public interface IDao <T> {

    public T cadastrar(T t);
    public Optional<T> buscar(Integer id);
    public void excluir(Integer id);
    public List<T> buscarTodos();
    public T atualizar (T t);
}
