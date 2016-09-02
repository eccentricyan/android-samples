package jp.samples.github.bundler;

import android.os.Bundle;

import org.parceler.Parcels;

import java.util.List;

import icepick.Bundler;

public class ListBundler implements Bundler<List<?>> {

    @Override
    public void put(String key, List<?> value, Bundle bundle) {
        bundle.putParcelable(key, Parcels.wrap(value));
    }

    @Override
    public List<?> get(String key, Bundle bundle) {
        return Parcels.unwrap(bundle.getParcelable(key));
    }

}
