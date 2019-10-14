package com.capg.rms.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.capg.rms.beans.Retailer;


@Configuration
@ComponentScan(basePackageClasses=Retailer.class)
public class RetailerConfig {

}
