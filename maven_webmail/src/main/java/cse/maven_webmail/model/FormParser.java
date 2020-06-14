/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.model;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 책임: enctype이 multipart/form-data인 HTML 폼에 있는 각 필드와 파일 정보 추출
 *
 * @author jongmin
 */
public class FormParser {

    private HttpServletRequest request;
    private String toAddress = null;
    private String ccAddress = null;
    private String subject = null;
    private String body = null;
    private String fileName = null;
    private final String uploadTargetDir = "C:/temp/upload";

    public FormParser(HttpServletRequest request) {
        this.request = request;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCcAddress() {
        return ccAddress;
    }

    public void setCcAddress(String ccAddress) {
        this.ccAddress = ccAddress;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public void parse() {
        try {
            request.setCharacterEncoding("UTF-8");

            // 1. 디스크 기반 파일 항목에 대한 팩토리 생성
            DiskFileItemFactory diskFactory = new DiskFileItemFactory();
            // 2. 팩토리 제한사항 설정
            diskFactory.setSizeThreshold(10 * 1024 * 1024);
            diskFactory.setRepository(new File(this.uploadTargetDir));
            // 3. 파일 업로드 핸들러 생성
            ServletFileUpload upload = new ServletFileUpload(diskFactory);

            // 4. request 객체 파싱
            List fileItems = upload.parseRequest(request);
            Iterator i = fileItems.iterator();
            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                if (fi.isFormField()) {  // 5. 폼 필드 처리
                    String fieldName = fi.getFieldName();
                    String item = fi.getString("UTF-8");

                    if (fieldName.equals("to")) {
                        setToAddress(item);
                    } else if (fieldName.equals("cc")) {
                        setCcAddress(item);
                    } else if (fieldName.equals("subj")) {
                        setSubject(item);
                    } else if (fieldName.equals("body")) {
                        setBody(item);
                    }
                } else {  // 6. 첨부 파일 처리
                    if (!(fi.getName() == null || fi.getName().equals(""))) {
                        String fieldName = fi.getFieldName();
                        System.out.println("ATTACHED FILE : " + fieldName + " = " + fi.getName());

                        // 절대 경로 저장
                        setFileName(uploadTargetDir + "/" + fi.getName());
                        File fn = new File(fileName);
                        // upload 완료. 추후 메일 전송후 해당 파일을 삭제하도록 해야 함.
                        if (fileName != null) {
                            fi.write(fn);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("FormParser.parse() : exception = " + ex);
        }
    }  // parse()
}
