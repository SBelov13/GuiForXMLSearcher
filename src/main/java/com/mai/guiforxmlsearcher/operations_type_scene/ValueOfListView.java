/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mai.guiforxmlsearcher.operations_type_scene;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Sergey
 */
public class ValueOfListView implements Comparable<ValueOfListView> {

    private final String key;
    private final List<File> files;

    public ValueOfListView(String key, List<File> files) {
        this.key = key;
        this.files = files;
    }

    public List<File> getFiles() {
        return files;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return key;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.key);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ValueOfListView other = (ValueOfListView) obj;
        if (!Objects.equals(this.key, other.key)) {
            return false;
        }
        if (!Objects.equals(this.files, other.files)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(ValueOfListView o) {
        return key.compareTo(o.key);
    }

}
