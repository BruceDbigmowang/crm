package com.knowhy.crm.service;

import com.knowhy.crm.dao.FlowApproveDAO;
import com.knowhy.crm.dao.TravelReqDAO;
import com.knowhy.crm.pojo.FlowApprove;
import com.knowhy.crm.pojo.TravelReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class TravelReqService {
    @Autowired
    TravelReqDAO travelReqDAO;
    @Autowired
    FlowApproveDAO flowDAO;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    public String createReqNum(String type){
        String req = "knowhy"+type+sdf.format(new Date());
        List<TravelReq> reqList = travelReqDAO.findByMakeDate(LocalDate.now());
        String last = String.valueOf(reqList.size());
        for(int i = last.length() ; i < 4 ; i++){
            last = "0" + last;
        }
        req = req+last;
        return req;
    }

    public List<TravelReq> findMyAll(String account){
        System.out.println("____"+account);
        List<TravelReq> travelReqList = travelReqDAO.findByMaker(account);
        System.out.println(travelReqList.size());
        return travelReqList;
    }

    public List<TravelReq> findAllApprove(String account){
        List<FlowApprove> approveList = flowDAO.findByAccount(account);
        if(approveList == null || approveList.size() == 0){
            return null;
        }else{
            String status = approveList.get(0).getStatus();
            return travelReqDAO.findByStatus(status);
        }
    }

    public int getNextNode(String account){
        List<FlowApprove> approveList = flowDAO.findByAccount(account);
        if(approveList == null || approveList.size() == 0){
            return 0;
        }else{
            return approveList.get(0).getNext();
        }
    }

    public void approveReqPass(String account , String reqNum){
        List<FlowApprove> approveList = flowDAO.findByAccount(account);
        int nextNode = approveList.get(0).getNext();
        switch (nextNode){
            case -1:
                String status = "pass";
                TravelReq travelReq = travelReqDAO.getOne(reqNum);
                travelReq.setStatus(status);
                travelReqDAO.save(travelReq);
                break;
            case 2:
                String status2 = "second";
                TravelReq travelReq2 = travelReqDAO.getOne(reqNum);
                travelReq2.setStatus(status2);
                travelReqDAO.save(travelReq2);
                break;
            case 3:
                String status3 = "third";
                TravelReq travelReq3 = travelReqDAO.getOne(reqNum);
                travelReq3.setStatus(status3);
                travelReqDAO.save(travelReq3);
                break;
            case 4:
                String status4 = "fourth";
                TravelReq travelReq4 = travelReqDAO.getOne(reqNum);
                travelReq4.setStatus(status4);
                travelReqDAO.save(travelReq4);
                break;
        }
    }

    public void approveReqRefuse(String account , String reqNum , String remark){
        List<FlowApprove> approveList = flowDAO.findByAccount(account);
        int nextNode = approveList.get(0).getNext();
        switch (nextNode){
            case -1:
                String status = "refuse";
                TravelReq travelReq = travelReqDAO.getOne(reqNum);
                travelReq.setStatus(status);
                travelReqDAO.save(travelReq);
                break;
            case 2:
                String status2 = "refuse";
                TravelReq travelReq2 = travelReqDAO.getOne(reqNum);
                travelReq2.setStatus(status2);
                travelReqDAO.save(travelReq2);
                break;
            case 3:
                String status3 = "refuse";
                TravelReq travelReq3 = travelReqDAO.getOne(reqNum);
                travelReq3.setStatus(status3);
                travelReqDAO.save(travelReq3);
                break;
            case 4:
                String status4 = "refuse";
                TravelReq travelReq4 = travelReqDAO.getOne(reqNum);
                travelReq4.setStatus(status4);
                travelReqDAO.save(travelReq4);
                break;
        }
    }
}
