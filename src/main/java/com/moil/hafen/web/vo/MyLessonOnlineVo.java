package com.moil.hafen.web.vo;

import lombok.Data;

@Data
public class MyLessonOnlineVo {
    private String categoryName;
    private Integer lessonId;
    private String lessonName;
    private String cover;
    private int totalChapterCount;
    private int useChapterCount;

    public MyLessonOnlineVo(Integer lessonId, String lessonName, String cover, int totalChapterCount, int useChapterCount) {
        this.lessonId = lessonId;
        this.lessonName = lessonName;
        this.cover = cover;
        this.totalChapterCount = totalChapterCount;
        this.useChapterCount = useChapterCount;
    }
}
