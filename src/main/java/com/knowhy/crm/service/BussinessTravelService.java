package com.knowhy.crm.service;

import com.knowhy.crm.dao.BussinessTravelDAO;
import com.knowhy.crm.dao.TravelReqDAO;
import com.knowhy.crm.pojo.BussinessTravel;
import com.knowhy.crm.pojo.TravelReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BussinessTravelService {

    @Autowired
    BussinessTravelDAO bussinessTravelDAO;
    @Autowired
    TravelReqDAO travelReqDAO;

    @Transactional
    public void saveTravel(BussinessTravel bussinessTravel , TravelReq travelReq){
        bussinessTravelDAO.save(bussinessTravel);
        travelReqDAO.save(travelReq);
    }

    public boolean canWriteTravel(String customerCode , String processCode , String applyer){
        List<BussinessTravel> travelList = bussinessTravelDAO.findByCustomerCodeAndProcessCodeAndMakerAccount(customerCode , processCode , applyer);
        for(int i = 0 ; i < travelList.size() ; i++){
            BussinessTravel travel = travelList.get(i);
            if(travel.getTravelStatus() == null || travel.getTravelStatus().equals("O")){
                return false;
            }
        }
        return true;

    }
}
