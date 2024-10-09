package com.petregisterofequipmentnew.petregisterofequipmentnew.front.feigns;/*package com.petregisterofequipmentnew.front.feigns;

import com.petregisterofequipmentnew.back.dtos.AttributesDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "attributesfeignclients")
@Validated
public interface AttributesFeignClient {

    @PostMapping("api/v1/attributes/create")
    ResponseEntity<AttributesDto> createAttributes(@RequestBody @NotNull AttributesDto attributesDto);
    @GetMapping("api/v1/attributes/find-attributes-nameDevice/{nameDevice}")
    ResponseEntity<List<AttributesDto>> findAttributesByName(@PathVariable String nameDevice,
                                                             @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                                             @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(100) Integer limit);
    @GetMapping("api/v1/attributes/find-count-attributes-nameDevice/{nameDevice}")
    Long findCountAttributesByName(@PathVariable String nameDevice);
    *//*ResponseEntity<Long> findCountAttributesByName(@PathVariable String nameDevice);*//*
    @DeleteMapping("api/v1/attributes/delete/{id}")
    ResponseEntity<Boolean> deleteAttributes(@PathVariable @NotNull Long id);

}*/
