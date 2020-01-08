package com.qmr.entity;


public enum FileNameEnum {
    jpg, png;

    /**
     * 枚举文件名,属于图片放行,不属于返回false
     * @param fileType 文件名
     * @return boolean
     */
    public static boolean contains(String fileType) {
        for (FileNameEnum fileNameEnum : FileNameEnum.values()) {
            if (fileNameEnum.name().equals(fileType)) {
                return true;
            }
        }
        return false;
    }
}
