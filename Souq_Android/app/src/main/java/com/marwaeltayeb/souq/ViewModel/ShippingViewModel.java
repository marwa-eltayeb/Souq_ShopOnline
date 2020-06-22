package com.marwaeltayeb.souq.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.marwaeltayeb.souq.model.Shipping;
import com.marwaeltayeb.souq.repository.ShippingRepository;

import okhttp3.ResponseBody;

public class ShippingViewModel  extends AndroidViewModel {

    private ShippingRepository shippingRepository;

    public ShippingViewModel(@NonNull Application application) {
        super(application);
        shippingRepository = new ShippingRepository(application);
    }

    public LiveData<ResponseBody> addShippingAddress(Shipping shipping) {
        return shippingRepository.addShippingAddress(shipping);
    }
}
