//
//  ReactoViewController.m
//  Reacto
//
//  Created by Ashkan Nasseri on 7/11/14.
//  Copyright (c) 2014 Reacto. All rights reserved.
//

#import "ReactoViewController.h"

@interface ReactoViewController ()

@end

@implementation ReactoViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    NSString *fullURL = [NSString stringWithFormat:@"http://reacto.herokuapp.com/app/index.html#fblogin/%@/%@",
                         self.userId, [self.userName stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]];
    
    //NSString *fullURL = @"http://reacto.herokuapp.com/app/index.html";
    NSURL *url = [NSURL URLWithString:fullURL];
    NSURLRequest *requestObj = [NSURLRequest requestWithURL:url];
    [_reactoWebView loadRequest:requestObj];
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
