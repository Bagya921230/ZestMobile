package com.inovaitsys.inozest.listeners;

/**
 * Created by Milan on 5/6/17.
 */

public interface GridItemClickListener {

    void onItemClick(Object object);

    void onItemClick(String id);

    void onItemClick(String id, String msgId);
}
