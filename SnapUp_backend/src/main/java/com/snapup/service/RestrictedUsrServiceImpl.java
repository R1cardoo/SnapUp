package com.snapup.service;


import com.snapup.dao.RestrictedUsrMapper;
import com.snapup.pojo.RestrictedUsr;

import java.util.List;

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
    public List<RestrictedUsr> findAllRestrictedUsr() {
        return restrictedUsrMapper.findAllRestrictedUsr();
    }
}
