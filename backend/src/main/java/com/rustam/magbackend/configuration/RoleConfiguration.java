package com.rustam.magbackend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

@Configuration
public class RoleConfiguration {
    @Value("${privileges.WATCH_MATURE}") private String PRIVILEGE_WATCH_MATURE;
    @Value("${privileges.PUBLISH_SAFE}") private String PRIVILEGE_PUBLISH_SAFE;
    @Value("${privileges.PUBLISH_MATURE}") private String PRIVILEGE_PUBLISH_MATURE;
    @Value("${privileges.PUBLICATION_RD}") private String PRIVILEGE_PUBLICATION_RD;
    @Value("${privileges.PUBLICATION_WR}") private String PRIVILEGE_PUBLICATION_WR;
    @Value("${privileges.USER_RD}") private String PRIVILEGE_USER_RD;
    @Value("${privileges.USER_WR}") private String PRIVILEGE_USER_WR;
    @Value("${privileges.STAFF_RD}") private String PRIVILEGE_STAFF_RD;
    @Value("${privileges.STAFF_WR}") private String PRIVILEGE_STAFF_WR;
    @Value("${privileges.STRUCT_RD}") private String PRIVILEGE_STRUCT_RD;
    @Value("${privileges.STRUCT_WR}")private String PRIVILEGE_STRUCT_WR;

    @Value("${roles.USER_WATCH_SAFE}") private String ROLE_USER_WATCH_SAFE;
    @Value("${roles.USER_WATCH_ALL}") private String ROLE_USER_WATCH_ALL;
    @Value("${roles.USER_PUBLISH_SAFE}") private String ROLE_USER_PUBLISH_SAFE;
    @Value("${roles.USER_PUBLISH_ALL}") private String ROLE_USER_PUBLISH_ALL;
    @Value("${roles.STAFF_MODERATOR}") private String ROLE_STAFF_MODERATOR;
    @Value("${roles.STAFF_ADMIN}") private String ROLE_STAFF_ADMIN;
    @Value("${roles.STAFF_HEAD_ADMIN}") private String ROLE_STAFF_HEAD_ADMIN;

    /*@Bean
    public RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s > %s \n %s > %s \n ",ROLE_STAFF_HEAD_ADMIN, ROLE_STAFF_ADMIN, ROLE_STAFF_ADMIN, ROLE_STAFF_MODERATOR));
        sb.append(String.format("%s > %s \n %s > %s \n ",ROLE_STAFF_MODERATOR, ROLE_USER_WATCH_ALL, ROLE_USER_WATCH_ALL, ROLE_USER_WATCH_SAFE));
        sb.append(String.format("%s > %s \n %s > %s \n ",ROLE_USER_PUBLISH_ALL, ROLE_USER_PUBLISH_SAFE, ROLE_USER_PUBLISH_ALL, ROLE_USER_WATCH_ALL));
        sb.append(String.format("%s > %s",ROLE_USER_PUBLISH_SAFE, ROLE_USER_WATCH_SAFE));
        roleHierarchy.setHierarchy(sb.toString());
        return roleHierarchy;
    }*/

    public String getPRIVILEGE_WATCH_MATURE() {
        return PRIVILEGE_WATCH_MATURE;
    }

    public String getPRIVILEGE_PUBLISH_SAFE() {
        return PRIVILEGE_PUBLISH_SAFE;
    }

    public String getPRIVILEGE_PUBLISH_MATURE() {
        return PRIVILEGE_PUBLISH_MATURE;
    }

    public String getPRIVILEGE_PUBLICATION_RD() {
        return PRIVILEGE_PUBLICATION_RD;
    }

    public String getPRIVILEGE_PUBLICATION_WR() {
        return PRIVILEGE_PUBLICATION_WR;
    }

    public String getPRIVILEGE_USER_RD() {
        return PRIVILEGE_USER_RD;
    }

    public String getPRIVILEGE_USER_WR() {
        return PRIVILEGE_USER_WR;
    }

    public String getPRIVILEGE_STAFF_RD() {
        return PRIVILEGE_STAFF_RD;
    }

    public String getPRIVILEGE_STAFF_WR() {
        return PRIVILEGE_STAFF_WR;
    }

    public String getPRIVILEGE_STRUCT_RD() {
        return PRIVILEGE_STRUCT_RD;
    }

    public String getPRIVILEGE_STRUCT_WR() {
        return PRIVILEGE_STRUCT_WR;
    }

    public String getROLE_USER_WATCH_SAFE() {
        return ROLE_USER_WATCH_SAFE;
    }

    public String getROLE_USER_WATCH_ALL() {
        return ROLE_USER_WATCH_ALL;
    }

    public String getROLE_USER_PUBLISH_SAFE() {
        return ROLE_USER_PUBLISH_SAFE;
    }

    public String getROLE_USER_PUBLISH_ALL() {
        return ROLE_USER_PUBLISH_ALL;
    }

    public String getROLE_STAFF_MODERATOR() {
        return ROLE_STAFF_MODERATOR;
    }

    public String getROLE_STAFF_ADMIN() {
        return ROLE_STAFF_ADMIN;
    }

    public String getROLE_STAFF_HEAD_ADMIN() {
        return ROLE_STAFF_HEAD_ADMIN;
    }
}
