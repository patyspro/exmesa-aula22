package com.digitalhouse.clinicaodonto;

import com.digitalhouse.clinicaodonto.model.Endereco;
import com.digitalhouse.clinicaodonto.model.Paciente;
import com.digitalhouse.clinicaodonto.service.EnderecoService;
import com.digitalhouse.clinicaodonto.service.PacienteService;
import com.digitalhouse.clinicaodonto.service.impl.EnderecoServiceImpl;
import com.digitalhouse.clinicaodonto.service.impl.PacienteServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.util.Date;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(JUnit4.class)
public class PacienteServiceTest {
    private static PacienteService pacienteService = new PacienteService(new PacienteServiceImpl(new EnderecoServiceImpl()));
    private EnderecoService enderecoService = new EnderecoService(new EnderecoServiceImpl());

    @BeforeClass
    public static void carregarDados() {
        Endereco endereco = new Endereco("Av Santa fe", "444", "São Paulo", "São Paulo");
        Paciente p = pacienteService.cadastrar(new Paciente("Vicente", "Santos", "88888888", new Date(), endereco));
        Endereco endereco1 = new Endereco("Av das Margaridas", "333", "Belo Horizonte", "Minas Gerais");
        Paciente p1 = pacienteService.cadastrar(new Paciente("Marcela", "Moura", "99999999", new Date(), endereco1));

    }

    @Test
    public void cadastrarTest() {
        Endereco endereco = new Endereco("Rua sem Saida", "123", "São Paulo", "São Paulo");
        Paciente p = pacienteService.cadastrar(new Paciente("Tiago", "Pereira", "12345678", new Date(), endereco));

        Assert.assertNotNull(pacienteService.buscar(p.getId()));
    }

    @Test
    public void eliminarPacienteTest() {
        pacienteService.excluir(3);
        Assert.assertTrue(pacienteService.buscar(3).isEmpty());

    }

    @Test
    public void buscarTodosTest() {
        List<Paciente> pacientes = pacienteService.buscarTodos();
        Assert.assertTrue(!pacientes.isEmpty());
        Assert.assertTrue(pacientes.size() == 2);
        System.out.println(pacientes);
    }


}
