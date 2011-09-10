package com.codeswimmer.android.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;

public class FragmentHelper {
//    @SuppressWarnings("unused")
    private static final String TAG = FragmentHelper.class.getSimpleName();
    private final FragmentManager fm;
    
    public FragmentHelper(FragmentManager fm) { this.fm = fm; }
    
    public void showFragment(int containerViewId, Fragment fragment, String tag) {
        Log.i(TAG, String.format("showFragment() - %s: %s", tag, Integer.toHexString(containerViewId)));
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(containerViewId, fragment, tag);
        transaction.show(fragment);
        transaction.commit();
    }
    
    /*
    @SuppressWarnings("serial")
    public static final class InvalidContainerViewIdException extends FragmentHelperException {
        private final int[] containerViewIdParam;
        
        public InvalidContainerViewIdException(int ... containerViewIdParam) { this.containerViewIdParam = containerViewIdParam; }
        
        public int[] getContainerViewIdParam() { return containerViewIdParam; }
    }
    
    @SuppressWarnings("serial")
    public static class FragmentHelperException extends Exception { public FragmentHelperException() {} }
    
    public void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(containerViewId, fragment);
        transaction.show(fragment);
        transaction.commit();
    }
    
    public void removeFragment(Fragment fragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(fragment);
        transaction.remove(fragment);
        transaction.commit();
    }
    
    void showFragment(Fragment fragment, int ... containerViewId) throws InvalidContainerViewIdException {
        FragmentTransaction transaction = fm.beginTransaction();
        
        ensureFragmentHasBeenAdded(transaction, fragment, containerViewId);
            
        transaction.show(fragment);
        transaction.commit();
    }
    
    private void ensureFragmentHasBeenAdded(FragmentTransaction transaction, Fragment fragment, int ... containerViewId) throws InvalidContainerViewIdException {
        if (fragmentNotAdded(fragment) == false)
            transaction.add(extractContainerViewId(containerViewId), fragment);
    }
    
    private int extractContainerViewId(int ... optionalParams) throws InvalidContainerViewIdException {
        throwExceptionIfContainerViewIdIsInvalid(optionalParams);
        return optionalParams[0];
    }
    
    private void throwExceptionIfContainerViewIdIsInvalid(int ... optionalParams) throws InvalidContainerViewIdException {
        if (optionalParams == null || optionalParams.length <= 0)
            throw new InvalidContainerViewIdException(optionalParams);
    }
    */
}
