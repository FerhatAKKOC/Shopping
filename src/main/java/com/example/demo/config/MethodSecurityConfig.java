package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class MethodSecurityConfig {
    /*
        Spring Security Module Annotations for method level security such as @Secured("ROLE_ADMIN")
        The prePostEnabled property enables Spring Security pre/post annotations.
        The securedEnabled property determines if the @Secured annotation should be enabled.
        The jsr250Enabled property allows us to use the @RoleAllowed annotation.
     */
}
