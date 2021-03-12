package com.ordersystem.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class OrderForm {
    @NotEmpty(message = "name mandatory")
    private String name;

    @NotEmpty(message = "cell phone mandatory")
    private String phone;

    @NotEmpty(message = "address mandatory")
    private String address;

    @NotEmpty(message = "openid mandatory")
    private String openid;

    @NotEmpty(message = "cart can't be empty")
    private String items;


}
