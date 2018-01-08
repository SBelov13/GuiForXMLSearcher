/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mai.guiforxmlsearcher.operations_type_scene;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

/**
 *
 * @author Sergey
 */
public class SearchByTagBahavior implements Bahavior {

    private final ListView valuesListView;
    private final ComboBox tagComboBox;

    public SearchByTagBahavior(ListView valuesListView, ComboBox atributeComboBox) {
        this.valuesListView = valuesListView;
        this.tagComboBox = atributeComboBox;
        initListener();
    }

    Map<String, Map<String, List<File>>> atrMap;

    public Map<String, Map<String, List<File>>> getAtrMap() {
        return atrMap;
    }

    public void refrashElement(Map<String, Map<String, List<File>>> atrMap) {
        if (atrMap == null || atrMap.isEmpty()) {
            return;
        }
        this.atrMap = atrMap;
        fillatributeComboBox();
        fillvaluesListView();
    }

    private void fillatributeComboBox() {
        tagComboBox.getItems().clear();
        Set<String> keys = atrMap.keySet();
        if (keys != null) {
            List<String> k = new ArrayList<>(keys);
            Collections.sort(k);
            tagComboBox.getItems().addAll(k);
            tagComboBox.setValue(keys.iterator().next());
        }
    }

    private void fillvaluesListView() {
        valuesListView.getItems().clear();
        String atr = (String) tagComboBox.getValue();
        if (atr == null) {
            return;
        }
        List<ValueOfListView> listViews = new ArrayList<>();
        for (Entry<String, List<File>> entry : atrMap.get(atr).entrySet()) {
            listViews.add(new ValueOfListView(entry.getKey(), entry.getValue()));
        }
        Collections.sort(listViews);
        valuesListView.getItems().addAll(listViews);
    }

    private void initListener() {
        tagComboBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                fillvaluesListView();
            }
        });
    }

    @Override
    public void setVisible(boolean value) {
        tagComboBox.setVisible(value);
        valuesListView.setVisible(value);
    }
}
