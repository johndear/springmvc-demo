package com.ls.springmvc.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.ls.springmvc.entity.User;
import com.ls.springmvc.utils.DateUtils;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Map<String, Object> map) {
		map.put("user", new User());
		return "addUser";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@Valid User u, BindingResult br) {
		if (br.getErrorCount() > 0) {
			return "addUser";
		}
		return "showUser";
	}
	
	@RequestMapping("/date")
    public String date(Date date){
        System.out.println(date);
        return "hello";
    }
    
    //At the time of initialization,convert the type "String" to type "date"
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),
                true));
    }
    
    @RequestMapping("/resetPwd")  
	public void resetPwd(String id){
    	String url = "http://10.10.63.49:20883/dubbox/UcRestUserService/syncMtdsUser";
		JSONObject postData = new JSONObject();
        postData.put("accountListStr", "A002@S000044");
        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(postData);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		ResponseEntity<JSONObject> resp = restTemplate.postForEntity(url, entity, JSONObject.class);
		
		JSONObject body = resp.getBody();
		if (resp.getStatusCode() == HttpStatus.OK && body.getIntValue("code") == HttpStatus.OK.value()) {
			System.out.println("���͵�΢�ŷ����쳣��url:" + url + "����Ӧ�����" + resp.getBody());
		}
	}
    
    @RequestMapping("/exception")
    public String exception(){
        throw new RuntimeException("手动抛异常。。。");
    }
    
    public static void main(String[] args) {
		System.out.println(getTimeoutCount("2017-10-26 14:50:00", "2017-10-28 10:50:00"));
	}
    
    // 每天最多记5次（8：00-18：00为有效期，每2小时/次）
    private static int getTimeoutCount(String startTimeStr, String endTimeStr){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat secondFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			int times = 0;
			Date startTime = secondFormat.parse(startTimeStr);
			Date endTime = secondFormat.parse(endTimeStr);
			if(endTime.getTime() <= startTime.getTime()){
				return times;
			}
			
			Date startDate = dateFormat.parse(startTimeStr);
			Date endDate = dateFormat.parse(endTimeStr);
			int diffDays = ((Long)DateUtils.getDays(startDate, endDate)).intValue();
			
			// 计算同一天的有效次数
			double startHour = getHour(startTime);
			double endHour = getHour(endTime);
			if(diffDays == 0){
				double diffHours = (endHour > 18 ? 18 : endHour) - (startHour < 8 ? 8 : startHour);
				times = Double.valueOf(diffHours > 0 ? diffHours : 0).intValue()/2;
				return times;
			}
			
			// 计算跨天的有效次数
			// 第一天的有效次数
			double startDiffHours = 18 - (startHour < 8 ? 8 : startHour);
			
			// 最后一天的有效次数
			double endDiffHours = (endHour > 18 ? 18 : endHour) - 8;

			// 中间间隔天数的有效次数 = 间隔天数  * 5
			times = times + (diffDays - 1) * 5;

			Double hours = (startDiffHours > 0 ? startDiffHours : 0) + (endDiffHours > 0 ? endDiffHours : 0);
			times = times + hours.intValue()/2;
			
			return times;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
    
    private static double getHour(Date sourceDate){
    	return sourceDate.getHours() + (double)sourceDate.getMinutes()/60;
    }

}
