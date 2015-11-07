package com.hongdazeng;

import android.accounts.AccountsException;
import android.app.Activity;

import com.hongdazeng.core.BootstrapService;

import java.io.IOException;

public interface BootstrapServiceProvider {
    BootstrapService getService(Activity activity) throws IOException, AccountsException;
}
