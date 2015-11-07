package com.hongdazeng;

import com.hongdazeng.authenticator.BootstrapAuthenticatorActivity;
import com.hongdazeng.core.TimerService;
import com.hongdazeng.ui.BootstrapActivity;
import com.hongdazeng.ui.BootstrapFragmentActivity;
import com.hongdazeng.ui.BootstrapTimerActivity;
import com.hongdazeng.ui.CheckInsListFragment;
import com.hongdazeng.ui.MainActivity;
import com.hongdazeng.ui.NavigationDrawerFragment;
import com.hongdazeng.ui.NewsActivity;
import com.hongdazeng.ui.NewsListFragment;
import com.hongdazeng.ui.UserActivity;
import com.hongdazeng.ui.UserListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                AndroidModule.class,
                BootstrapModule.class
        }
)
public interface BootstrapComponent {

    void inject(BootstrapApplication target);

    void inject(BootstrapAuthenticatorActivity target);

    void inject(BootstrapTimerActivity target);

    void inject(MainActivity target);

    void inject(CheckInsListFragment target);

    void inject(NavigationDrawerFragment target);

    void inject(NewsActivity target);

    void inject(NewsListFragment target);

    void inject(UserActivity target);

    void inject(UserListFragment target);

    void inject(TimerService target);

    void inject(BootstrapFragmentActivity target);
    void inject(BootstrapActivity target);


}
