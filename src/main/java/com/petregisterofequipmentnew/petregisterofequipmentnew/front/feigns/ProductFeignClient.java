/*package com.petregisterofequipmentnew.petregisterofequipmentnew.front.feigns;

import com.petregisterofequipmentnew.petregisterofequipmentnew.front.entities.ColorEquipment;
import com.petregisterofequipmentnew.petregisterofequipmentnew.front.entities.TypeEquipmentEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
public interface ProductFeignClient {

    @PostMapping("api/v1/product/create")
    ResponseEntity<ProductDto> addModel(@RequestBody ProductDto productDto);

    @GetMapping("api/v1/product/list-products")
    ResponseEntity<List<ProductDto>> getModel(@RequestParam(value = "nameProduct", required = false) String nameProduct,
                                              @RequestParam(value = "typeOfEquipment", required = false) TypeEquipmentEnum typeEquipmentEnum,
                                              @RequestParam(value = "color", required = false) ColorEquipment colorEquipment,
                                              @RequestParam(value = "price", required = false) @PositiveOrZero Integer price,
                                              @RequestParam(value = "size", required = false) @PositiveOrZero Integer size,
                                              @RequestParam(value = "isAvailability", required = false) Boolean isAvailability,
                                              @RequestParam(value = "countsDoor", required = false) Integer countsDoor,
                                              @RequestParam(value = "typeCompressor", required = false) String typeCompressor,
                                              @RequestParam(value = "size_dust_collect", required = false) Integer sizeDustCollect,
                                              @RequestParam(value = "countsRegime", required = false) Integer countsRegime,
                                              @RequestParam(value = "typeProcessor", required = false) String typeProcessor,
                                              @RequestParam(value = "category", required = false) String category,
                                              @RequestParam(value = "memoryPhone", required = false) Integer memoryPhone,
                                              @RequestParam(value = "countsSnaps", required = false) Integer countsSnaps,
                                              @RequestParam(value = "technology", required = false) String technology,
                                              @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                              @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(100) Integer limit,
                                              @RequestParam(value = "sortBy", defaultValue = "alphabet") ParametersSort parametersSort,
                                              @RequestParam(value = "sortOrder", defaultValue = "asc") DirectionSort directionSort
    );

    @PutMapping("api/v1/product/update-product")
    ResponseEntity<ProductDto> editModel(@RequestBody ProductDto productDto);

    @DeleteMapping("api/v1/product/delete/{id}")
    ResponseEntity<Boolean> deleteModel(@PathVariable @NotNull Long id);

}*/
