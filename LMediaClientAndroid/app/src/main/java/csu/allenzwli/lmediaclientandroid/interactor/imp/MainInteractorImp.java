package csu.allenzwli.lmediaclientandroid.interactor.imp;

import java.util.ArrayList;
import java.util.List;

import csu.allenzwli.lmediaclientandroid.base.BaseLazyFragment;
import csu.allenzwli.lmediaclientandroid.interactor.MainInteractor;
import csu.allenzwli.lmediaclientandroid.ui.fragment.AdminManageFragment;
import csu.allenzwli.lmediaclientandroid.ui.fragment.LocalMusicFragment;
import csu.allenzwli.lmediaclientandroid.ui.fragment.LocalVideoFragment;
import csu.allenzwli.lmediaclientandroid.ui.fragment.OnlineMusicFragment;
import csu.allenzwli.lmediaclientandroid.ui.fragment.OnlineVideoFragment;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class MainInteractorImp implements MainInteractor {
    @Override
    public List<BaseLazyFragment> getPagerFragments() {
        List<BaseLazyFragment> fragments = new ArrayList<>();
        fragments.add(new LocalMusicFragment());
        fragments.add(new OnlineMusicFragment());
        fragments.add(new LocalVideoFragment());
        fragments.add(new OnlineVideoFragment());
        fragments.add(new AdminManageFragment());
        return fragments;
    }
}
