package com.project.motos.Controller;


import com.project.motos.model.Bill;
import com.project.motos.model.Detail;
import com.project.motos.model.Product;
import com.project.motos.repository.BillRepository;
import com.project.motos.repository.DetailRepository;
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
import java.util.Set;

@Controller
@RequestMapping("/v1")
public class BillController {

    @Autowired
    BillRepository bill_repository;

    @Autowired
    ProductRepository product_repository;

    @Autowired
    DetailRepository detail_repository;

    //POST (CREATE)
    //CREATE
    @RequestMapping(value = "/bills", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> createCourse(@RequestBody Bill bill, UriComponentsBuilder ucBuilder) {

        Set<Detail> details = bill.getDetails();

        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);

        bill.setDate(currentTime);

        if(details != null) {
            for (Detail detail : details
                    ) {
                Product product = product_repository.findByIdProduct(detail.getProduct().getIdProduct());
                if (product == null) {
                    return new ResponseEntity(new CustomErrorType("product with name " +
                            product.getName() + " doesn't exist."), HttpStatus.CONFLICT);
                }
                if (product.getStock() < detail.getQuantity()) {
                    return new ResponseEntity(new CustomErrorType("There isn't enought " +
                            product.getName() + " in the stock for this quantity."), HttpStatus.CONFLICT);
                }

                if (detail.getQuantity() < 0) {
                    return new ResponseEntity(new CustomErrorType("Please input positive values for quantity"), HttpStatus.CONFLICT);
                }
                detail.setPrice(product.getPrice() * detail.getQuantity());

                detail_repository.save(detail);
            }
        }
        bill_repository.save(bill);




        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/v1/bills/{id}").buildAndExpand(bill.getIdBill()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //GET BY ID
    @RequestMapping(value = "/bills/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Bill> getBillByName(@PathVariable("id") Long id){
        Bill bill = bill_repository.findByIdBill(id);
        if (bill == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<Bill>(bill, HttpStatus.OK);
    }



    //GET FINDALL
    @RequestMapping(value = "/bills/findAll", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<Bill>> getBills() {
        if (bill_repository.findAll().isEmpty()) {
            //logger.error("Unable to create. A bill with name {} already exist", bill.getName())
            return new ResponseEntity(new CustomErrorType("bills don't exist in db."),HttpStatus.CONFLICT);
        }
        List<Bill> bills = bill_repository.findAll();
        return new ResponseEntity<List<Bill>>(bills, HttpStatus.OK);
    }

    //DELETE
    @RequestMapping(value = "/bills/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<?> deleteBill(@PathVariable("id") Long id) {
        if (bill_repository.findById(id) == null) {
            //logger.error("Unable to create. A bill with name {} already exist", bill.getName())
            return new ResponseEntity(new CustomErrorType("bill with id " +
                    id + " doesn't exist."),HttpStatus.CONFLICT);
        }
        Bill bill = bill_repository.findByIdBill(id);
        bill_repository.delete(bill);
        return new ResponseEntity<Bill>(HttpStatus.NO_CONTENT);
    }
    
    //PATCH UPDATE
    @RequestMapping(value = "/bills/{id}", method = RequestMethod.PATCH,headers = "Accept=application/json")
    public ResponseEntity<?> updatebill(@PathVariable("id") Long id, @RequestBody Bill cbill){
        Bill bill = bill_repository.findByIdBill(id);

        if (bill == null) {
            //logger.error("Unable to create. A bill with name {} already exist", bill.getName())
            return new ResponseEntity(new CustomErrorType("bill with id " +
                    id + " doesn't exist."),HttpStatus.CONFLICT);
        }

        bill.setClient(cbill.getClient());
        bill_repository.save(bill);
        return new ResponseEntity<Bill>(HttpStatus.OK);
    }
}

