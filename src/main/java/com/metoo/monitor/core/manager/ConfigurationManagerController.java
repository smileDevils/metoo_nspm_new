package com.metoo.monitor.core.manager;

import cn.hutool.core.net.Ipv4Util;
import com.metoo.monitor.core.entity.User;
import com.metoo.monitor.core.mapper.UserMapper;
import com.metoo.monitor.core.service.IInterfaceService;
import com.metoo.monitor.core.service.IUserService;
import com.metoo.monitor.core.utils.IpUtil;
import com.metoo.monitor.core.utils.RuleUtil;
import io.swagger.annotations.ApiModelProperty;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.*;

@RequestMapping("/admin/config")
@RestController
public class ConfigurationManagerController {

    @Autowired
    private IInterfaceService interfaceService;
    private Ipv4Util ipv4Util;
    @Autowired
    private IpUtil ipUtil;
    @Autowired
    private UserMapper userService;
    @Autowired
    private RuleUtil ruleUtil;

   @RequestMapping("/interface")
    public int config(){
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\46075\\Desktop\\metoo\\word\\需求记录\\4，策略可视化\\metoo\\project\\思科.txt");
            // 防止路径乱码 如果utf-8 乱码 改GBK eclipse里创建的txt 用UTF-8，在电脑上自己创建的txt 用GBK

            try {
                InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String line = "";

                List interfaceList = new ArrayList();
                Map map = null;
                while ((line = br.readLine()) != null) {
                    if(map == null){
                        map = this.value(line);
                        continue;
                    }
                    if(map != null){
                        if(this.value(map, line) == null){
                            interfaceList.add(map);
                            map = null;
                            continue;
                        }
                    }
                }
               return this.interfaceService.batchInsert(interfaceList);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     *
     * @return
     */
    @RequestMapping("/acl")
    public int acl(){
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\46075\\Desktop\\metoo\\word\\需求记录\\4，策略可视化\\2，文档\\config\\R3.txt");
            // 防止路径乱码 如果utf-8 乱码 改GBK eclipse里创建的txt 用UTF-8，在电脑上自己创建的txt 用GBK

            try {
                InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String line = "";

                List interfaceList = new ArrayList();
                Long policy = null;
                while ((line = br.readLine()) != null) {
                    if(policy == null){
                        policy = this.ruleUtil.policy(line);
                        continue;
                    }
                    if(policy != null){
                        this.ruleUtil.rule(line, policy);
                        continue;
                    }
                }
                return this.interfaceService.batchInsert(interfaceList);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public Map value(String key){
        String[] strs =  key.split(" ");
        String str = strs[0];
        if(str.equals("interface")){
            Map map = new HashMap();
            map.put(str, key.substring(key.indexOf(" ") + 1));
            return map;
        }
        return null;
    }

    public Boolean value(Map map, String line){
        String data = line.trim();
        if(data.equals("shutdown")){
            System.out.println(data);
        }
        if(data.equals("!")){
            return null;
        }
        String[] strs =  data.split(" ");
        int index = data.indexOf(" ");
        String key = "";
        String value = "";
        if(index > -1){
            key = data.substring(0, index);
            value = data.substring(data.lastIndexOf(" ") + 1);
        }else{
            key = data;
            value = data;
        }
        List list = new ArrayList();
        list.add("description");
        list.add("ip");
        list.add("shutdown");
        if(list.contains(key)){
            if(key.equals("ip")){

                String ipSegment = data.substring(0, data.lastIndexOf(" "));
                String key1 = data.substring(0, ipSegment.lastIndexOf(" "));
                if(key1.equals("ip address")){
                    String ip = ipSegment.substring(ipSegment.lastIndexOf(" ") + 1);
                    map.put("ip", ip);
                    String maskSegment = value;
                    int mask = this.ipUtil.calcPrefixLengthByMack(maskSegment);
                    map.put("mask", mask);
                }
            }else{
                map.put(key, value);
            }
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Set set = new HashSet();
        set.add(1);
        set.add(2);
        set.add(1);
        set.add(3);
        set.add(1);
        System.out.println(set);
    }


}
