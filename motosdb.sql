CREATE TABLE USER(
  name VARCHAR(25) PRIMARY KEY,
  password VARCHAR(25) NOT NULL
);


CREATE TABLE BILL (
  id_bill INT AUTO_INCREMENT PRIMARY KEY ,
  date date not null
);

CREATE TABLE PRODUCT(
  id_product INT AUTO_INCREMENT PRIMARY KEY ,
  name VARCHAR(50) NOT NULL ,
  price INT NOT NULL ,
  stock INT NOT NULL
);

CREATE TABLE DETAIL(
  id_detail INT AUTO_INCREMENT,
  id_bill_detail INT,
  id_product_detail INT,
  quantity INT NOT NULL,
  price INT NOT NULL,
  FOREIGN KEY (id_bill_detail) REFERENCES BILL(id_bill) ON DELETE CASCADE ,
  FOREIGN KEY (id_product_detail) REFERENCES PRODUCT(id_product),
  PRIMARY KEY (id_bill_detail,id_detail),
  KEY(id_detail)
);


