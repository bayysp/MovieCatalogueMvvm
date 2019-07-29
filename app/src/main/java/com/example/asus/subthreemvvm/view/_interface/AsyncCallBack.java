package com.example.asus.subthreemvvm.view._interface;

import com.example.asus.subthreemvvm.database.MovieModelDb;

public interface AsyncCallBack {
    void onPreExecute();
    void onPostExecute(MovieModelDb movieModelDb);
}
