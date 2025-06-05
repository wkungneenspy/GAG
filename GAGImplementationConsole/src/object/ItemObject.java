/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package object;

import java.io.Serializable;

/**
 *
 * @author willyk
 */
public class ItemObject implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    String name;
    
    String id;
    
    String refItem;
    
    String content;

    public ItemObject() {
    }

    public ItemObject(String name, String id, String refItem, String content) {
        this.name = name;
        this.id = id;
        this.refItem = refItem;
        this.content = content;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefItem() {
        return refItem;
    }

    public void setRefItem(String refItem) {
        this.refItem = refItem;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    
    
}
