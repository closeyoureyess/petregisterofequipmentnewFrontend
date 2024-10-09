package com.petregisterofequipmentnew.petregisterofequipmentnew.front.productspage;

import com.petregisterofequipmentnew.petregisterofequipmentnew.front.entities.AttributesDto;
import com.petregisterofequipmentnew.petregisterofequipmentnew.front.feigns.AttributesEndpoints;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.petregisterofequipmentnew.petregisterofequipmentnew.front.constants.ConstantsFront.EMPTY_SPACE;

@SpringComponent
@UIScope
public class CreateProductDialog extends Dialog {

    @Autowired
    private RestClient restClient;

    public CreateProductDialog() {

        ComboBox<String> comboBox = createNewCombox();

        Button saveButton = new Button("Сохранить", event -> {
            String selectedValue = comboBox.getValue();
            if (selectedValue != null && !selectedValue.isEmpty()) {
                // Логика сохранения или обработки выбранного значения
                Notification.show("Сохранено значение: " + selectedValue);
            } else {
                Notification.show("Пожалуйста, выберите или введите значение.", 3000, Notification.Position.MIDDLE);
            }
            close(); // Закрыть диалог после сохранения
        });
        add(comboBox, saveButton);
    }

    private ComboBox<String> createNewCombox() {
        ComboBox<String> comboBox = new ComboBox<>("Выберите элемент");
        comboBox.setAllowCustomValue(true); // Разрешить ввод нового значения
        comboBox.setItems(query -> filterUserInputInCombox(query.getFilter().orElse(EMPTY_SPACE),
                query.getOffset(), query.getLimit()));
        return comboBox;
    }

    private Stream<String> filterUserInputInCombox(String queryFilter, Integer offset, Integer limit) {
        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        AttributesEndpoints attributesEndpoints = HttpServiceProxyFactory
                .builderFor(restClientAdapter)
                .build()
                .createClient(AttributesEndpoints.class);
        List<AttributesDto> attributesDtoList = Optional.ofNullable(
                attributesEndpoints.findAttributesByName(queryFilter, offset, limit))
                .map(request -> request.getBody()).orElse(new LinkedList<>());
        List<String> attributesList = new LinkedList<>();
        if (!attributesDtoList.isEmpty()) {
            for (AttributesDto attributesDto : attributesDtoList)
                attributesList.add(attributesDto.getNameDevice());
        }
        return attributesList.stream();
    }

    /*private Stream<String> filterUserInputInCombox(String queryFilter, Integer offset, Integer limit) {
        List<AttributesDto> attributesDtoList = Optional.ofNullable(
                attributesEndpoints.findAttributesByName(queryFilter, offset, limit))
                .map(request -> request.getBody()).orElse(new LinkedList<>());
        List<String> attributesList = new LinkedList<>();
        if (!attributesDtoList.isEmpty()) {
            for (AttributesDto attributesDto : attributesDtoList)
                attributesList.add(attributesDto.getNameDevice());
        }
        return attributesList.stream();
    }*/

    /*private ComboBox<String> createNewCombox() {
        ComboBox<String> comboBox = new ComboBox<>(CHOOSE_ELEMENT);
        comboBox.setAllowCustomValue(TEST_VALUE_BOOLEAN);
        comboBox.setDataProvider((filter, offset, limit) -> {
                    return filterUserInputInCombox(filter, offset, limit);
                },
                filterText -> {
                    return countAttributesByName(filterText);
                }
        );
        *//*comboBox.addValueChangeListener(event -> {
            String currentValue = event.getValue();
            if (currentValue != null) {
                Notification.show("Вы выбрали: " + currentValue);
            }
        });*//*
        return comboBox;
    }

    private Stream<String> filterUserInputInCombox(String queryFilter, Integer offset, Integer limit) {
        *//*List<AttributesDto> attributesDtoList = attributesFeignClient
                .findAttributesByName(queryFilter, offset, limit)
                .getBody();*//*
        List<AttributesDto> attributesDtoList = attributesService.findProductByName(queryFilter, offset, limit);
        List<String> attributesList = new LinkedList<>();
        if (attributesDtoList != null) {
            for (AttributesDto attributesDto : attributesDtoList)
                attributesList.add(attributesDto.getNameDevice());
        }
        return attributesList.stream();
    }

    private int countAttributesByName(String filter) {
        *//*ResponseEntity<Long> responseEntity = attributesFeignClient.findCountAttributesByName(filter);
        Long newLong = responseEntity.getBody();*//*
     *//*Optional.ofNullable(attributesFeignClient.findCountAttributesByName(filter)).map(request -> Integer.valueOf(request.toString())).orElse(ZERO_FLAG);*//*
        return Optional.ofNullable(attributesService.findCountAttributesByName(filter))
                .map(request -> Integer.parseInt(request.get().toString())).orElse(ZERO_FLAG);
    }*/
}
