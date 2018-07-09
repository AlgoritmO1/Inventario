package com.project.motos.Controller;

import com.project.motos.model.User;
import com.project.motos.repository.UserRepository;
import com.project.motos.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/v1")
public class UserController {
    
    @Autowired
    UserRepository user_repository;
    
    //POST (CREATE)
    @RequestMapping(value = "/users", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        if (user_repository.findByName(user.getName()) != null) {
            //logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A user with name " +
                    user.getName() + " already exist."),HttpStatus.CONFLICT);
        }
        user_repository.save(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/v1/users/{name}").buildAndExpand(user.getName()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //GET
    @RequestMapping(value = "/users/{name}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<User> getUser(@PathVariable("name") String name) {
        if (user_repository.findByName(name) == null) {
            //logger.error("Unable to create. A User with name {} already exist", user.getName())
            return new ResponseEntity(new CustomErrorType("user with name " +
                    name + " doesn't exist."),HttpStatus.CONFLICT);
        }
        User user = user_repository.findByName(name);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    //GET FINDALL
    @RequestMapping(value = "/users/findAll", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<User>> getUsers() {
        if (user_repository.findAll().isEmpty()) {
            //logger.error("Unable to create. A User with name {} already exist", user.getName())
            return new ResponseEntity(new CustomErrorType("users don't exist."),HttpStatus.CONFLICT);
        }
        List<User> users = user_repository.findAll();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    //DELETE
    @RequestMapping(value = "/users/{name}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<?> deleteUser(@PathVariable("name") String name) {
        if (user_repository.findByName(name) == null) {
            //logger.error("Unable to create. A User with name {} already exist", user.getName())
            return new ResponseEntity(new CustomErrorType("user with name " +
                    name + " doesn't exist."),HttpStatus.CONFLICT);
        }
        User user = user_repository.findByName(name);
        user_repository.delete(user);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    //UPDATE PASSW
    @RequestMapping(value = "/users/{name}", method = RequestMethod.PATCH,headers = "Accept=application/json")
    public ResponseEntity<?> updateUser(@PathVariable("name") String name, @RequestBody User cUser){
        if (user_repository.findByName(name) == null) {
            //logger.error("Unable to create. A User with name {} already exist", user.getName())
            return new ResponseEntity(new CustomErrorType("user with name " +
                    name + " doesn't exist."),HttpStatus.CONFLICT);
        }

        User user = user_repository.findByName(name);

        user.setPassword(cUser.getPassword());

        user_repository.save(user);

        return new ResponseEntity<User>(HttpStatus.OK);
    }
}
