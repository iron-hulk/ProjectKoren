package com.mobileagent.app.repository;

import java.util.Date;
import java.util.List;

import com.mobileagent.app.utilities.ContextObject;

/**
 * Created by ironhulk on 2014/07/29.
 * Service that will be used to perform persistent operations on context information
 */
public interface DBService<E> {

    public Long createContext(E e);

    public ContextObject getLatestContextObject();
    
    public List<E> getAllContexts();

    public List<E> getByStorageDate(Date storageDate);

    public void deleteAllContexts();
}
