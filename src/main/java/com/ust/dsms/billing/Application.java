package com.ust.dsms.billing;

import com.ust.dsms.billing.expression.BillingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws BillingException {
        SpringApplication.run(Application.class, args);
    }
}
