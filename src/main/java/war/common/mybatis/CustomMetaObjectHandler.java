package war.common.mybatis;//package war.common.mybatis;
//
//import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.reflection.ReflectionException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Date;
//
///**
// * @Auther: Aaron
// * @Date: 2018/7/10 11:52
// * @Description: 自定义填充策略接口实现
// */
//public class CustomMetaObjectHandler extends MetaObjectHandler {
//
//    private final static Logger logger = LoggerFactory.getLogger(CustomMetaObjectHandler.class);
//
//
//
//    public void insertFill(MetaObject metaObject) {
//
//
//
//        try {
//            Object createTime = getFieldValByName("createTime", metaObject);
//            if (null == createTime) {
//                setFieldValByName("createTime", new Date(), metaObject);
//            }
//        } catch (Exception e) {
////            logger.error("自动更新createTime字段异常", e);
//            //表中没有updateBy字段，不填充，catch异常
//        }
//        try {
//            Object delFlag = getFieldValByName("delFlag", metaObject);
//            if (null == delFlag) {
//                setFieldValByName("delFlag", 0, metaObject);
//            }
//        } catch (Exception e) {
////            logger.error("自动更新createTime字段异常", e);
//            //表中没有updateBy字段，不填充，catch异常
//        }
//    }
//
//    @Override
//    public void updateFill(MetaObject metaObject) {
//
//        try {
//            Object updateTime = getFieldValByName("updateTime", metaObject);
//
//            setFieldValByName("updateTime", new Date(), metaObject);
//        } catch (ReflectionException e) {
////            logger.error("自动更新updateTime字段异常", e);
//            //表中没有updateBy字段，不填充，catch异常
//        }
//    }
//}
