package com.snapup.service;

import com.snapup.pojo.RestrictedUsr;

import java.util.List;

public interface RestrictedUsrService {
    public void createRestrictedUsr(RestrictedUsr restrictedUsr);
    public RestrictedUsr findRestrictedUsr(String identity_id);
    public int deleteRestrictedUsr(RestrictedUsr restrictedUsr);
    public List<RestrictedUsr> findAllRestrictedUsr();
}
