package com.zzx.zzx_music_recommendation_system.enums;

public enum UserRoleEnum {
    //游客、用户、管理员
    TOURIST(0, "TOURIST"),
    USER(1, "USER"),
    ADMIN(2, "ADMIN");

    private Integer code;

    private String message;

    UserRoleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessageByCode(Integer code) {
        for (UserRoleEnum u : UserRoleEnum.values()) {
            if (u.code.equals(code)) {
                return u.message;
            }
        }
        return null;
    }
}
