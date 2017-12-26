/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mai.guiforxmlsearcher.operations_type_scene;

import java.util.Map;
import java.util.Set;
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
    }

    Map<String, Set<String>> atrMap;

    public Map<String, Set<String>> getAtrMap() {
        return atrMap;
    }

    public void refrashElement(Map<String, Set<String>> atrMap) {
        if (atrMap == null || atrMap.isEmpty()) {
            return;
        }
        fillatributeComboBox();
        fillvaluesListView();
    }

    private void fillatributeComboBox() {
        valuesListView.getItems().addAll(atrMap.keySet());
    }

    private void fillvaluesListView() {
        String atr = (String) atributeComboBox.getValue();
        Set<String> values = atrMap.get(atr);
        if (values != null) {
            valuesListView.getItems().addAll(values);
        }
    }

    private void initListener() {

    }

}
