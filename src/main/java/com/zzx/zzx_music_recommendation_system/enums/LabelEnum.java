package com.zzx.zzx_music_recommendation_system.enums;

public enum LabelEnum {
    //"blues", "classical", "country", "disco", "hiphop", "jazz", "metal", "pop", "reggae", "rock"
    BLUES(1, "blues"),
    CLASSICAL(2, "classical"),
    COUNTRY(3, "country"),
    DISCO(4, "disco"),
    HIPHOP(5, "hiphop"),
    JAZZ(6, "jazz"),
    METAL(7, "metal"),
    POP(8, "pop"),
    REGGAE(9, "reggae"),
    ROCK(10, "rock");

    private int code;
    private String label;

    LabelEnum(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static int getCodeByLabel(String label) {
        for (LabelEnum labelEnum : LabelEnum.values()) {
            if (labelEnum.getLabel().equalsIgnoreCase(label)) {
                return labelEnum.getCode();
            }
        }
        return -1;
    }

}
