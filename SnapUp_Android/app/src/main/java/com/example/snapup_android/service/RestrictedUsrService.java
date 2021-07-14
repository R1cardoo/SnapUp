package com.example.snapup_android.service;

import com.example.snapup_android.pojo.RestrictedUsr;

public interface RestrictedUsrService {
    public void createRestrictedUsr(RestrictedUsr restrictedUsr);
    public RestrictedUsr findRestrictedUsr(String identity_id);
    public int deleteRestrictedUsr(RestrictedUsr restrictedUsr);

}
