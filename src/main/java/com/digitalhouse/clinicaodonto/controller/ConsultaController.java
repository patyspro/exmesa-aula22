package com.digitalhouse.clinicaodonto.controller;

import com.digitalhouse.clinicaodonto.model.Consulta;
import com.digitalhouse.clinicaodonto.service.ConsultaService;
import com.digitalhouse.clinicaodonto.service.DentistaService;
import com.digitalhouse.clinicaodonto.service.PacienteService;
import com.digitalhouse.clinicaodonto.service.impl.ConsultaServiceimpl;
import com.digitalhouse.clinicaodonto.service.impl.DentistaServiceImpl;
import com.digitalhouse.clinicaodonto.service.impl.EnderecoServiceImpl;
import com.digitalhouse.clinicaodonto.service.impl.PacienteServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")

public class ConsultaController {
    private ConsultaService consultaService = new ConsultaService(new ConsultaServiceimpl());
    private DentistaService dentistaService = new DentistaService(new DentistaServiceImpl());
    private PacienteService pacienteService = new PacienteService(new PacienteServiceImpl(new EnderecoServiceImpl()));

    @PostMapping()
    public ResponseEntity<Consulta> cadastrar(@RequestBody Consulta consulta) {
        ResponseEntity<Consulta> response = null;
        if (dentistaService.buscar(consulta.getDentista().getId()).isPresent()&& pacienteService.buscar(consulta.getPaciente().getId()).isPresent()){
            response= ResponseEntity.ok(consultaService.cadastrar(consulta));
        } else{
            response= ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarTodos(@PathVariable Integer id) {
        return ResponseEntity.ok(consultaService.buscar(id).orElse(null));
    }

    @PutMapping
    public ResponseEntity<Consulta> atualizar(@RequestBody Consulta consulta) {
        ResponseEntity<Consulta> response = null;
        if (consulta.getId() != null && consultaService.buscar(consulta.getId()).isPresent())
            response = ResponseEntity.ok(consultaService.atualizar(consulta));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir (@PathVariable Integer id) {
        ResponseEntity<String> response = null;
        if (consultaService.buscar(id).isPresent()) {
            consultaService.excluir(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Consulta excluída");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> buscarTodos () {
        return ResponseEntity.ok(consultaService.buscartodos());
    }


}
