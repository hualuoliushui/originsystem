package com.scut.originsystem.receiveentity;

import com.scut.originsystem.annotation.SqlConditionAnno;
import com.scut.originsystem.enums.SqlConditionType;
import com.scut.originsystem.util.SqlUtil;

public class PageConditions {
    private int page;
    @SqlConditionAnno(type = SqlConditionType.LIMIT)
    private int limit;
    @SqlConditionAnno(type = SqlConditionType.OFFSET)
    private int offset;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        if(this.page!=0){
            this.limit= SqlUtil.PAGE_LIMIT;
            this.offset = SqlUtil.getOffset(this.page);
        }else {
            this.limit = 0;
            this.offset = 0;
        }
        return limit;
    }

    public int getOffset() {
        if(this.page!=0){
            this.limit= SqlUtil.PAGE_LIMIT;
            this.offset = SqlUtil.getOffset(this.page);
        }else {
            this.limit = 0;
            this.offset = 0;
        }
        return offset;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
