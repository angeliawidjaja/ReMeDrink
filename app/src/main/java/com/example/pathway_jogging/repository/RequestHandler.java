package com.example.pathway_jogging.repository;

/**
 * Created by Angelia Widjaja on 14-Jan-22 21:57.
 */
public interface RequestHandler<T> {
    void onResult(T response);
}
