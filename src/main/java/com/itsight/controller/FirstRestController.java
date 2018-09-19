package com.itsight.controller;

import com.itsight.domain.SecurityUser;
import com.itsight.domain.Usuario;
import com.itsight.repository.SecurityUserRepository;
import com.itsight.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class FirstRestController {

    private SecurityUserRepository securityUserRepository;

    private UsuarioService usuarioService;

    @Autowired
    public FirstRestController(SecurityUserRepository securityUserRepository, UsuarioService usuarioService) {
        this.securityUserRepository = securityUserRepository;
        this.usuarioService = usuarioService;
    }

    @GetMapping(value = "/rest/list")
    public List<SecurityUser> getUsers() {
        return securityUserRepository.findAll();
    }

    @PostMapping(value = "/rest/crud")
    public SecurityUser addUser(@RequestBody SecurityUser secObj) {
        return securityUserRepository.save(secObj);
    }

    @PutMapping(value = "/rest/{username}")
    public SecurityUser updateUser(@PathVariable(value = "username") String username, @RequestBody SecurityUser secObj) {
        SecurityUser su = securityUserRepository.findByUsername(username);
        su.setUsername(secObj.getUsername());
        return securityUserRepository.save(su);
    }

    @DeleteMapping(value = "/rest/{username}")
    public List<SecurityUser> deleteUser(@PathVariable(value = "username") String username) {
        securityUserRepository.delete(securityUserRepository.findByUsername(username));
        return securityUserRepository.findAll();
    }

    @ExceptionHandler(NullPointerException.class)
    public void nullPointerException(HttpServletResponse response, HttpServletRequest request, NullPointerException e) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), request.getServletPath().split("/")[2] + " not found");
    }

    @GetMapping(value = "/pick/{id}")
    public @ResponseBody
    Usuario getUserById(@PathVariable int id) {
        if (usuarioService == null) {
            System.out.println(">> NULL");
        }
        return usuarioService.findOne(id);
    }

    @GetMapping(value = "/pick/s/{nombreCompleto}")
    public @ResponseBody
    List<Usuario> findNombreCompleto(@PathVariable String nombreCompleto) {
        if (usuarioService == null) {
            System.out.println(">> NULL");
        }
        return usuarioService.findByNombreCompleto(nombreCompleto);
    }

}
