package com.metoo.monitor.core.utils;

import com.metoo.monitor.core.entity.Policy;
import com.metoo.monitor.core.entity.Rule;
import com.metoo.monitor.core.service.IPolicyService;
import com.metoo.monitor.core.service.IRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PolicyConfigUtil {

    private static final List list = Arrays.asList("ip access-list standard", "ip access-list extended");
    private static final List agreementList = Arrays.asList("ip","icpm","tcp","udp");
    @Autowired
    private IPolicyService policyService;
    @Autowired
    private IRuleService ruleService;
    @Autowired
    private IpUtil ipUtil;

    // 检测是否为策略集
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
                    policy.setType(key.equalsIgnoreCase("ip access-list standard") ? 0 : 1);
                    this.policyService.save(policy);
//                    ruleMap.put("policyId", policy.getId());
//                    ruleMap.put("flag", true);
                    return policy.getId();
                }
            }
        }
        return null;
    }

    // 先做判断，属于哪一类策略集
    public void content(String line, Long policyId){
        // 查询策略集是否存在
        Policy policy = this.policyService.getObjById(policyId);
        if(policy.getType() != null){
            Rule rule = new Rule();
            rule.setPolicyId(policy.getId());
            rule.setPolicyName(policy.getName());
            switch (policy.getType()){
                case 0:
                    this.standard(line, rule);
                    break;
                case 1:
                    this.extended(line, rule);
                    break;
            }
        }
    }

    public void standard(String line, Rule rule){
        if(!line.equals("")){
            line = line.trim();
            // 三种格式，两种写法
            int index = line.indexOf(" ");
            if(index > -1){
                String[] keys = line.split(" ");
                if(keys[0].equals("permit") || keys[0].equals("deny")){
                    rule.setAction(keys[0]);// 动作
                    String ip = keys[1];
                    rule.setSrcIp(ip);
                    if(this.verify(keys.length, 2)){
                        int maskLength = this.ipUtil.calcPrefixLengthByMack(keys[2]);
                        String mask = this.ipUtil.conversion(maskLength);
                        rule.setSrcMask(mask);
                    }
                    this.ruleService.save(rule);
                }
            }
        }
    }

    public void extended(String line, Rule rule) throws ArrayIndexOutOfBoundsException{
        if(!line.equals("")){
            line = line.trim();
            // 三种格式，两种写法
            int index = line.indexOf(" ");
            if(index > -1){
                String[] keys = line.split(" ");
                int length  = keys.length;
                if(this.verify(length, 0) && keys[0].equals("permit") || keys[0].equals("deny")){
                    rule.setAction(keys[0]);// 动作
                    if(this.verify(length, 1) && keys[1].equalsIgnoreCase("any")){
                        rule.setSrcIp("any");
                        if(this.verify(length, 2) && keys[2].equalsIgnoreCase("host")){
                            String dstMask = this.ipUtil.conversion(32);
                            rule.setDstMask(dstMask);
                            rule.setDstIp(keys[3]);
                            if(this.verify(length, 4) && keys[4].equalsIgnoreCase("eq")){
                                rule.setDstPort1(keys[5]);
                            }else if(this.verify(length, 5)){
                                rule.setDstPort1(keys[5]);
                                rule.setDstPort2(keys[6]);
                            }
                        }else if(this.verify(length, 2)){
                            rule.setDstIp(keys[2]);
                            if(this.verify(length, 3) && keys[3].equalsIgnoreCase("eq")){
                                rule.setDstPort1(keys[4]);
                            }else if(this.verify(length, 4)){
                                rule.setDstPort1(keys[4]);
                                rule.setDstPort2(keys[5]);
                            }
                        }
                    }else  if(agreementList.contains(keys[1])){
                            if(this.verify(length, 2) && keys[2].equalsIgnoreCase("host")){
                                String mask = this.ipUtil.conversion(32);
                                rule.setSrcMask(mask);
                                rule.setSrcIp(keys[3]);
                                if(this.verify(length, 4) && keys[4].equalsIgnoreCase("eq")){
                                    rule.setSrcPort1(keys[5]);
                                    if(this.verify(length, 6) && keys[6].equalsIgnoreCase("host")){
                                        String dstMask = this.ipUtil.conversion(32);
                                        rule.setDstMask(dstMask);
                                        rule.setDstIp(keys[7]);
                                        if(this.verify(length, 8) && keys[8].equalsIgnoreCase("eq")){
                                            rule.setDstPort1(keys[9]);
                                        }else if(this.verify(length, 9)){
                                            rule.setDstPort1(keys[9]);
                                            rule.setDstPort2(keys[10]);
                                        }
                                    }else if(this.verify(length, 6)){
                                        rule.setDstIp(keys[6]);
                                        String dstReverseMask = keys[7];
                                        int maskLength = this.ipUtil.calcPrefixLengthByMack(dstReverseMask);
                                        String dstMask = this.ipUtil.conversion(maskLength);
                                        rule.setDstMask(dstMask);
                                        if(this.verify(length, 8) && keys[8].equalsIgnoreCase("eq")){
                                            rule.setDstPort1(keys[9]);
                                        }else if(this.verify(length, 9)){
                                            rule.setDstPort1(keys[9]);
                                            rule.setDstPort2(keys[10]);
                                        }
                                    }
                                }else if(this.verify(length, 4) && keys[4].equalsIgnoreCase("range")){
                                    rule.setSrcPort1(keys[5]);
                                    rule.setSrcPort2(keys[6]);
                                    if(this.verify(length, 7) && keys[7].equalsIgnoreCase("host")){
                                        String dstMask = this.ipUtil.conversion(32);
                                        rule.setDstMask(dstMask);
                                        rule.setDstIp(keys[8]);
                                        if(this.verify(length, 9) && keys[9].equalsIgnoreCase("eq")){
                                            rule.setDstPort1(keys[10]);
                                        }else if(this.verify(length, 10)){
                                            rule.setDstPort1(keys[10]);
                                            rule.setDstPort2(keys[11]);
                                        }
                                    }else if(this.verify(length, 7)){
                                        rule.setDstIp(keys[7]);
                                        String dstReverseMask = keys[8];
                                        int maskLength = this.ipUtil.calcPrefixLengthByMack(dstReverseMask);
                                        String dstMask = this.ipUtil.conversion(maskLength);
                                        rule.setDstMask(dstMask);
                                        if(this.verify(length, 9) && keys[9].equalsIgnoreCase("eq")){
                                            rule.setDstPort1(keys[10]);
                                        }else if(this.verify(length, 10)){
                                            rule.setDstPort1(keys[10]);
                                            rule.setDstPort2(keys[11]);
                                        }
                                    }
                                }else if(this.verify(length, 4) && keys[4].equalsIgnoreCase("host")){
                                        String dstMask = this.ipUtil.conversion(32);
                                        rule.setDstMask(dstMask);
                                        String dstIp = keys[5];
                                        rule.setDstIp(dstIp);
                                        if(this.verify(length, 6) && keys[6].equalsIgnoreCase("eq")){
                                            rule.setDstPort1(keys[7]);
                                        }else if(this.verify(length, 7)){
                                            rule.setDstPort1(keys[7]);
                                            rule.setDstPort2(keys[8]);
                                    }
                                }else if(this.verify(length, 4)){
                                    rule.setDstIp(keys[4]);
                                    String dstReverseMask = keys[5];
                                    int maskLength = this.ipUtil.calcPrefixLengthByMack(dstReverseMask);
                                    String dstMask = this.ipUtil.conversion(maskLength);
                                    rule.setDstMask(dstMask);
                                    if(this.verify(length, 6) && keys[6].equalsIgnoreCase("eq")){
                                        rule.setDstPort1(keys[7]);
                                    }else if(this.verify(length, 7)){
                                        rule.setDstPort1(keys[7]);
                                        rule.setDstPort2(keys[8]);
                                    }
                                }
                            }else if(this.verify(length, 2) && keys[2].equalsIgnoreCase("any")){
                                if(this.verify(length, 3) && keys[3].equalsIgnoreCase("host")){
                                    String dstMask = this.ipUtil.conversion(32);
                                    rule.setDstMask(dstMask);
                                    String dstIp = keys[4];
                                    rule.setDstIp(dstIp);
                                    if(this.verify(length, 5) && keys[5].equalsIgnoreCase("eq")){
                                        rule.setDstPort1(keys[6]);
                                    }else if(this.verify(length, 6)){
                                        rule.setDstPort1(keys[6]);
                                        rule.setDstPort2(keys[7]);
                                    }
                                }else if(this.verify(length, 3)){
                                    rule.setDstIp(keys[3]);
                                    String dstReverseMask = keys[4];
                                    int maskLength = this.ipUtil.calcPrefixLengthByMack(dstReverseMask);
                                    String dstMask = this.ipUtil.conversion(maskLength);
                                    rule.setDstMask(dstMask);
                                    if(this.verify(length, 5) && keys[5].equalsIgnoreCase("eq")){
                                        rule.setDstPort1(keys[6]);
                                    }else if(this.verify(length, 6)){
                                        rule.setDstPort1(keys[6]);
                                        rule.setDstPort2(keys[7]);
                                    }
                                }
                            }else if(this.verify(length, 2)){
                                rule.setSrcIp(keys[2]);
                                String reverseMask = keys[3];
                                int maskLength = this.ipUtil.calcPrefixLengthByMack(reverseMask);
                                String mask = this.ipUtil.conversion(maskLength);
                                rule.setSrcMask(mask);
                            if(this.verify(length, 4) && keys[4].equalsIgnoreCase("eq")){
                                rule.setSrcPort1(keys[5]);
                                if(this.verify(length, 6) && keys[6].equalsIgnoreCase("host")){
                                    String dstMask = this.ipUtil.conversion(32);
                                    rule.setDstMask(dstMask);
                                    rule.setDstIp(keys[7]);
                                    if(this.verify(length, 8) && keys[8].equalsIgnoreCase("eq")){
                                        rule.setDstPort1(keys[9]);
                                    }else if(this.verify(length, 9)){
                                        rule.setDstPort1(keys[9]);
                                        rule.setDstPort1(keys[10]);
                                    }
                                }else if(this.verify(length, 6)){
                                    rule.setDstIp(keys[6]);
                                    String dstReverseMask = keys[7];
                                    int dstMaskLength = this.ipUtil.calcPrefixLengthByMack(dstReverseMask);
                                    String dstMask = this.ipUtil.conversion(dstMaskLength);
                                    rule.setDstMask(dstMask);
                                    if(this.verify(length, 8) && keys[8].equalsIgnoreCase("eq")){
                                        rule.setDstPort1(keys[9]);
                                    }else if(this.verify(length, 9)){
                                        rule.setDstPort1(keys[9]);
                                        rule.setDstPort1(keys[10]);
                                    }
                                }

                            }else if(this.verify(length, 4) && keys[4].equalsIgnoreCase("range")){
                                rule.setSrcPort1(keys[5]);
                                rule.setSrcPort2(keys[6]);
                                if(this.verify(length, 7) && keys[7].equalsIgnoreCase("host")){
                                    String dstMask = this.ipUtil.conversion(32);
                                    rule.setDstMask(dstMask);
                                    rule.setDstIp(keys[8]);
                                    if(this.verify(length, 9) && keys[9].equalsIgnoreCase("eq")){
                                        rule.setDstPort1(keys[10]);
                                    }else if(this.verify(length, 10)){
                                        rule.setDstPort1(keys[10]);
                                        rule.setDstPort1(keys[11]);
                                    }
                                }else if(this.verify(length, 7)){
                                    rule.setDstIp(keys[7]);
                                    String dstReverseMask = keys[8];
                                    int dstMaskLength = this.ipUtil.calcPrefixLengthByMack(dstReverseMask);
                                    String dstMask = this.ipUtil.conversion(dstMaskLength);
                                    rule.setDstMask(dstMask);
                                    if(this.verify(length, 9) && keys[9].equalsIgnoreCase("eq")){
                                        rule.setDstPort1(keys[10]);
                                    }else if(this.verify(length, 10)){
                                        rule.setDstPort1(keys[10]);
                                        rule.setDstPort1(keys[11]);
                                    }
                                }
                            }else if(this.verify(length, 4) && keys[4].equalsIgnoreCase("host")){
                                String dstMask = this.ipUtil.conversion(32);
                                rule.setDstMask(dstMask);
                                rule.setDstIp(keys[5]);
                                if(this.verify(length, 6) && keys[6].equalsIgnoreCase("eq")){
                                    rule.setDstPort1(keys[7]);
                                }else if(this.verify(length, 7)){
                                    rule.setDstPort1(keys[7]);
                                    rule.setDstPort1(keys[8]);
                                }
                            }else if(this.verify(length, 4)){
                                // ip
                                rule.setDstIp(keys[4]);
                                String dstReverseMask = keys[5];
                                int dstMaskLength = this.ipUtil.calcPrefixLengthByMack(dstReverseMask);
                                String dstMask = this.ipUtil.conversion(dstMaskLength);
                                rule.setDstMask(dstMask);
                                if(this.verify(length, 6) &&  keys[6].equalsIgnoreCase("eq")){
                                    rule.setDstPort1(keys[7]);
                                }else if(this.verify(length, 7)){
                                    rule.setDstPort1(keys[7]);
                                    rule.setDstPort1(keys[8]);
                                }
                            }
                        }
                    }
                    System.out.println(rule);
                    this.ruleService.save(rule);
                }
            }
        }
    }

    public boolean verify(int length, int index){
        if(index <= length - 1){
            return true;
        }
        return false;
    }
    public static void main(String[] args) {
       List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        System.out.println(list.get(2));
    }
}
