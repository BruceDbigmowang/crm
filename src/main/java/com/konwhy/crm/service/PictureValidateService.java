package com.konwhy.crm.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public interface PictureValidateService {

    String generateValidatePicture(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
