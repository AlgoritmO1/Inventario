package com.project.motos.Controller;

import com.project.motos.model.User;
import com.project.motos.repository.UserRepository;
import com.project.motos.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/v1")
public class UserController {
    
    @Autowired
    UserRepository user_repository;
    
    //POST
    @RequestMapping(value = "/users", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> createCourse(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        if (user_repository.findByName(user.getName()) != null) {
            //logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A course with name " +
                    user.getName() + " already exist."),HttpStatus.CONFLICT);
        }
        user_repository.save(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/v1/users/{name}").buildAndExpand(user.getName()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

}
