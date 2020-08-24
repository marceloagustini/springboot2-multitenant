package com.agustini.app.service.impl;

import com.agustini.app.dao.UsuarioDao;
import com.agustini.app.models.Usuario;
import com.agustini.app.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioDao dao;

    @Override
    public Usuario findById(Long id) {
        Optional<Usuario> optional = dao.findById(id);
        if(!optional.isPresent()) {
            throw new EntityNotFoundException();
        }

        return optional.get();
    }

    @Override
    public Page<Usuario> findAll(Pageable page) {
        return dao.findAll(page);
    }

    @Override
    public Usuario saveOrUpdate(Usuario entity) {
        return dao.save(entity);
    }

    @Override
    public void delete(Long id) {
        dao.deleteById(id);
    }
}
