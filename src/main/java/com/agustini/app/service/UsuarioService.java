package com.agustini.app.service;

import com.agustini.app.models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {

    Usuario findById(Long id);

    Page<Usuario> findAll(Pageable page);

    Usuario saveOrUpdate(Usuario entity);

    void delete(Long id);

}
