# fastpay

Integration plugin for open payment platforms for Android and iOS mobile devices.

## Usage
```
static Future<String>  checkoutActitvity(String amt) async {
    var data ="";
    try {
      data = await rootBundle.loadString('assets/config.json');
    }catch(e){print(e);}
    final String version = await _channel.invokeMethod('checkoutActivity',{"amt":amt, "data": data});
    return version;
  }
```

