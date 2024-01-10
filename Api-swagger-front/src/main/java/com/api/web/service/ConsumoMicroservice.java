package com.api.web.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;



import com.api.web.model.Persona;

@Service
public class ConsumoMicroservice {

    
    private final RestTemplate restTemplate;

    @Autowired
    public ConsumoMicroservice(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    private final String BASE_URL = "http://localhost:8081";

    public List<Persona> obtenerListaDePersonas() {
        String url = BASE_URL + "/lista";
        // Realiza una solicitud GET al microservicio y obtiene el resultado como un array de Personas
        Persona[] personasArray = restTemplate.getForObject(url, Persona[].class);
        return Arrays.asList(personasArray);
    }

    public Persona agregarPersona(Persona persona) {
        String url = BASE_URL + "/agregar";
        return restTemplate.postForObject(url, persona, Persona.class);
    }

    public boolean eliminarPersona(Long id) {
        String url = BASE_URL + "/eliminar/{id}";
        restTemplate.delete(url, id);
        return true; 
    }

    public ResponseEntity<Persona> editarPersona(Long id, Persona persona) {
        String url = BASE_URL + "/editar/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Persona> requestEntity = new HttpEntity<>(persona, headers);

        ResponseEntity<Persona> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                requestEntity,
                Persona.class
        );

        return responseEntity;
    }


}