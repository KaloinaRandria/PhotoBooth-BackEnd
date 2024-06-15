package org.photobooth.restapi.controller;

import org.entityframework.dev.ApiResponse;
import org.photobooth.restapi.model.Role;
import org.photobooth.restapi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private ApplicationContext applicationContext;
    private static final Logger logger = Logger.getLogger(RoleController.class.getName());

    /*
    desc : maka ny role rehetra
    method : get
    url : .../role/all
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllRole() {
        try (RoleService roleService = new RoleService()) {
            List<Role> roles = roleService.getAllRole();
            ApiResponse apiResponse = new ApiResponse(true, roles, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
 desc : get by id
 method : get
 url (example) : .../role/ROLE_2
  */
    @GetMapping("/{roleId}")
    public ResponseEntity<ApiResponse> getRoleById(@PathVariable String roleId) {
        try (RoleService roleService = new RoleService()) {
            Optional<Role> optionalRole = roleService.getRoleById(roleId);
            if (optionalRole.isPresent()) {
                Role role = optionalRole.get();
                ApiResponse response = new ApiResponse(true, role, null);
                return ResponseEntity.ok(response);
            } else {
                String errorMessage = "Role not found : " + roleId;
                ApiResponse response = new ApiResponse(false, null, errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
  desc : mi_inserte role vaovao anaty base de donne
  method : post
  url : .../role/save
  body (example) :
   {
    "intitule": "Level1"
    }
   */
    @PostMapping("/save")
    public ResponseEntity<ApiResponse> createRole(@RequestBody Role role) {
        try (RoleService roleService = new RoleService()) {
            String idRoleCreated = roleService.save(role);
            ApiResponse response = new ApiResponse(true, role, "done");
            logger.info("new role inserted");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
 desc : update
 method : put
 url : .../role/update
 body (example) :
  {
"id_role": "ROLE_1",
"intitule": "Level1"
}
  */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateRole(@RequestBody Role role) {
        try (RoleService roleService = new RoleService()) {
            roleService.update(role);
            ApiResponse response = new ApiResponse(true, role, "done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
   desc : delete
   method : delete
   url (example) : .../role/delete/ROLE_1
    */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable String id) {
        try (RoleService roleService = new RoleService()) {
            roleService.delete(id);
            ApiResponse response = new ApiResponse(true, null, "done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
}
