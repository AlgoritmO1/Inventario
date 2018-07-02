package com.project.motos.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "DETAIL")
public class Detail implements Serializable {

    @Id
    @Column(name = "id_detail")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long idDetail;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_bill_detail")
    private Bill bill;

    @ManyToOne(optional = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "id_product_detail")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Integer price;


    public Detail(Bill bill, Product product, Integer quantity, Integer price) {
        this.bill = bill;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getIDetail() {
        return idDetail;
    }

    public Bill getBill() {
        return bill;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
