package com.example.rxjavaexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<String> animalObservable = getAnimalObsevable();

        Observer<String> animalObserver = getAnimalObserver();

        animalObservable
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.toLowerCase().startsWith("b");
                    }
                })
                .subscribe(animalObserver);
    }

    private Observer<String> getAnimalObserver(){
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG,"onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,"Name: "+s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"onError: "+e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"All Items are Emitted...");
            }
        };
    }

    private Observable<String> getAnimalObsevable(){
        return Observable.fromArray(
                "Ant", "Ape",
                "Bat", "Bee", "Bear", "Butterfly",
                "Cat", "Crab", "Cod",
                "Dog", "Dove",
                "Fox", "Frog");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
