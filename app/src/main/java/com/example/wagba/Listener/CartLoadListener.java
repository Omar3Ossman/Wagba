package com.example.wagba.Listener;

import com.example.wagba.model.CartModel;
import com.example.wagba.model.OrdersModel;

import java.util.List;

public interface CartLoadListener {
    void onCartSuccess(List<CartModel> cartModelList);
    void onCartFailure(String message);

    void onCartSuccess(String message);
}
