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

import java.util.List;

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
            //logger.error("Unable to create. A product with name {} already exist", product.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A course with name " +
                    product.getName() + " already exist."),HttpStatus.CONFLICT);
        }
        product_repository.save(product);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/v1/products/{id}").buildAndExpand(product.getIdProduct()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //GET BY NAME
    @RequestMapping(value = "/products/{name}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Product> getProductByName(@PathVariable("name") String name){
        Product product = product_repository.findByName(name);
        if (product == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }



    //GET FINDALL
    @RequestMapping(value = "/products/findAll", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<Product>> getProducts() {
        if (product_repository.findAll().isEmpty()) {
            //logger.error("Unable to create. A product with name {} already exist", product.getName())
            return new ResponseEntity(new CustomErrorType("products don't exist."),HttpStatus.CONFLICT);
        }
        List<Product> products = product_repository.findAll();
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    //DELETE
    @RequestMapping(value = "/products/{name}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<?> deleteProduct(@PathVariable("name") String name) {
        if (product_repository.findByName(name) == null) {
            //logger.error("Unable to create. A product with name {} already exist", product.getName())
            return new ResponseEntity(new CustomErrorType("product with name " +
                    name + " doesn't exist."),HttpStatus.CONFLICT);
        }
        Product product = product_repository.findByName(name);
        product_repository.delete(product);
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }
    
    //PATCH UPDATE
    @RequestMapping(value = "/products/{name}", method = RequestMethod.PATCH,headers = "Accept=application/json")
    public ResponseEntity<?> updateproduct(@PathVariable("name") String name, @RequestBody Product cproduct){
        Product product = product_repository.findByName(name);

        if (product == null) {
            //logger.error("Unable to create. A product with name {} already exist", product.getName())
            return new ResponseEntity(new CustomErrorType("product with name " +
                    name + " doesn't exist."),HttpStatus.CONFLICT);
        }

        if (product.getName().equalsIgnoreCase(cproduct.getName())){
            return new ResponseEntity(new CustomErrorType("product with name " +
                    name + " already exist."),HttpStatus.CONFLICT);
        }
        product.setName(cproduct.getName());
        product.setPrice(cproduct.getPrice());
        product.setStock(cproduct.getStock());
        product_repository.save(product);
        return new ResponseEntity<Product>(HttpStatus.OK);
    }
}

