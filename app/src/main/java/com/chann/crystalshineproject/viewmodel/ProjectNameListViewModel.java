package com.chann.crystalshineproject.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chann.crystalshineproject.data.ProjectNameListResponse;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ProjectNameListViewModel extends AndroidViewModel {

    private Application application;
    private CompositeDisposable compositeDisposable;
    private Disposable disposable;
    private MutableLiveData<ProjectNameListResponse> projectData;

    public ProjectNameListViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        compositeDisposable = new CompositeDisposable();
    }

//    public LiveData<ProjectNameListResponse> getData(){
//        return projectData();
//    }


//    private void loadData(Long aLong) {
////        Disposable subscribe = RetrofitService.getApiEnd().getMatchList(Token.token , date)
////                .subscribeOn(Schedulers.computation())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(this::handleResult , this::handleError);
//
//        compositeDisposable.add(subscribe);
//    }
//
//    private void handleError(Throwable throwable) {
//        Log.e("failure", throwable.toString());
//    }
//
//    private void handleResult(MatchListResponse matchListResponse) {
//
//        this.matchListResponse.setValue(matchListResponse);
//
//    }
}
