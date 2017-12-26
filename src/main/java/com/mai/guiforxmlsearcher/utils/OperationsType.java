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
    SEARCH_BY_TAG("Поиск по тегу"),;

    private final String name;

    private OperationsType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
