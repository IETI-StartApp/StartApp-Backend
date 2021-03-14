package edu.escuelaing.ieti.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/proyects")
public class ProyectController {

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> obtenerUsuario(@PathVariable int id) {
		return new ResponseEntity<>("Prueba", HttpStatus.ACCEPTED);
	}
}
