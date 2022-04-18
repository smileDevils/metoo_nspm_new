package com.metoo.monitor.core.manager;

import com.metoo.monitor.core.entity.Policy;
import com.metoo.monitor.core.service.IPolicyConfigService;
import com.metoo.monitor.core.utils.PolicyConfigUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@RequestMapping("/admin/config")
@RestController
public class PolicyConfigAction {

    private static final Logger log = LoggerFactory.getLogger(PolicyConfigAction.class);


    @Autowired
    private PolicyConfigUtil policyConfigUtil;

    @ApiOperation("单文件上传")
    @RequestMapping("/policy")
    public void policy(@PathVariable MultipartFile file){
        if(file != null && !file.isEmpty()){
            try {
                InputStream is = file.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = "";
                Long time=System.currentTimeMillis();
                Long policyId = null;
                while((line = br.readLine()) != null){
                    // 获取单行数据，检测是否为策略集
                    Long id = this.policyConfigUtil.policy(line);
                    if(id != null && policyId != id){
                        policyId = id;
                    }
                    // 判断policyId不为null，检索策略集内容；检索到下一个policy时将policy致为null
                    if(policyId != null){
                        Map map = new HashMap();// 内容map
                        map.put("policyId", policyId);
                        this.policyConfigUtil.content(line, policyId);
                    }
                }
                System.out.println("===解析耗时："+(System.currentTimeMillis()-time)+"===");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


//                System.out.println("getName：" + file.getName());
//                System.out.println("getOriginalFileName：" + file.getName());
//                System.out.println("getOriginalFileName：" + file.getOriginalFilename());
//                System.out.println("getContentType：" + file.getContentType());
//                System.out.println("isEmpty：" + file.isEmpty());
//                System.out.println("getSize：" + file.getSize());
//                System.out.println("getBytes：" + file.getBytes());
//                System.out.println("getInputStream：" + file.getInputStream());
}
