package com.digitalhouse.clinicaodonto;

import com.digitalhouse.clinicaodonto.model.Dentista;
import com.digitalhouse.clinicaodonto.service.DentistaService;
import com.digitalhouse.clinicaodonto.service.impl.DentistaServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(JUnit4.class)
public class DentistaServiceTest {

    private static DentistaService dentistaService = new DentistaService(new DentistaServiceImpl());

    @BeforeClass
    public static void carregarDados() {
        dentistaService.cadastrar(new Dentista("Sebastião", "Luz", 3455647));
    }

    @Test
    public void cadastrarTest() {
        Dentista dentista = dentistaService.cadastrar(new Dentista("João", "Pereira", 348971960));
        Assert.assertTrue(dentista.getId() != null);
    }

    @Test
    public void excluirTest() {
        dentistaService.excluir(1);
        Assert.assertTrue(dentistaService.buscar(1).isEmpty());
    }

    @Test
    public void buscarTodosTest() {
        List<Dentista> dentistas = dentistaService.buscarTodos();
        Assert.assertTrue(!dentistas.isEmpty());
        Assert.assertTrue(dentistas.size() == 1);
        System.out.println(dentistas);
    }
}
