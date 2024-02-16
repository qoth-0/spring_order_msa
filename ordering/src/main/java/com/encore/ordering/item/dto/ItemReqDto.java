package com.encore.ordering.item.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ItemReqDto {
    private String name;
    private String category;
    private int price;
    private int stockQuantity;
    private MultipartFile itemImage; // 이미지를 파일형태로 받음
}
