package com.snapup.service;

import com.snapup.pojo.RestrictedUsr;

public interface RestrictedUsrService {
    public void createRestrictedUsr(RestrictedUsr restrictedUsr);
    public RestrictedUsr findRestrictedUsr(String identity_id);
    public int deleteRestrictedUsr(RestrictedUsr restrictedUsr);

}
