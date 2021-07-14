package com.example.snapup_android.service;


import com.example.snapup_android.dao.RestrictedUsrMapper;
import com.example.snapup_android.pojo.RestrictedUsr;

public class RestrictedUsrServiceImpl implements RestrictedUsrService{
    private RestrictedUsrMapper restrictedUsrMapper;

    public void setRestrictedUsrMapper(RestrictedUsrMapper restrictedUsrMapper) {
        this.restrictedUsrMapper = restrictedUsrMapper;
    }

    public void createRestrictedUsr(RestrictedUsr restrictedUsr) {
        if(restrictedUsrMapper.findRestrictedUsr(restrictedUsr.getIdentity_id()) == null){
            restrictedUsrMapper.createRestrictedUsr(restrictedUsr);
        }
    }

    public RestrictedUsr findRestrictedUsr(String identity_id) {
        return restrictedUsrMapper.findRestrictedUsr(identity_id);
    }

    public int deleteRestrictedUsr(RestrictedUsr restrictedUsr) {
        return restrictedUsrMapper.deleteRestrictedUsr(restrictedUsr);
    }
}
