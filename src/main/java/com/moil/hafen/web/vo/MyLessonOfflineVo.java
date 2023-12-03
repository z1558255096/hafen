package com.moil.hafen.web.vo;

import lombok.Data;

@Data
public class MyLessonOfflineVo {
    private String categoryName;
    private Integer lessonId;
    private String lessonName;
    private String cover;
    private int residueCount;

    public MyLessonOfflineVo(Integer lessonId, String lessonName, String cover, int residueCount) {
        this.lessonId = lessonId;
        this.lessonName = lessonName;
        this.cover = cover;
        this.residueCount = residueCount;
    }
}
