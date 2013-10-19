package com.superone.superone.listeners;

import com.superone.superone.requests.RequestTask;

/**
 * Created by BadMojo on 22/09/13.
 */
public interface RequestTaskListener {
    public void onRequestComplete(String data, RequestTask request);
    public void onRequestFailed(String error, RequestTask request);
}
