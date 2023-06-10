package com.stewart.datatransport.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * @author stewart
 * @date 2023/1/19
 */
public class MybatisPlusGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/data-transport", "root", "rootadmin")
                .globalConfig(builder -> {
                    builder.author("stewart") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("/Users/stewart/Desktop"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.stewart.datatransport") // 设置父包名
                            .moduleName("data-transport") // 设置父包模块名
                            // 设置mapperXml生成路径
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "/Users/stewart/Desktop"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("user","database_config","data_set","data_object", "data_migrate_record");
                })
                .execute();
    }
}
