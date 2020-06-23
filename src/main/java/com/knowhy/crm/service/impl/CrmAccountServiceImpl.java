package com.knowhy.crm.service.impl;

import com.knowhy.crm.entity.CrmAccount;
import com.knowhy.crm.mapper.CrmAccountMapper;
import com.knowhy.crm.service.CrmAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Bruce
 * @since 2020-06-23
 */
@Service
public class CrmAccountServiceImpl extends ServiceImpl<CrmAccountMapper, CrmAccount> implements CrmAccountService {

    @Resource
    CrmAccountMapper crmAccountMapper;


}
