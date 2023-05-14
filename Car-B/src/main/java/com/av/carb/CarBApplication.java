package com.av.carb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CarBApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarBApplication.class , args);
    }

    public static String toStandard(String string){
        char [] array = string.toLowerCase().toCharArray();
        array[0] -= 32;
        return String.valueOf(array);
    }
}
