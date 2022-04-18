package com.metoo.monitor.core.utils;

import com.metoo.monitor.core.entity.Policy;
import com.metoo.monitor.core.service.IPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RuleUtil {

    @Autowired
    private IPolicyService policyService;

    private static final List list = Arrays.asList("ip access-list standard", "ip access-list extended", "access-list");
    private static final List action = Arrays.asList("permit","deny");

    /**
     * 检测到当前行为策略及时，则insert当前策略，记录当前策略Id，并把传入的策略信息保存
     * @param line
     * @return
     */
    public Long policy(String line){
        if(!line.equals("")){
            line = line.trim();
            // 三种格式，两种写法
            int index = line.indexOf(" ");
            if(index > -1){
                String key = line.substring(0, line.lastIndexOf(" "));
                String[] keys = line.split(" ");
                if(list.contains(key) || list.contains(keys[0])){
                    Map ruleMap = new HashMap();
                    // 处理策略及 begin
                    Policy policy = new Policy();
                    String value = line.substring(line.lastIndexOf(" ") + 1);
                    policy.setName(value);
                    this.policyService.save(policy);
                    ruleMap.put("policyId", policy.getId());
                    //上一条策略及结束
//                    if(!map.isEmpty()){
//                        // insert rule
//                    }
                    ruleMap.put("flag", true);
                    return policy.getId();
                }
            }
        }
        return null;
    }

    /**
     * map 为id
     * @param line
     * @return
     */
    public Map rule(String line, Long policy){
        if(!line.equals("")){
            line = line.trim();
            // 三种格式，两种写法
            int index = line.indexOf(" ");
            if(index > -1){
                String key = line.substring(0, line.lastIndexOf(" "));
                String[] keys = line.split(" ");
                if(list.contains(key) || list.contains(keys[0])){// 检测到下一个策略则跳出本次循环，并设置策略集合为空，进入下一次检测
                    return null;
                }
                // 遍历策略集
                if(action.contains(keys[0])){// 这是一条策略
                    
                }
            }
        }
        return null;
    }
}
