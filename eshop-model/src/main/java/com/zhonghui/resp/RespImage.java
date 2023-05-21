package com.zhonghui.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhonghui.model.ImageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespImage {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_at;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated_at;
    private String url;

    public RespImage(ImageInfo imageInfo) {
        this.id=imageInfo.getId();
        this.created_at=imageInfo.getCreateTime();
        this.updated_at=imageInfo.getUpdateTime();
        this.url=imageInfo.getUrl();
    }
}
