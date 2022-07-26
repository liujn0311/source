package com.liugroup.springboot_base.common;

public class Constant {

    public static class ErrorCode{
        public static final Integer FILE_NULL = 100001;
        public static final Integer FILE_MAX = 100002;
        public static final Integer FILE_SUFFIX = 100003;
    }

    public static class ErrorMsg{
        public static final String FILE_NULL = "图片不能为空";
        public static final String FILE_MAX = "图片最大为500KB";
        public static final String FILE_SUFFIX = "图片格式允许为jpg和png";
    }
}
