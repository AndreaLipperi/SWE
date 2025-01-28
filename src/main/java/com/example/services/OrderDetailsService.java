package com.example.services;

import com.example.models.Order_Details;
import com.example.ORM.OrderDetailsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsService {

    @Autowired
    private OrderDetailsDAO orderDetailRepository;

    public void save(Order_Details orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

}

