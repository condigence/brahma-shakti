package com.condigence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {


	@Id
    private String id;
    private String moduleName;
    private String name;
    private String type;
    private String imagePath;
    private long imageSize;
    /// Image format : name(0,3)+datTime
    private String imageName;
    private byte[] pic;

}
