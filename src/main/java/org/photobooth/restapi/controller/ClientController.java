package org.photobooth.restapi.controller;

import org.entityframework.dev.ApiResponse;
import org.photobooth.restapi.model.Client;
import org.photobooth.restapi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ApplicationContext applicationContext;
    private static final Logger logger = Logger.getLogger(ClientController.class.getName());
       /*
    desc : maka ny client rehetra
    method : get
    url : .../client/all
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllClient(){
        try (ClientService clientService = new ClientService()) {
            List<Client> clients = clientService.getAllClient();
            ApiResponse apiResponse = new ApiResponse(true,clients, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*desc : get by id
method : get
url (example) : .../client/CLT_2
  */
    @GetMapping("/{ClientId}")
    public ResponseEntity<ApiResponse> getClientById(@PathVariable String clientId) {
        try (ClientService clientService = new ClientService()) {
            Optional<Client> clientOptional = clientService.getClientById(clientId);
            if (clientOptional.isPresent()) {
                Client client= clientOptional.get();
                ApiResponse response = new ApiResponse(true, client, null);
                return ResponseEntity.ok(response);
            } else {
                String errorMessage = "Materiel not found : " + clientId;
                ApiResponse response = new ApiResponse(false, null, errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*desc : mi_inserte materiel vaovao anaty base de donne
    method : post
    url : .../client/save
    body (example) :
    {
             "nom":tantandraza,
            "prenom": "noov aaa",
            "email": tatandraza@gmail.com,
            num_telephone:0342220215,
    }
    */
    @PostMapping("/save")
    public ResponseEntity<ApiResponse> createClient(@RequestBody Client client ) {
        try (ClientService clientService = new ClientService()) {
            String idClientCreated = clientService.save(client);
            ApiResponse response = new ApiResponse(true,idClientCreated, "done");
            logger.info("new Client inserted");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
 desc : update
 method : put
 url : .../client/update
 body (example) :
  {         "id_Client"=CLT_1,
            "nom":tantandraza,
            "prenom": "noov aaa",
            "email": tatandraza@gmail.com,
            num_telephone:0342220215,

}
  */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateClient(@RequestBody Client client) {
        try (ClientService clientService = new ClientService()) {
            clientService.update(client);
            ApiResponse response = new ApiResponse(true,client,"done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*desc : delete
    method : delete
    url (example) : .../client/delete/CLT_2
    */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteClient(@PathVariable String id) {
        try (ClientService clientService = new ClientService()) {
            clientService.delete(id);
            ApiResponse response = new ApiResponse(true,id, "done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
}
