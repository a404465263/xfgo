package com.locbytes.xfgo;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

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
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    if(param.args[1].toString().contains("battleresult")){
                        //XposedBridge.log("Battle Result");
                        String requestData=param.args[1].toString();
                        String[] dataArray=requestData.split("&");
                        /*
                        int i=0;
                        for (String aDataArray : dataArray) {
                            XposedBridge.log(i+" "+aDataArray);
                            ++i;
                        }
                        */

                        // result
                        //XposedBridge.log(dataArray[12]);
                        JSONObject jsonObject = new JSONObject(dataArray[12].substring(7));

                        StringBuilder newRequestData = new StringBuilder();

                        // 检查是否为撤退
                        Boolean battleCancel;
                        if(jsonObject.getInt("battleResult")==3){
                            battleCancel=true;
                            // battleResult=1 任务完成
                            jsonObject.put("battleResult",1);
                            // elapsedTurn=11 回合数11
                            jsonObject.put("elapsedTurn",11);
                            // 因JSONArray类要求，只能将最低Android SDK Version由API-17为API-19
                            JSONArray aliveUniqueIds = jsonObject.getJSONArray("aliveUniqueIds");
                            // aliveUniqueIds=[] 敌方无存活
                            aliveUniqueIds = new JSONArray();
                            jsonObject.put("aliveUniqueIds",aliveUniqueIds);
                            //XposedBridge.log(jsonObject.toString());
                            dataArray[12]="result="+jsonObject.toString();
                            //XposedBridge.log(dataArray[12]);
                            int i=1;
                            for (String aDataArray : dataArray) {
                                newRequestData.append(aDataArray);
                                if(i<dataArray.length){
                                    newRequestData.append("&");
                                }
                                ++i;
                            }
                        }else{
                            battleCancel=false;
                        }

                        if(battleCancel){
                            param.args[1]=newRequestData.toString();
                        }

                    }
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    if(param.args[1].toString().contains("battle_setup")||param.args[1].toString().contains("battle_resume")){
                        //XposedBridge.log("Battle Setup/Resume");
                        param.setResult("");
                    }
                    super.afterHookedMethod(param);
                }
            });
        }
    }
}
