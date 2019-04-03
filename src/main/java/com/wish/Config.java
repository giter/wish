package com.wish;

/**
 * author: ff
 * date: 2017/1/10
 * description:
 */
public class Config {

//    小程序兑奖界面
    public final static String PRIZEPAGE="pages/index/index";

//    小程序首页
    public final static String INDEXPAGE="pages/index/index";

//    一件商品20积分
    public final static Integer ADDPOINT=20;

//    2000积分一件商品
    public final static Integer REDUCEPOINT=2000;

    public final static  Integer QUESTIONPOINT=1;

    /**
     * 服务器地址
     */
    public final static String SERVER = "https://www.xingyuanji.com/";
    /**
     * 上传的图片的存放地址
     */
//    public final static String UPLOAD_DIR = "H:/apache-tomcat-9.0.10/webapps/upload/";
//    public final static String UPLOAD_DIR = "/opt/apache-tomcat-9.0.2/webapps/upload/";
//    public final static String UPLOAD_DIR = "C:/Users/Administrator/Desktop/apache-tomcat-8.5.31/webapps/upload/";
    public final static String UPLOAD_DIR = "C:/Users/Administrator/Desktop/apache-tomcat-8.5.37/webapps/upload/";

    /**
     * 图片的访问地址,软连接地址
     */
    public final static String RESOURCE_DIR = "upload/";

    //导出excel路径
    public final static String EXCEL_SAVE_PATH = UPLOAD_DIR;
//    public final static String EXCEL_SAVE_PATH = "H:\\";

//    //公众号appid
    public final static String APPID="wx7dee141dcedc0421";
    public final static String SECRET="ee702e5c923782990236ce7f2decdff4";
    public static final String APP_KEY = "xyj185568014781qaz2wsx3edc4rfv5t";
    public static final String MCH_ID = "1504738871";  //商户号
    public static final String URL_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public final static  String NOTIFYURL="https://www.xingyuanji.com/wish/wxpay/notify";//异步结果接口


    //公众号secret  ed3a5dbc1aafc037f9ecc1eccfe0a7e7

//    个人
//    public final static String APPID="wx4bdabda27edf13ef";
//    public final static String SECRET="d90700dd7f7662ada79df172cf0bb4c5";


}
