package com.knowhy.crm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
public class PicController {

    @RequestMapping("/saveVoucher")
    public String saveVoucherPic(@RequestParam("voucher") MultipartFile file , HttpServletRequest request) throws IOException {
        //将图片存储到img/sale目录下
        String path = request.getSession().getServletContext().getRealPath("img/filePic/");
        String filename ;
        if (!file.isEmpty()) {
            filename = file.getOriginalFilename();
            File filepath = new File(path, filename);
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            file.transferTo(new File(path + File.separator + filename));
        }
        return "文件上传成功";
    }

    /**
     * 保密协议附件上传
     */
    @RequestMapping("/saveSecretFileDoc")
    public String saveSecretFilePic(@RequestParam("secretFile") MultipartFile file , HttpServletRequest request) throws IOException {
        //将图片存储到img/sale目录下
        String path = request.getSession().getServletContext().getRealPath("img/filePic/");
        String filename ;
        if (!file.isEmpty()) {
            filename = file.getOriginalFilename();
            File filepath = new File(path, filename);
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            file.transferTo(new File(path + File.separator + filename));
        }
        return "文件上传成功";
    }

    /**
     * 初步方案附件上传
     */
    @RequestMapping("/saveFirstSchemeFile")
    public String saveFirstScheme(@RequestParam("firstFile") MultipartFile file , HttpServletRequest request) throws IOException {
        //将图片存储到img/sale目录下
        String path = request.getSession().getServletContext().getRealPath("assets/firstFile/");
        String filename ;
        if (!file.isEmpty()) {
            filename = file.getOriginalFilename();
            File filepath = new File(path, filename);
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            file.transferTo(new File(path + File.separator + filename));
        }
        return "文件上传成功";
    }

    /**
     * 最终方案附件上传
     */

    @RequestMapping("/saveSecondSchemeFile")
    public String saveSecondScheme(@RequestParam("secondFile") MultipartFile file , HttpServletRequest request) throws IOException {
        //将图片存储到img/sale目录下
        String path = request.getSession().getServletContext().getRealPath("assets/secondFile/");
        String filename ;
        if (!file.isEmpty()) {
            filename = file.getOriginalFilename();
            File filepath = new File(path, filename);
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            file.transferTo(new File(path + File.separator + filename));
        }
        return "文件上传成功";
    }

    /**
     * 初步合同上传
     */
    @RequestMapping("/saveFirstContractFile")
    public String saveFirstContract(@RequestParam("contractF") MultipartFile file , HttpServletRequest request) throws IOException {
        //将图片存储到img/sale目录下
        String path = request.getSession().getServletContext().getRealPath("assets/contractFirst/");
        String filename ;
        if (!file.isEmpty()) {
            filename = file.getOriginalFilename();
            File filepath = new File(path, filename);
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            file.transferTo(new File(path + File.separator + filename));
        }
        return "文件上传成功";
    }

    @RequestMapping("/saveSecondContractFile")
    public String saveSecondContract(@RequestParam("contractS") MultipartFile file , HttpServletRequest request) throws IOException {
        //将图片存储到img/sale目录下
        String path = request.getSession().getServletContext().getRealPath("assets/contractSecond/");
        String filename ;
        if (!file.isEmpty()) {
            filename = file.getOriginalFilename();
            File filepath = new File(path, filename);
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            file.transferTo(new File(path + File.separator + filename));
        }
        return "文件上传成功";
    }


    /**
     * ==============================================
     * 文件下载
     * ==============================================
     */
    @RequestMapping("downloadReceiveModel")
    public void downloadModel(HttpServletRequest request , HttpServletResponse response) throws FileNotFoundException, IOException {
        String filename = "2保密协议.doc";
        String path = request.getSession().getServletContext().getRealPath("assets/modelFile/")+filename;
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(path)));
        filename = URLEncoder.encode(filename,"UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        out.close();
    }

    @RequestMapping("/downLoadSchemeF")
    public void downloadSchemeFileF(HttpServletRequest request , HttpServletResponse response , String filename) throws FileNotFoundException, IOException {
        String path = request.getSession().getServletContext().getRealPath("assets/firstFile/")+filename;
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(path)));
        filename = URLEncoder.encode(filename,"UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        out.close();
    }

    @RequestMapping("/downLoadSchemeS")
    public void downloadSchemeFileS(HttpServletRequest request , HttpServletResponse response , String filename) throws FileNotFoundException, IOException {
        String path = request.getSession().getServletContext().getRealPath("assets/secondFile/")+filename;
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(path)));
        filename = URLEncoder.encode(filename,"UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        out.close();
    }

    @RequestMapping("/downLoadContractF")
    public void downloadContractFileF(HttpServletRequest request , HttpServletResponse response , String filename) throws FileNotFoundException, IOException {
        String path = request.getSession().getServletContext().getRealPath("assets/contractFirst/")+filename;
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(path)));
        filename = URLEncoder.encode(filename,"UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        out.close();
    }

    @RequestMapping("/downLoadContractS")
    public void downloadContractFileS(HttpServletRequest request , HttpServletResponse response , String filename) throws FileNotFoundException, IOException {
        String path = request.getSession().getServletContext().getRealPath("assets/contractSecond/")+filename;
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(path)));
        filename = URLEncoder.encode(filename,"UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        out.close();
    }
}
