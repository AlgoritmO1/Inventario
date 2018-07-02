package com.project.motos.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "BILL")
public class Bill implements Serializable {

    @Id
    @Column(name = "id_bill")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long idBill;

    @Column(name = "date")
    private Date date;

    @Column(name = "client")
    private String client;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_bill_detail")
    private Set<Detail> details;



    public Bill(Date date, String client) {
        this.date = date;
        this.client = client;
    }

    public Set<Detail> getDetails() {
        return details;
    }

    public void setDetails(Set<Detail> details) {
        this.details = details;
    }

    public Long getId_bill() {
        return idBill;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
