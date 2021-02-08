package org.qf.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.qf.pojo.SmbmsUser;
import org.qf.service.SmbmsUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MyRelam extends AuthorizingRealm {

    @Resource
    private SmbmsUserService smbmsUserService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 身份认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        AuthenticationInfo authenticationInfo=null;

        //用户名
        String name=(String)authenticationToken.getPrincipal();
        SmbmsUser smbmsUser=smbmsUserService.queryLogin(name);
        if (smbmsUser!=null){
            authenticationInfo=new SimpleAuthenticationInfo(smbmsUser.getUsername(),smbmsUser.getUserpassword(),getName());
        }

        return authenticationInfo;
    }
}
