#import "FastpayPlugin.h"
#import <OPPWAMobile/OPPWAMobile.h>

@implementation FastpayPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  //[SwiftFastpayPlugin registerWithRegistrar:registrar];
  FlutterMethodChannel* channel = [FlutterMethodChannel
          methodChannelWithName:@"fastpay"
                binaryMessenger:[registrar messenger]];
    FastpayPlugin* instance = [[FastpayPlugin alloc] init];
    [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
    if ([call.method isEqualToString:@"checkoutActivity"]) {
        /*Request Checkout ID*/ //@"https://YOUR_URL/?amount=100&currency=EUR&paymentType=DB"
        //
        //NSURLRequest *merchantServerRequest = [[NSURLRequest alloc] initWithURL:[NSURL URLWithString:[NSString stringWithFormat:@"%@?amount=%@&currency=%@&paymentType=%@", @"http://52.59.56.185:80/token", @"100", @"EUR", @"DB"]]];
        NSDictionary *arguments = call.arguments;
        if(arguments.count == 0){
            result(@"No hay Parametros");
            return;
        }
        
        if([arguments objectForKey:@"amt"]){
            NSString *amount = arguments[@"amt"];
            NSString *base = @"http://52.59.56.185:80/token?amount=";
            NSString *endBase = @"&paymentType=PA&testMode=INTERNAL&currency=EUR&notificationUrl=http://52.59.56.185:80/notification";
            NSString *merchantStr = [NSString stringWithFormat: @"%@%@%@",base,amount,endBase];

            NSURL *merchantUrl = [[NSURL alloc] initWithString:merchantStr];

            NSURLRequest *merchantServerRequest = [[NSURLRequest alloc] initWithURL: merchantUrl];


            [[[NSURLSession sharedSession] dataTaskWithRequest:merchantServerRequest completionHandler:^(NSData * _Nullable data, NSURLResponse * _Nullable response, NSError * _Nullable error) {
                // TODO: Handle errors

                if (data != nil) {
                    NSDictionary *JSON = [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:nil];

                    if (JSON != nil) {
                        self->checkoutID = JSON[@"checkoutId"];
                    }
                    else {
                        result(@"Failed1");
                    }
                }
                else {
                    result(@"Failed2");
                }


                [self initialisedPaymet:@"20" withComplition:^(NSString* status) {
                    NSLog(@"The task is complete : %@", status);
                    result(status);
                }];

            }] resume];
        }else {
            result(@"Ingrese un monto");
        }
            
        
        
    }


    else if ([@"getPlatformVersion" isEqualToString:call.method]) {
        result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
    }
    else {
        result(FlutterMethodNotImplemented);
  }
}

#pragma Initialize Payment Provider
- (void)initialisedPaymet: (NSString*)amount withComplition: (void (^)(NSString*))completionBlock {
    provider = [OPPPaymentProvider paymentProviderWithMode:OPPProviderModeTest];

    /*Configure the Checkout Settings*/
    OPPCheckoutSettings *checkoutSettings = [[OPPCheckoutSettings alloc] init];
    checkoutSettings.paymentBrands = @[@"VISA", @"DIRECTDEBIT_SEPA", @"MASTER", @"PAYPAL"]; // Set available payment brands for your shop
    checkoutSettings.shopperResultURL = @"msdk.demo.async://payment"; // Set shopper result URL

    /*Present the Checkout Page*/
    OPPCheckoutProvider *checkoutProvider = [OPPCheckoutProvider checkoutProviderWithPaymentProvider:provider checkoutID:checkoutID settings:checkoutSettings];

    dispatch_async(dispatch_get_main_queue(), ^{
        // Since version 2.13.0
        [checkoutProvider presentCheckoutForSubmittingTransactionCompletionHandler:^(OPPTransaction * _Nullable transaction, NSError * _Nullable error) {
            if (error) {
                completionBlock(@"error occurred");
                // Executed in case of failure of the transaction for any reason
            } else if (transaction.type == OPPTransactionTypeSynchronous)  {
                // Send request to your server to obtain the status of the synchronous transaction
                // You can use transaction.resourcePath or just checkout id to do it
                completionBlock(@"competed1");
            } else {
                // The SDK opens transaction.redirectUrl in a browser
                // See 'Asynchronous Payments' guide for more details
                completionBlock(@"competed2");
            }
        } cancelHandler:^{
            // Executed if the shopper closes the payment page prematurely
            completionBlock(@"cancelled");
        }];
    });
}
@end
