package com.agustini.app.controller;

import com.agustini.app.service.UsuarioService;
import com.agustini.app.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/{tenantid}/api/usuarios")
// Todos los controladores deben tener el tenant en primer lugar
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @GetMapping
    Page<Usuario> findAll(Pageable page) {
        return service.findAll(page);
    }

    @GetMapping(value = "/{id}")
    Usuario findById(@PathVariable long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Usuario save(@RequestBody Usuario entity) {
        return service.saveOrUpdate(entity);
    }

    @PutMapping
    Usuario update(@RequestBody Usuario entity) {
        return service.saveOrUpdate(entity);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
