package com.petregisterofequipmentnew.petregisterofequipmentnew.front.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AttributesDto implements Serializable {

    private Long id;
    private String nameDevice;
    private Integer serialNumber;
    private ColorEquipment color;
    private Integer size;
    private Integer price;
    private Boolean isAvailabilityProducts;
    @JsonIgnore
    private List<ProductDto> productDtoList;

    private Integer countsDoor;
    private String typeCompressor;
    private Integer sizeDustCollect;
    private Integer countsRegime;
    private String typeProcessor;
    private String category;
    private Integer memoryPhone;
    private Integer countsSnaps;
    private String technology;
    @JsonIgnore
    private Integer serviceFlag;

}
