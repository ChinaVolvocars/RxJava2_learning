package com.meahu.ui.opreates;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.meahu.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SimpleActivity extends AppCompatActivity {

    private String TAG = "SimpleActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);


        View viewById = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(getObserver());
            }
        });


    }

    private Observable<String> getObservable() {
        return Observable.just("小米", "华为", "苹果");
    }

    public Observer<? super String> getObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());

            }

            @Override
            public void onNext(String s) {
                textView.setText(s);
                textView.append(s);
                Log.d(TAG, " onNext : value : " + s);

            }

            @Override
            public void onError(Throwable e) {
                textView.append(" onError : " + e.getMessage());
                Log.d(TAG, " onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                textView.append(" onComplete");
                Log.d(TAG, " onComplete");
            }
        };
    }
}
