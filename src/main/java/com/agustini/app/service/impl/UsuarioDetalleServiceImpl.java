package com.agustini.app.service.impl;

import com.agustini.app.security.UserDetailsMapper;
import com.agustini.app.dao.UsuarioDao;
import com.agustini.app.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Qualifier("userDetailsService")
public class UsuarioDetalleServiceImpl implements UserDetailsService {
    @Autowired
    private UsuarioDao dao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        final Usuario retrievedUser = dao.findOneByUsername(userName);
        //para comprobar
        if (retrievedUser != null) System.out.println(retrievedUser.getUsername());
        if (retrievedUser == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return UserDetailsMapper.build(retrievedUser);
    }
}

