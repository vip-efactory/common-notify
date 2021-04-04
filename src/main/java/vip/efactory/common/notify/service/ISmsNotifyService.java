package vip.efactory.common.notify.service;

import java.util.List;
import java.util.Map;

/**
 * @author dbdu
 * @since 2021/04/04 下午13:04
 */
public interface ISmsNotifyService extends INotifyService {

    /**
     * 单个发送
     *
     * @param smsCode 短信模板编码
     * @param params  模板占位参数
     * @param mobile  手机号,若是多个用逗号分隔
     * @return boolean true，发送成功，false发送失败
     */
    boolean sendNotify(String smsCode, Map<String, Object> params, String mobile);

    /**
     * 批量发送
     *
     * @param smsCode    短信模板编码
     * @param params     模板占位参数
     * @param mobileList 手机号集合
     * @return boolean true，发送成功，false发送失败
     */
    boolean sendNotifyBatch(String smsCode, Map<String, Object> params, List<String> mobileList);

    /**
     * 单个发送
     *
     * @param smsCode  短信模板编码
     * @param params   模板占位参数
     * @param mobile   手机号,若是多个用逗号分隔
     * @param signName 短信签名
     * @return boolean true，发送成功，false发送失败
     */
    boolean sendNotify(String smsCode, Map<String, Object> params, String mobile, String signName);

    /**
     * 批量发送
     *
     * @param smsCode    短信模板编码
     * @param params     模板占位参数
     * @param mobileList 手机号集合
     * @param signName   短信签名
     * @return boolean true，发送成功，false发送失败
     */
    boolean sendNotifyBatch(String smsCode, Map<String, Object> params, List<String> mobileList, String signName);

}
