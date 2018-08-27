# xfgo
Fate/Grand Order b服过sign检测xposed模块

当前游戏客户端版本: 1.21.2

如有任何问题请提交issue，我会及时进行回复。

核心代码 [Main.java](https://github.com/locbytes/xfgo/blob/master/app/src/main/java/com/locbytes/xfgo/Main.java)

## 说明

**本模块工作原理为修改验证函数返回值或传入参数，开启本模块后则必须使用科技，否则报201。**

**如果不使用科技，请关闭此模块并重启手机。**

**B服FGO经测试无法在VirtualXposed中运行，并非本模块造成的问题。**

Android版本需求: >=4.4.2

Xposed API需求: >=54

经测试可以通过[Magisk](https://forum.xda-developers.com/apps/magisk)以框中框的方式来使用本模块。

因加入了撤退胜利的数据处理逻辑，导致需要使用JSONArray类，要求Android SDK最低为API-19，故将min SDK更改为API-19。

Android版本低于4.4.2的用户只能使用v1.1版的模块，即只能修改战斗数据，而无法使用撤退胜利。

v1.1版：[https://github.com/locbytes/xfgo/releases/tag/v1.1](https://github.com/locbytes/xfgo/releases/tag/v1.1)

**不使用撤退胜利功能的用户请使用v1.1版，使用新版会造成撤退时报错误201，想要使用撤退胜利功能的用户请等待自己科技的作者适配完成。**

## 使用方法

用户需安装并应用此模块。

### 修改战斗数据

科技作者需在科技服务端将`response`的`sign`改为`""`。

```json
{
    "response": {},
    "cache": {},
    "sign": ""
}
```

### 撤退胜利

科技作者需在科技服务端对request进行修改，修改方法，具体示例代码请先参考模块中的代码。

先监测包含`key=battleresult`的`requestData`，对该`requestData`进行处理。

拆分数据，获得`result`的value值，该值为一个json字符串。

对该json数据进行处理，有如下数据需要修改，参数意义请参考模块代码中的注释

```json
{
    "battleResult": 1,
    "elapsedTurn": 11,
    "aliveUniqueIds": []
}
```

重新建立`requestData`，无需对sign进行处理。

## 科技服务端代码参考

暂未添加撤退胜利的相关示例代码

AnyProxy版: [https://github.com/locbytes/FGO_AnyProxy](https://github.com/locbytes/FGO_AnyProxy)

Fiddler版: [https://github.com/locbytes/FGO_FiddlerScript](https://github.com/locbytes/FGO_FiddlerScript)
