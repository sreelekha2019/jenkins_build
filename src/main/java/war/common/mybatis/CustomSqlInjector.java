package war.common.mybatis;///**
// * Copyright (c) 2011-2020, hubin (jobob@qq.com).
// *
// * Licensed under the Apache License, Version 2.0 (the "License"); you may not
// * use this file except in compliance with the License. You may obtain a copy of
// * the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
// * License for the specific language governing permissions and limitations under
// * the License.
// */
//package war.common.mybatis;
//
//import com.baomidou.mybatisplus.entity.TableInfo;
//import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
//import org.apache.ibatis.builder.MapperBuilderAssistant;
//import org.apache.ibatis.mapping.SqlSource;
//import org.apache.ibatis.session.Configuration;
//
///**
// * <p>
// * 测试自定义注入 SQL
// * </p>
// *
// * @author hubin
// * @Date 2016-07-23
// */
//
///**
// * @Auther: Aaron
// * @Date: 2018/7/10 11:51
// * @Description: 自定义注入 SQL
// */
//public class CustomSqlInjector extends LogicSqlInjector {
//
//	@Override
//	public void inject(Configuration configuration, MapperBuilderAssistant builderAssistant, Class<?> mapperClass,
//                       Class<?> modelClass, TableInfo table) {
//		/* 添加一个自定义方法 */
//		deleteOfLogicById(mapperClass, modelClass, table);
//	}
//
//	public void deleteOfLogicById(Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
//
//		/* 执行 SQL ，动态 SQL 参考类 SqlMethod */
//		String sql = String.format("UPDATE %s SET del_flag = %s WHERE id = #{id}",table.getTableName(), "1");
//
//		/* mapper 接口方法名一致 */
//		String method = "deleteOfLogicById";
//		SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
//		this.addUpdateMappedStatement(mapperClass, modelClass, method, sqlSource);
//	}
//
//}
