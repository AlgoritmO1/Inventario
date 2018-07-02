package com.project.motos.Controller;


import com.project.motos.model.Product;
import com.project.motos.repository.ProductRepository;
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

@Controller
@RequestMapping("/v1")
public class ProductController {

    @Autowired
    ProductRepository product_repository;

    //POST (CREATE)
    //CREATE
    @RequestMapping(value = "/products", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> createCourse(@RequestBody Product product, UriComponentsBuilder ucBuilder) {
        if (product_repository.findByName(product.getName()) != null) {
            //logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A course with name " +
                    product.getName() + " already exist."),HttpStatus.CONFLICT);
        }
        product_repository.save(product);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/v1/products/{id}").buildAndExpand(product.getIdProduct()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //GET BY ID
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Product> getCourseByIdCourse(@PathVariable("id") Long id){
        Product product = product_repository.findByIdProduct(id);
        if (product == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }
}
