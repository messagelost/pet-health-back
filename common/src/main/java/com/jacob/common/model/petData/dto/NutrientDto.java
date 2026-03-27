package com.jacob.common.model.petData.dto;

import lombok.Data;

@Data
public class NutrientDto {

    private String name;

    private String percent;

    public NutrientDto(String name, String percent) {
        this.name = name;
        this.percent = percent;
    }
}
