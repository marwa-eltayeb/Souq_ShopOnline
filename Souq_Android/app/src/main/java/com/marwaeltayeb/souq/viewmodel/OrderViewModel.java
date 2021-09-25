package com.marwaeltayeb.souq.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.souq.model.OrderApiResponse;
import com.marwaeltayeb.souq.repository.OrderRepository;

public class OrderViewModel extends ViewModel {

    private final OrderRepository orderRepository;

    public OrderViewModel() {
        orderRepository = new OrderRepository();
    }

    public LiveData<OrderApiResponse> getOrders(int userId) {
        return orderRepository.getOrders(userId);
    }
}

