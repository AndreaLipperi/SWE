package com.example.services;

import com.example.models.Order_Details;
import com.example.repositories.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailRepository;

    public void save(Order_Details orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

}

