package com.zzx.zzx_music_recommendation_system.vo;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/20 14:11
 */
public class SshModel {
    /**
     * 状态码
     */
    private int code;

    /**
     * 信息
     */
    private String info;

    /**
     * 错误信息
     */
    private String error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "SshModel [code=" + code + ", info=" + info + ", error=" + error + "]";
    }
}

