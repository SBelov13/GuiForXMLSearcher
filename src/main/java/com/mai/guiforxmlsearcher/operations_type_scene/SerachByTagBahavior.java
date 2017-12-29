/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mai.guiforxmlsearcher.operations_type_scene;

import java.util.Map;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

/**
 *
 * @author Sergey
 */
public class SerachByTagBahavior {

    private final ListView valuesListView;
    private final ComboBox atributeComboBox;

    public SerachByTagBahavior(ListView valuesListView, ComboBox atributeComboBox) {
        this.valuesListView = valuesListView;
        this.atributeComboBox = atributeComboBox;
        initListener();
    }

    Map<String, Set<String>> atrMap;

    public Map<String, Set<String>> getAtrMap() {
        return atrMap;
    }

    public void refrashElement(Map<String, Set<String>> atrMap) {
        if (atrMap == null || atrMap.isEmpty()) {
            return;
        }
        this.atrMap = atrMap;
        fillatributeComboBox();
        fillvaluesListView();
    }

    private void fillatributeComboBox() {
        Set<String> keys = atrMap.keySet();
        if (keys != null) {
            atributeComboBox.getItems().addAll(atrMap.keySet());
            atributeComboBox.setValue(keys.iterator().next());
        }

    }

    private void fillvaluesListView() {
        String atr = (String) atributeComboBox.getValue();
        Set<String> values = atrMap.get(atr);
        if (values != null) {
            valuesListView.getItems().addAll(values);
        }
    }

    private void initListener() {
        atributeComboBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (atributeComboBox.getValue() == null) {
                    return;
                }
                Set<String> values = atrMap.get((String) atributeComboBox.getValue());
                if (values != null) {
                    valuesListView.getItems().addAll(values);
                }
            }
        });
    }
}
