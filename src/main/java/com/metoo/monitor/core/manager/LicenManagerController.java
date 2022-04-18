package com.metoo.monitor.core.manager;

import com.alibaba.fastjson.JSONObject;
import com.metoo.monitor.core.dto.LicenseDto;
import com.metoo.monitor.core.utils.AesEncryptUtils;
import com.metoo.monitor.core.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("monitor")
@RestController
public class LicenManagerController {

    @Autowired
    private AesEncryptUtils aesEncryptUtils;

    @PostMapping("/license")
    public Object license(@RequestBody LicenseDto dto){
        if(dto.getSystemSN() != null){
            String sn = null;
            try {
                sn = this.aesEncryptUtils.decrypt(dto.getSystemSN());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dto.getExpireTime());
                long currentTime = calendar.getTimeInMillis();
                long timeStampSec = currentTime / 1000;// 13位时间戳（单位毫秒）转换为10位字符串（单位秒）
                String timestamp = String.format("%010d", timeStampSec);// 当前时间
                Map map = new HashMap();
                map.put("systemSN", sn);
                map.put("expireTime", timestamp);
                return this.aesEncryptUtils.encrypt(JSONObject.toJSONString(map));
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseUtil.error("申请码错误");
            }
        }
        return null;
    }

    @RequestMapping("/system")
    public Object systemInfo(HttpServletRequest request){
        try {
            HttpSession session = request.getSession();
            System.out.println("session：" + session.getId());
            Cookie[] cookies  = request.getCookies();
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("JSESSIONID")){
                    System.out.println("cookieName：" + cookie.getName() + " cookieValue："+cookie.getValue());
                }
            }
            return "123";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/info")
    public Object info(){
        return "123";
    }

    @RequestMapping("/systemInfo")
    public Object systemInfo(){
         return "123";
    }
}
