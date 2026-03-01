package com.jacob.security.scanner;

import com.jacob.common.annotation.ApiPermission;
import com.jacob.service.author.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
@Component
public class ApiPermissionScanner implements ApplicationRunner {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private SysMenuService sysMenuService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始扫描权限接口...");

        // 直接扫描指定包下的Controller类
        String[] basePackages = {"com.jacob.web"};

        for (String basePackage : basePackages) {
            scanPackageForControllers(basePackage);
        }

        log.info("权限接口扫描完成");
    }

    private void scanPackageForControllers(String basePackage) {
        // 直接通过ApplicationContext获取
        Map<String, Object> controllers = context.getBeansWithAnnotation(RestController.class);

        for (Object bean : controllers.values()) {
            Class<?> originalClass = getOriginalClass(bean);
            scanControllerClass(originalClass);
        }
    }

    private Class<?> getOriginalClass(Object bean) {
        Class<?> clazz = bean.getClass();
        if (clazz.getName().contains("$$SpringCGLIB$$")) {
            return clazz.getSuperclass();
        }
        return clazz;
    }

    private void scanControllerClass(Class<?> clazz) {

        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            ApiPermission api = method.getAnnotation(ApiPermission.class);

            if (api != null) {
                if (api.register()) {
                    log.info("注册权限接口：{}", method.getName());
                    sysMenuService.registerMenu(api.code(), api.name());
                }
            }
        }
    }
}
