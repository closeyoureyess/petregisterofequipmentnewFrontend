package com.petregisterofequipmentnew.petregisterofequipmentnew.front.productspage;

import com.petregisterofequipmentnew.petregisterofequipmentnew.front.entities.AttributesDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

import static com.petregisterofequipmentnew.petregisterofequipmentnew.front.constants.ConstantsClass.TRUE_VALUE_BOOLEAN;
import static com.petregisterofequipmentnew.petregisterofequipmentnew.front.constants.ConstantsFront.*;
import static com.petregisterofequipmentnew.petregisterofequipmentnew.front.exceptions.DescriptionExeptions.*;

@SpringComponent
@UIScope
@Slf4j
public class CreateProductDialog extends Dialog {

    @Autowired
    private RestTemplate restTemplate; // Внедрение RestTemplate

    public CreateProductDialog() {
        ComboBox<String> comboBox = createNewCombox();

        Button saveButton = new Button(CREATE_NEW_ENTITY, event -> {
            close(); // Закрыть диалог после сохранения
        });

        add(comboBox, saveButton);
    }

    private ComboBox<String> createNewCombox() {
        ComboBox<String> comboBox = getStringComboBox();
        // Реализация DataProvider с корректными коллбэками
        DataProvider<String, String> dataProvider = DataProvider.fromFilteringCallbacks(
                // Коллбэк для получения данных
                query -> {
                    String filter = query.getFilter().orElse(EMPTY_SPACE); // Получаем строку фильтра
                    int offset = query.getOffset();
                    int limit = query.getLimit();

                    List<AttributesDto> response = new LinkedList<>();
                    if (!filter.isEmpty()) {
                        try {
                            ResponseEntity<List<AttributesDto>> responseEntity = restTemplate.exchange(
                                    "/api/v1/attributes/find-attributes-nameDevice/{nameDevice}?offset={offset}&limit={limit}&serviceFlag=1",
                                    org.springframework.http.HttpMethod.GET,
                                    null,
                                    new ParameterizedTypeReference<List<AttributesDto>>() {},
                                    filter, offset, limit
                            );
                            response = responseEntity.getBody();
                        } catch (RestClientException e) {
                            log.error(BACKEND_ERROR_RESPONSE.getDescription(), e.getCause() + SPACE + e.getMessage());
                            Notification.show(LOADING_DATA_ERROR);
                        }
                    }

                    List<String> attributesList = new LinkedList<>();
                    if (response != null) {
                        for (AttributesDto attributesDto : response) {
                            if (attributesDto.getNameDevice() != null) {
                                attributesList.add(attributesDto.getNameDevice());
                            }
                        }
                    }
                    return attributesList.stream();
                },
                // Коллбэк для подсчета общего количества элементов
                query -> {
                    String filter = query.getFilter().orElse("");
                    Long count = 0L;
                    if (!filter.isEmpty()) {
                        try {
                            count = restTemplate.getForObject(
                                    "/api/v1/attributes/find-count-attributes-nameDevice/{nameDevice}",
                                    Long.class,
                                    filter
                            );
                        } catch (RestClientException e) {
                            log.error(COUNTING_ERROR.getDescription(), e.getCause() + SPACE + e.getMessage());
                            Notification.show(COUNTING_ELEMENT_ERROR);
                        }
                    }
                    return count != null ? count.intValue() : 0;
                }
        );

        comboBox.setDataProvider(dataProvider, e -> e); // Устанавливаем DataProvider
        return comboBox;
    }

    private static ComboBox<String> getStringComboBox() {
        ComboBox<String> comboBox = new ComboBox<>(CHOOSE_ELEMENT);
        comboBox.setAllowCustomValue(TRUE_VALUE_BOOLEAN); // Разрешить ввод нового значения

        // Обработчик для пользовательского ввода
        comboBox.addCustomValueSetListener(event -> {
            String value = event.getDetail();
            if (value == null || value.trim().isEmpty()) {
                Notification.show(EMPTY_VALUE_NOT_DENIED);
                comboBox.clear(); // Сбросить значение
            }
        });
        return comboBox;
    }
}