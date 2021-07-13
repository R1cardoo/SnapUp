package com.snapup.dao;

import com.snapup.pojo.RestrictedUsr;

public interface RestrictedUsrMapper {
    //创建一个限制用户
    public void createRestrictedUsr(RestrictedUsr restrictedUsr);
    //通过用户ID查询限制用户
    public RestrictedUsr findRestrictedUsr(String identity_id);
    //删除一限制个用户
    public int deleteRestrictedUsr(RestrictedUsr restrictedUsr);
}
