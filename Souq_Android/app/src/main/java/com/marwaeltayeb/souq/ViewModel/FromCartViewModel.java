package com.marwaeltayeb.souq.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.marwaeltayeb.souq.repository.FromCartRepository;
import com.marwaeltayeb.souq.utils.RequestCallback;

import okhttp3.ResponseBody;

public class FromCartViewModel extends AndroidViewModel {

    private FromCartRepository fromCartRepository;

    public FromCartViewModel(@NonNull Application application) {
        super(application);
        fromCartRepository = new FromCartRepository(application);
    }

    public LiveData<ResponseBody> removeFromCart(int userId, int productId, RequestCallback callback) {
        return fromCartRepository.removeFromCart(userId, productId, callback);
    }
}
