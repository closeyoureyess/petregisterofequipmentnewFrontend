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
import java.util.stream.Stream;

@SpringComponent
@UIScope
@Slf4j
public class CreateProductDialog extends Dialog {

    @Autowired
    private RestTemplate restTemplate; // Внедрение RestTemplate

    public CreateProductDialog() {
        ComboBox<String> comboBox = createNewCombox();

        Button saveButton = new Button("Сохранить", event -> {
            try {
                ResponseEntity<List<AttributesDto>> responseEntity = restTemplate.exchange(
                        "/api/v1/attributes/find-attributes-nameDevice/fridge",
                        org.springframework.http.HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<AttributesDto>>() {}
                );
                List<AttributesDto> response = responseEntity.getBody();

                StringBuilder test = new StringBuilder();
                if (response != null) {
                    for (AttributesDto attributesDto : response) {
                        if (attributesDto.getNameDevice() != null)
                            test.append(" ").append(attributesDto.getNameDevice());
                    }
                }
                Notification.show("Пожалуйста, выберите или введите значение. " + test.toString());
            } catch (RestClientException e) {
                log.error("Ошибка при сохранении данных: ", e);
                Notification.show("Ошибка при сохранении данных.");
            }
            close(); // Закрыть диалог после сохранения
        });

        add(comboBox, saveButton);
    }

    private ComboBox<String> createNewCombox() {
        ComboBox<String> comboBox = new ComboBox<>("Выберите элемент");
        comboBox.setAllowCustomValue(true); // Разрешить ввод нового значения

        // Обработчик для пользовательского ввода
        comboBox.addCustomValueSetListener(event -> {
            String value = event.getDetail();
            if (value == null || value.trim().isEmpty()) {
                Notification.show("Пустое значение не разрешено!");
                comboBox.clear(); // Сбросить значение
            }
        });

        // Реализация DataProvider с корректными коллбэками
        DataProvider<String, String> dataProvider = DataProvider.fromFilteringCallbacks(
                // Коллбэк для получения данных
                query -> {
                    String filter = query.getFilter().orElse(""); // Получаем строку фильтра
                    int offset = query.getOffset();
                    int limit = query.getLimit();

                    List<AttributesDto> response = new LinkedList<>();
                    if (!filter.isEmpty()) {
                        try {
                            ResponseEntity<List<AttributesDto>> responseEntity = restTemplate.exchange(
                                    "/api/v1/attributes/find-attributes-nameDevice/{nameDevice}?offset={offset}&limit={limit}",
                                    org.springframework.http.HttpMethod.GET,
                                    null,
                                    new ParameterizedTypeReference<List<AttributesDto>>() {},
                                    filter, offset, limit
                            );
                            response = responseEntity.getBody();
                        } catch (RestClientException e) {
                            log.error("Ошибка при запросе к бэкенду: ", e);
                            Notification.show("Ошибка при загрузке данных.");
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
                            log.error("Ошибка при подсчете элементов: ", e);
                            Notification.show("Ошибка при подсчете элементов.");
                        }
                    }
                    return count != null ? count.intValue() : 0;
                }
        );

        comboBox.setDataProvider(dataProvider, e -> e); // Устанавливаем DataProvider
        return comboBox;
    }
}


/*package com.petregisterofequipmentnew.petregisterofequipmentnew.front.productspage;

import com.petregisterofequipmentnew.petregisterofequipmentnew.front.entities.AttributesDto;
import com.petregisterofequipmentnew.petregisterofequipmentnew.front.feigns.AttributesEndpoints;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.petregisterofequipmentnew.petregisterofequipmentnew.front.constants.ConstantsFront.EMPTY_SPACE;
import static com.petregisterofequipmentnew.petregisterofequipmentnew.front.constants.ConstantsFront.SPACE;

@SpringComponent
@UIScope
@Slf4j
public class CreateProductDialog extends Dialog {

    @Autowired
    private RestClient restClient;

    public CreateProductDialog() {

        ComboBox<String> comboBox = createNewCombox();

        Button saveButton = new Button("Сохранить", event -> {
            List<AttributesDto> response = restClient.get()
                    .uri("/find-attributes-nameDevice/fridge")
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<List<AttributesDto>>() {
                    })
                    .getBody();
            String test = null;
            if (response != null) {
                for (AttributesDto attributesDto : response) {
                    if (attributesDto.getNameDevice() != null)
                        test += SPACE + attributesDto.getNameDevice();
                }
            }
            Notification.show("Пожалуйста, выберите или введите значение. " + test);
            close(); // Закрыть диалог после сохранения
        });
        add(comboBox, saveButton);
    }

    private ComboBox<String> createNewCombox() {
        ComboBox<String> comboBox = new ComboBox<>("Выберите элемент");
        comboBox.setAllowCustomValue(true); // Разрешить ввод нового значения
        comboBox.addCustomValueSetListener(event -> {
            String value = event.getDetail();
            if (value == null || value.trim().isEmpty()) {
                Notification.show("Пустое значение не разрешено!");
                comboBox.clear(); // Сбросить значение
            }
        });
        DataProvider<String, String> dataProvider = DataProvider.fromFilteringCallbacks(
                query -> {
                    log.error(query + " IS TEST");
                    List<AttributesDto> response = new LinkedList<>();
                    if (query != null) {
                        response = restClient.get()
                                .uri(uriBuilder -> uriBuilder
                                        .path("/find-attributes-nameDevice/{nameDevice}")
                                        .queryParam("offset", query.getOffset())
                                        .queryParam("limit", query.getLimit())
                                        .build(query)) // Подставляем значение переменной nameDevice
                                .retrieve()
                                .toEntity(new ParameterizedTypeReference<List<AttributesDto>>() {
                                })
                                .getBody();
                    }
                    List<String> attributesList = new LinkedList<>();
                    if (response != null) {
                        for (AttributesDto attributesDto : response)
                            attributesList.add(attributesDto.getNameDevice());
                    }
                    return attributesList.stream();
                }
                ,
                Query::getRequestedRangeEnd
        );
        *//*comboBox.setDataProvider(dataProvider, query -> query);*//*
        comboBox.setItems(dataProvider);
        return comboBox;
    }

    private Stream<String> filterUserInputInCombox(String queryFilter, Integer offset, Integer limit) {
        *//*RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        AttributesEndpoints attributesEndpoints = HttpServiceProxyFactory
                .builderFor(restClientAdapter)
                .build()
                .createClient(AttributesEndpoints.class);
        log.info(queryFilter + EMPTY_SPACE + offset + EMPTY_SPACE + limit);
        List<AttributesDto> attributesDtoList = Optional.ofNullable(
                        attributesEndpoints.findAttributesByName(queryFilter, offset, limit))
                .map(request -> request).orElse(new LinkedList<>());*//*
        log.error(queryFilter + " IS TEST");
        List<AttributesDto> response = new LinkedList<>();
        if (queryFilter != null) {
            response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/find-attributes-nameDevice/{nameDevice}")
                            .queryParam("offset", offset)
                            .queryParam("limit", limit)
                            .build(queryFilter)) // Подставляем значение переменной nameDevice
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<List<AttributesDto>>() {
                    })
                    .getBody();
        }
        List<String> attributesList = new LinkedList<>();
        if (response != null) {
            for (AttributesDto attributesDto : response)
                attributesList.add(attributesDto.getNameDevice());
        }
        return attributesList.stream();
    }
 ------------------*/
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

