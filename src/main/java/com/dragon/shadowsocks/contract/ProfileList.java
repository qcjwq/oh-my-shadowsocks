package com.dragon.shadowsocks.contract;

import com.dragon.shadowsocks.contract.common.RequestHead;
import com.dragon.shadowsocks.contract.common.ResponseHead;
import com.dragon.shadowsocks.contract.model.ProfileInfo;

import java.util.List;

/**
 * Created by cjw on 2017/6/18.
 */
public class ProfileList {
    private ProfileList() {
    }

    public static class Request {
        private RequestHead head;

        public RequestHead getHead() {
            return head;
        }

        public void setHead(RequestHead head) {
            this.head = head;
        }
    }

    public static class Response {
        private ResponseHead head = new ResponseHead();
        private List<ProfileInfo> profileInfoList;

        public ResponseHead getHead() {
            return head;
        }

        public void setHead(ResponseHead head) {
            this.head = head;
        }

        public List<ProfileInfo> getProfileInfoList() {
            return profileInfoList;
        }

        public void setProfileInfoList(List<ProfileInfo> profileInfoList) {
            this.profileInfoList = profileInfoList;
        }
    }
}
