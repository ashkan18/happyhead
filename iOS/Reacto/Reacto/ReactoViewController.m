//
//  ReactoViewController.m
//  Reacto
//
//  Created by Ashkan Nasseri on 7/2/14.
//  Copyright (c) 2014 Reacto. All rights reserved.
//

#import "ReactoViewController.h"

@interface ReactoViewController ()

@end

@implementation ReactoViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
	
    NSString *fullURL = @"http://reacto.herokuapp.com/app/index.html";
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
