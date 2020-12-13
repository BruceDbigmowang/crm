package com.knowhy.crm.service;

import com.knowhy.crm.dao.ExtraContractDAO;
import com.knowhy.crm.pojo.ExtraContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtraContractService {
    @Autowired
    ExtraContractDAO extraContractDAO;

    public void saveextraFile(ExtraContract contract){
        extraContractDAO.save(contract);
    }
}
