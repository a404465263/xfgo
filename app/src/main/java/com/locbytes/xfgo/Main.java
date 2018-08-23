package com.locbytes.xfgo;

import android.content.Context;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class Main implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if(lpparam.packageName.equals("com.bilibili.fatego")){

            findAndHookMethod("com.netease.htprotect.HTProtect",lpparam.classLoader,"getDataSign", Context.class, String.class, int.class, new XC_MethodHook() {
                /*
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    //XposedBridge.log(param.args[0].getClass().toString());
                    //XposedBridge.log(param.args[1].toString());
                    //XposedBridge.log(param.args[2].toString());
                    super.beforeHookedMethod(param);
                }
                */
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    //XposedBridge.log(param.args[0].toString());
                    //XposedBridge.log(param.args[1].toString());
                    //XposedBridge.log(param.args[2].toString());
                    //XposedBridge.log(param.getResult().toString());
                    //Log.d("fgo sign: ",param.getResult().toString());
                    if(param.args[1].toString().contains("battle_setup")||param.args[1].toString().contains("battle_resume")){
                        param.setResult("");
                    }
                    super.afterHookedMethod(param);
                }
            });

        }
    }

}
