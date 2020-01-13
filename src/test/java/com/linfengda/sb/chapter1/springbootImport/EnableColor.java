package com.linfengda.sb.chapter1.springbootImport;

import com.linfengda.sb.chapter1.springbootImport.color.Red;
import com.linfengda.sb.chapter1.springbootImport.target.ColorImportBeanDefinitionRegistrar;
import com.linfengda.sb.chapter1.springbootImport.target.ColorImportSelector;
import com.linfengda.sb.chapter1.springbootImport.target.ColorRegistrarConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 描述: @Import可以传入四种类型：普通类、配置类、ImportSelector的实现类，ImportBeanDefinitionRegistrar的实现类。
 *
 * @author linfengda
 * @create 2019-12-30 15:01
 */
@Import({Red.class, ColorRegistrarConfiguration.class, ColorImportSelector.class, ColorImportBeanDefinitionRegistrar.class})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableColor {
}