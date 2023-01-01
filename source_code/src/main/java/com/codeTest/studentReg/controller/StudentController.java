package com.codeTest.studentReg.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeTest.studentReg.annotation.SecureWithToken;
import com.codeTest.studentReg.entity.Student;
import com.codeTest.studentReg.service.StudentService;
import com.codeTest.studentReg.utility.ExcelExportUtil;
import com.codeTest.studentReg.utility.generalUtil;

@RestController
@RequestMapping("/api/student")
public class StudentController {
	@Autowired
	StudentService stuService;
	
	@Autowired
	JavaMailSender mailSenderService;

	@GetMapping("/all")
	@SecureWithToken
	public List<Student> getAllStudents(HttpServletRequest request){
		return stuService.getAllStudents();
	}

	@GetMapping("/all/byName/{pageNo}/{noOfData}")
	@SecureWithToken
	public Page<Student> getAllByPage(@PathVariable(name = "pageNo") int pn, @PathVariable(name = "noOfData") int noOfData, HttpServletRequest request){
		return stuService.getAllByPage(pn, noOfData);
	}
	
	@GetMapping("/getById/{id}")
	@SecureWithToken
	public Student getStudentById(@PathVariable(name = "id") int id, HttpServletRequest request) {
		return stuService.getStudentById(id);
	}
	
	@PostMapping("/save")
	@SecureWithToken
	public Student addStudent(@RequestBody Student data, HttpServletRequest request) {
		String currentDate = generalUtil.getCurrentDate();
		data.setCreatedAt(currentDate);
		data.setUpdatedAt(currentDate);
		
		return stuService.addStudent(data);
	}
	
	@PutMapping("/update")
	@SecureWithToken
	public Student updateStudent(@RequestBody Student data, HttpServletRequest request) {
		String currentDate = generalUtil.getCurrentDate();
		data.setUpdatedAt(currentDate);
		
		return stuService.updateStudent(data);
	}
	
	@DeleteMapping("/delete")
	@SecureWithToken
	public ResponseEntity<?> deleteStudent(@RequestBody Student data, HttpServletRequest request){
		boolean isDeleted = stuService.deleteStudent(data.getId());
		
		if(isDeleted == false) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/export/excel")
	@SecureWithToken
	public void exportExcel(@RequestBody HashMap<String, Object> data, HttpServletRequest request, HttpServletResponse response){
		response.setContentType("application/octet-stream");
		
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + data.get("fileName") + ".xlsx";
        response.setHeader(headerKey, headerValue);
        
        List<Student> stuList = stuService.getAllStudents();
        
        ExcelExportUtil exportUtils = new ExcelExportUtil(stuList);
        
        try {
			exportUtils.exportDataToExcel(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping(value = "/email/excel", produces = MediaType.APPLICATION_ATOM_XML_VALUE)
	@SecureWithToken
	public ResponseEntity<InputStreamResource> exportExcelToEmail(@RequestBody HashMap<String, Object> data, HttpServletRequest request, HttpServletResponse response){
		response.setContentType("application/octet-stream");
		
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + data.get("fileName").toString() + ".xlsx";
        response.setHeader(headerKey, headerValue);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add(headerKey, headerValue);
        
        List<Student> stuList = stuService.getAllStudents();
        
        ExcelExportUtil exportUtils = new ExcelExportUtil(stuList);
        
        try {
			ByteArrayInputStream bis = exportUtils.exportExcelToEmail(response);
			
			sendmail(data, stuList, bis);
			
			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_XML)
	        		.body(new InputStreamResource(bis));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return null;
	}

	private void sendmail(HashMap<String, Object> data, List<Student> stuList, ByteArrayInputStream bis) {
		final String to = data.get("to").toString();
		final String subject = "Student DataList";
		final String body = "<html><body><p>DataList of All Students</p></body></html>";
		
		mailSenderService.send(new MimeMessagePreparator(){
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				helper.setTo(to);
				helper.setSubject(subject);
				helper.setText(body, true);
				
				DataSource attachment = new ByteArrayDataSource(bis, "application/xlsx");
				helper.addAttachment(data.get("fileName").toString() + ".xlsx", attachment);
			}
		});
	}
}