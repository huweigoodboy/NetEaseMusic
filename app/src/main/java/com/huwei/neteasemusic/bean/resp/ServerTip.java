package com.huwei.neteasemusic.bean.resp;

/**
 * @author jerry
 * @date 2016/07/01
 */
public class ServerTip {
    public int code;
    public String msg;

    public ServerTip() {

    }

    public ServerTip(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }

    public void code(int code) {
        this.code = code;
    }

    public void msg(String msg) {
        this.msg = msg;
    }
}
