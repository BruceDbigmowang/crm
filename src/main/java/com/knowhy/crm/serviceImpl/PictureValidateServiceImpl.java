package com.knowhy.crm.serviceImpl;

import com.knowhy.crm.service.PictureValidateService;
import com.knowhy.crm.util.PictureValidateUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class PictureValidateServiceImpl implements PictureValidateService {

    @Override
    public String generateValidatePicture(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return PictureValidateUtil.generateValidatePicture(request,response);

    }
}
