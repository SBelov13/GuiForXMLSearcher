/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mai.guiforxmlsearcher.utils.operations;

import com.mai.guiforxmlsearcher.operations_type_scene.Bahavior;

/**
 *
 * @author Sergey
 */
public class Operation {

    private final OperationsType operationsType;
    private final Bahavior bahavior;

    public Operation(OperationsType operationsType, Bahavior bahavior) {
        this.operationsType = operationsType;
        this.bahavior = bahavior;
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
        final Operation other = (Operation) obj;
        return operationsType.equals(other.operationsType);
    }

    @Override
    public int hashCode() {
        return operationsType.hashCode();
    }

    @Override
    public String toString() {
        return operationsType.toString();
    }

}
