package com.scut.originsystem.enums;

public enum SqlConditionType {
    AND_LIKE,
    AND_EQUAL,
    /**
     * 变量名必须 以"_start"作为后缀
     */
    AND_BETWEEN_DATE_START,
    /**
     * 变量名必须 以"_end"作为后缀
     */
    AND_BETWEEN_DATE_END,
    ORDER_BY,
    IS_ASC,
    LIMIT,
    OFFSET
}
