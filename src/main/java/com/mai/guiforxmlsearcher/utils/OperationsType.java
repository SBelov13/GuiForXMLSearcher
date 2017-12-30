/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mai.guiforxmlsearcher.utils;

/**
 *
 * @author Sergey
 */
public enum OperationsType {
    SEARCH_BY_TAG("Поиск и копирование по тегу"),
    DECOMPOSE_BY_FOLDER_IN_TXT("Преобразовать в txt и разложить в папки выбранного тега"),;

    private final String name;

    private OperationsType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
