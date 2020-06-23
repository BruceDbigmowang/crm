package com.knowhy.crm.service;

import com.knowhy.crm.dao.PathDAO;
import com.knowhy.crm.pojo.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PathService {
    @Autowired
    PathDAO pathDAO;

    public void writeRecord(Path path){
        pathDAO.save(path);
    }
}
