package com.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.constant.Constant;
import com.demo.dingtalk.DingCallbackCrypto;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 钉钉工作流测试
 */
@Slf4j
@RestController
@RequestMapping("/dingTalkFlow")
public class DingTalkFlowController {

    String access_token = "";

    /**
     * 获取token
     * @return
     * @throws ApiException
     */
    @GetMapping("/getCorpToken")
    public String getCorpToken() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(Constant.APPKEY);
        request.setAppsecret(Constant.APPSECRET);
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        System.out.println(response.getBody());

        return response.getBody();
    }

    /**
     * 发起审批
     * @return
     * @throws ApiException
     */
    @GetMapping("/create")
    public String create() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");
        OapiProcessinstanceCreateRequest req = new OapiProcessinstanceCreateRequest();
        req.setAgentId(Constant.AGENTID);
        req.setProcessCode(Constant.PROCESS_CODE);

        //唐龙， 审批发起人
        req.setOriginatorUserId("manager1005");

        //发起人所在的部门，如果发起人属于根部门，传-1
        req.setDeptId(-1L);

        // 非必填项，审批人userid列表，最大列表长度20。 多个审批人用逗号分隔，按传入的顺序依次审批。
        //req.setApprovers("manager01, manager02");

        //非必填项，审批人列表。 支持会签/或签，优先级高于approvers变量。
        //req.setCcList("user2,user3");

        //非必填项，在什么节点抄送给抄送人： START   FINISH   START_FINISH
        //req.setCcPosition("START");

        //审批流表单参数，最大列表长度20。 仅支持下表列举的表单控件。
        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> formComponentValueVoList = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();
        OapiProcessinstanceCreateRequest.FormComponentValueVo formComponentValueVo = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        //表单每一栏的名称。
        formComponentValueVo.setName("原数据");
        //表单每一栏的值。
        formComponentValueVo.setValue("ASDFGH");

        formComponentValueVoList.add(formComponentValueVo);

        OapiProcessinstanceCreateRequest.FormComponentValueVo formComponentValueVo1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        //表单每一栏的名称。
        formComponentValueVo1.setName("新数据");
        //表单每一栏的值。
        formComponentValueVo1.setValue("qwerty");

        formComponentValueVoList.add(formComponentValueVo1);

        req.setFormComponentValues(formComponentValueVoList);


        /*List<ProcessInstanceApproverVo> processInstanceApproverVoList = new ArrayList<ProcessInstanceApproverVo>();
        ProcessInstanceApproverVo processInstanceApproverVo = new ProcessInstanceApproverVo();
        processInstanceApproverVoList.add(processInstanceApproverVo);
        processInstanceApproverVo.setTaskActionType("AND");
        processInstanceApproverVo.setUserIds(Arrays.asList("user1","user2"));
        req.setApproversV2(processInstanceApproverVoList);*/

        OapiProcessinstanceCreateResponse rsp = client.execute(req, access_token);


        return rsp.getBody();
    }




    @PostMapping("/callBack")
    public Map<String, String> callBack(
            @RequestParam(value = "msg_signature", required = false) String msg_signature,
            @RequestParam(value = "timestamp", required = false) String timeStamp,
            @RequestParam(value = "nonce", required = false) String nonce,
            @RequestBody(required = false) JSONObject json) {
        try {

            /*log.info("msg_signature -> " + msg_signature);
            log.info("timeStamp -> " + timeStamp);
            log.info("nonce -> " + nonce);
            log.info("json -> " + json.toJSONString());*/

            // 1. 从http请求中获取加解密参数

            // 2. 使用加解密类型
            // Constant.OWNER_KEY 说明：
            // 1、开发者后台配置的订阅事件为应用级事件推送，此时OWNER_KEY为应用的APP_KEY。
            // 2、调用订阅事件接口订阅的事件为企业级事件推送，
            // 此时OWNER_KEY为：企业的appkey（企业内部应用）或SUITE_KEY（三方应用）
            DingCallbackCrypto callbackCrypto = new DingCallbackCrypto(Constant.TOKEN, Constant.ENCODING_AES_KEY, Constant.APPKEY);
            String encryptMsg = json.getString("encrypt");
            String decryptMsg = callbackCrypto.getDecryptMsg(msg_signature, timeStamp, nonce, encryptMsg);

            log.info(decryptMsg);

            // 3. 反序列化回调事件json数据
            JSONObject eventJson = JSON.parseObject(decryptMsg);
            String eventType = eventJson.getString("EventType");

            // 4. 根据EventType分类处理
            if ("check_url".equals(eventType)) {
                // 测试回调url的正确性
                log.info("测试回调url的正确性");
            } else if ("user_add_org".equals(eventType)) {
                // 处理通讯录用户增加事件
                log.info("发生了：" + eventType + "事件");
            } else if("bpms_instance_change".equals(eventType)) {
                log.info("发生了：" + eventType + "事件");
            } else {
                // 添加其他已注册的
                log.info("发生了：" + eventType + "事件");
            }

            // 5. 返回success的加密数据
            Map<String, String> successMap = callbackCrypto.getEncryptedMap("success");
            return successMap;

        } catch (DingCallbackCrypto.DingTalkEncryptException e) {
            e.printStackTrace();
        }
        return null;
    }

}
