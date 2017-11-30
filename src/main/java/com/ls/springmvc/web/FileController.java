package com.ls.springmvc.web;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/file")
public class FileController {
	
	@RequestMapping("/upload")
	public String upload(Model model) {
		model.addAttribute("message", "准备上传文件...");
		return "fileupload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(Model model, HttpServletRequest req) throws Exception {
		MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) req;
		MultipartFile file = mreq.getFile("file");
		String fileName = file.getOriginalFilename();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		FileOutputStream fos = new FileOutputStream(req.getSession()
				.getServletContext().getRealPath("/")
				+ "/upload/"
				+ sdf.format(new Date())
				+ fileName.substring(fileName.lastIndexOf('.')));
		fos.write(file.getBytes());
		fos.flush();
		fos.close();
		
		model.addAttribute("message", "上传成功！");
		return "fileupload";
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String download(Model model, HttpServletRequest req) throws Exception {
		return "redirect:http://opyx-mtds-test.oss-cn-shenzhen.aliyuncs.com/ServiceOrder/2017/9/14/1505361145085-40986826-64bbae0b8b5864e9.jpg";
	}
}
