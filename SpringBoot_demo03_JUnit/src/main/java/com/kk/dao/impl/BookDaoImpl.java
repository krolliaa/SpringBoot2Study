package com.kk.dao.impl;

import com.kk.dao.BookDao;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BookDao {
    @Override
    public void save() {
        System.out.println("BookDao save...");
    }
}
