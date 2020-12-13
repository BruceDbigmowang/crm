package com.knowhy.crm.controller;

import com.knowhy.crm.dao.MarketingDAO;
import com.knowhy.crm.pojo.Marketing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class PicController {

    @Autowired
    MarketingDAO marketingDAO;

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

    @RequestMapping("/saveFileContract")
    public String saveContract(@RequestParam("contractFile") MultipartFile file , HttpServletRequest request) throws IOException {
        //将图片存储到img/sale目录下
        String path = request.getSession().getServletContext().getRealPath("assets/contract/");
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

    @RequestMapping("/saveExtraContract")
    public String saveExtraFile(@RequestParam("contractFile") MultipartFile file , HttpServletRequest request) throws IOException {
        //将图片存储到img/sale目录下
        String path = request.getSession().getServletContext().getRealPath("assets/extraContract/");
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

    @RequestMapping("/saveMarketingFile")
    public String saveMarketFile(@RequestParam("contractFile") MultipartFile file , HttpServletRequest request) throws IOException {
        //将图片存储到img/sale目录下
        String path = request.getSession().getServletContext().getRealPath("assets/marketingFile/");
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

    @RequestMapping("/saveReport")
    public String saveReportFile(@RequestParam("reportFile") MultipartFile file , HttpServletRequest request) throws IOException {
        //将图片存储到img/sale目录下
        String path = request.getSession().getServletContext().getRealPath("assets/reports/");
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

    @RequestMapping("/saveActivityFile")
    public String saveActivityFile(@RequestParam("activityFile") MultipartFile file , HttpServletRequest request) throws IOException {
        //将图片存储到img/sale目录下
        String path = request.getSession().getServletContext().getRealPath("assets/activityFiles/");
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

    @RequestMapping("/saveCustomerFiles")
    public String saveCustomerFile(@RequestParam("customerFile") MultipartFile file , HttpServletRequest request) throws IOException {
        //将图片存储到img/sale目录下
        int mId = Integer.parseInt(request.getParameter("mId"));
        Marketing marketing = marketingDAO.getOne(mId);
        if(marketing.getUploadStatus().equals("O")){
            String path = request.getSession().getServletContext().getRealPath("assets/customerFiles/");
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
        }else{
            return "文件已上传，请勿重复上传";
        }

    }

    @RequestMapping("/saveOfflineExcel")
    public String saveNewOffline(@RequestParam("offlineExcel") MultipartFile file , String type , HttpServletRequest request) throws IOException {
        //将图片存储到img/sale目录下
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");
        String path = request.getSession().getServletContext().getRealPath("assets/offline/");
        String filename  = "";
        if (!file.isEmpty()) {
            filename = file.getOriginalFilename();
            System.out.println("文件名"+filename);
            String[] parts = filename.split("\\.");
            System.out.println(parts.length);
            filename = parts[0]+type+sdf.format(new Date())+"."+parts[1];
            File filepath = new File(path, filename);
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            file.transferTo(new File(path + File.separator + filename));
        }else{
            filename = "error";
        }
        return filename;
    }


    /**
     * ==============================================
     * 文件下载
     * ==============================================
     */
    @RequestMapping("downloadReceiveModel")
    public void downloadModel(HttpServletRequest request , HttpServletResponse response) throws FileNotFoundException, IOException {
        String filename = "2保密协议.doc";
        String path = request.getSession().getServletContext().getRealPath("assets/offline/")+filename;
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

    @RequestMapping("downloadOfflineFile")
    public void downloadfileOffline(HttpServletRequest request , HttpServletResponse response , String filename) throws FileNotFoundException, IOException {
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

    @RequestMapping("/downLoadSecretFile")
    public void downloadSecretFile(HttpServletRequest request , HttpServletResponse response , String filename) throws FileNotFoundException, IOException {
        String path = request.getSession().getServletContext().getRealPath("img/filePic/")+filename;
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

    @RequestMapping("/downLoadContractFile")
    public void downloadContractFile(HttpServletRequest request , HttpServletResponse response , String filename) throws FileNotFoundException, IOException {
        String path = request.getSession().getServletContext().getRealPath("assets/contract/")+filename;
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

    @RequestMapping("/downLoadContractExtra")
    public void downloadExtraFile(HttpServletRequest request , HttpServletResponse response , String filename) throws FileNotFoundException, IOException {
        String path = request.getSession().getServletContext().getRealPath("assets/extraContract/")+filename;
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

    @RequestMapping("/downLoadReportFile")
    public void downloadReportfile(HttpServletRequest request , HttpServletResponse response , String filename) throws FileNotFoundException, IOException {
        String path = request.getSession().getServletContext().getRealPath("assets/reports/")+filename;
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
