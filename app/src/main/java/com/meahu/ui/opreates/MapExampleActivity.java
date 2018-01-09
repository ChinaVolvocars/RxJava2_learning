package com.meahu.ui.opreates;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.meahu.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MapExampleActivity extends AppCompatActivity {

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
                getObservable().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new Function<String, Integer>() {
                            @Override
                            public Integer apply(String s) throws Exception {
                                return Integer.valueOf(s.length());
                            }
                        })
                        .subscribe(getObserver());
            }
        });


    }

    private Observable<String> getObservable() {
        return io.reactivex.Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                if (!e.isDisposed()) {

                    e.onNext("A");
                    e.onNext("B");
                    e.onNext("C");
                    e.onNext("D");
                    e.onComplete();
                }
            }
        });
    }

    private Observer<Integer> getObserver() {
        return new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d("--------", " onSubscribe : " + d.isDisposed());

            }

            @Override
            public void onNext(Integer s) {
                textView.append(String.valueOf(s));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
}
