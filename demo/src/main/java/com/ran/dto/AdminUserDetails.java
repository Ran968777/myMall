package com.ran.dto;

import com.ran.model.UmsAdmin;
import com.ran.model.UmsPermission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述:
 * 用户详情
 *
 * @author RanJianSong
 * @create 2020-09-23 14:22
 */
public class AdminUserDetails implements UserDetails {

    /**
     * 用户信息
     */
    private UmsAdmin umsAdmin;
    /**
     * 用户权限
     */
    private List<UmsPermission> permissionList;

    public AdminUserDetails(UmsAdmin umsAdmin, List<UmsPermission> permissionList) {
        this.umsAdmin = umsAdmin;
        this.permissionList = permissionList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        // 查找如果权限值不为null则生成对应的SimpleGrantedAuthority,最后返回
        List<SimpleGrantedAuthority> collect = permissionList.stream().filter(permission -> permission.getValue() != null)
                .map(permission -> new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus().equals(1);
    }
}