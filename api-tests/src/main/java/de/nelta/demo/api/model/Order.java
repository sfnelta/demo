package de.nelta.demo.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {
    private String user_id;
    private String product_id;
    private String product_name;
    private Double product_amount;
    private Integer qty;
    private Double tax_amt;
    private Double total_amt;

    // Default constructor for Jackson
    public Order() {}

    public Order(String user_id, String product_id, String product_name, Double product_amount, Integer qty, Double tax_amt, Double total_amt) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_amount = product_amount;
        this.qty = qty;
        this.tax_amt = tax_amt;
        this.total_amt = total_amt;
    }

    public String getUser_id() { return user_id; }
    public void setUser_id(String user_id) { this.user_id = user_id; }

    public String getProduct_id() { return product_id; }
    public void setProduct_id(String product_id) { this.product_id = product_id; }

    public String getProduct_name() { return product_name; }
    public void setProduct_name(String product_name) { this.product_name = product_name; }

    public Double getProduct_amount() { return product_amount; }
    public void setProduct_amount(Double product_amount) { this.product_amount = product_amount; }

    public Integer getQty() { return qty; }
    public void setQty(Integer qty) { this.qty = qty; }

    public Double getTax_amt() { return tax_amt; }
    public void setTax_amt(Double tax_amt) { this.tax_amt = tax_amt; }

    public Double getTotal_amt() { return total_amt; }
    public void setTotal_amt(Double total_amt) { this.total_amt = total_amt; }
}
