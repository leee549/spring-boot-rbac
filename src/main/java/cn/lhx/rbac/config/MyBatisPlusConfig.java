package cn.lhx.rbac.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * MyBatisPlus 分页配置
 *
 * @author lee549
 * @date 2020/3/26 10:38
 */
@Configuration
public class MyBatisPlusConfig {

  /**
   * 分页插件配置
   *
   * @return 分页插件对象
   */
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    PaginationInterceptor interceptor = new PaginationInterceptor();
    List<ISqlParser> sqlParserList = new ArrayList<>();
    // 攻击 SQL 阻断解析器、加入解析链
    sqlParserList.add(new BlockAttackSqlParser());
    interceptor.setSqlParserList(sqlParserList);
    return interceptor;
  }
}
