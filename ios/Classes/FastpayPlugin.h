#import <Flutter/Flutter.h>
#import <OPPWAMobile/OPPWAMobile.h>

@interface FastpayPlugin : NSObject<FlutterPlugin>{
  OPPPaymentProvider *provider;
  NSString *checkoutID;
}
@end
