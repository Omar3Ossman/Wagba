package com.example.wagba.Listener;

import com.example.wagba.model.OrdersModel;

import java.util.List;

public interface OrdersLoadListener {
    void onOrderSuccess(List<OrdersModel>ordersModelList);
    void onOrderFailure(String message);
}
