//
//  ReactoViewController.h
//  Reacto
//
//  Created by Ashkan Nasseri on 7/2/14.
//  Copyright (c) 2014 Reacto. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <FacebookSDK/FacebookSDK.h>

@interface LoginViewController : UIViewController <FBLoginViewDelegate>
@property (weak, nonatomic) IBOutlet UIWebView *reactoWebView;

@end
