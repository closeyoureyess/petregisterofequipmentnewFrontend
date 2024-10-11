package com.petregisterofequipmentnew.petregisterofequipmentnew.front.exceptions;

public enum DescriptionExeptions {

    GENERATION_ERROR("Возникла ошибка в системе"),
    PROBLEM_SAVE_DATA_ERROR("Возникла ошибка при сохранении данных"),
    COUNTING_ERROR("Ошибка при подсчете элементов: "),
    BACKEND_ERROR_RESPONSE("Ошибка при запросе к бэкенду: ");

    private String description;

    DescriptionExeptions(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
