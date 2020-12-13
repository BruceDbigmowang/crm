package com.knowhy.crm.controller;

import com.knowhy.crm.dao.AreaDAO;
import com.knowhy.crm.dao.AreaProvinceDAO;
import com.knowhy.crm.dao.ProvinceDAO;
import com.knowhy.crm.pojo.Area;
import com.knowhy.crm.pojo.AreaProvince;
import com.knowhy.crm.pojo.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AreaController {
    @Autowired
    AreaDAO areaDAO;
    @Autowired
    AreaProvinceDAO areaProvinceDAO;
    @Autowired
    ProvinceDAO provinceDAO;

    @RequestMapping("/selectByProvince")
    public String findByProvince(String provinceName){
        List<Province> provinceList = provinceDAO.findByProvinceName(provinceName);
        List<AreaProvince> areaProvinceList = areaProvinceDAO.findByProvinceId(provinceList.get(0).getId());
        Area area = areaDAO.getOne(areaProvinceList.get(0).getAreaId());
        return area.getAreaName();
    }

    @RequestMapping("/getAllArea")
    public List<Area> findAllArea(){
        return areaDAO.findAll();
    }

    @RequestMapping("/findProvinceByArea")
    public Map<String , Object> findAllProvince(int aId){
        List<AreaProvince> areaProvinceList = areaProvinceDAO.findByAreaId(aId);
        Map<String , Object> map = new HashMap<>();
        List<Province> areaList1 = new ArrayList<>();
        for(int i = 0 ;  i < areaProvinceList.size() ; i++){
            Province province = provinceDAO.getOne(areaProvinceList.get(i).getProvinceId());
            areaList1.add(province);
        }
        List<Province> areaList2 = provinceDAO.findAll();
        for(int i = 0 ; i < areaList1.size() ; i++){
            Province province = areaList1.get(i);
            areaList2.remove(province);
        }
        map.put("list1" , areaList1);
        map.put("list2" , areaList2);
        return map;
    }

    @RequestMapping("/saveArea")
    public String createNewArea(String areaName){
        Area area = new Area();
        area.setAreaName(areaName);
        areaDAO.save(area);
        return "OK";
    }

    @RequestMapping("/updateProvince")
    @Transactional
    public String updateAreaProvince(int aId , int pId){
        areaProvinceDAO.deleteByProvinceId(pId);
        AreaProvince areaProvince = new AreaProvince();
        areaProvince.setAreaId(aId);
        areaProvince.setProvinceId(pId);
        areaProvinceDAO.save(areaProvince);
        return "OK";
    }
}
