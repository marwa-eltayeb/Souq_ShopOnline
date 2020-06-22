package com.marwaeltayeb.souq.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.marwaeltayeb.souq.model.Ordering;
import com.marwaeltayeb.souq.repository.OrderingRepository;

import okhttp3.ResponseBody;

public class OrderingViewModel extends AndroidViewModel {

    private OrderingRepository orderingRepository;

    public OrderingViewModel(@NonNull Application application) {
        super(application);
        orderingRepository = new OrderingRepository(application);
    }

    public LiveData<ResponseBody> orderProduct(Ordering ordering) {
        return orderingRepository.orderProduct(ordering);
    }

}
