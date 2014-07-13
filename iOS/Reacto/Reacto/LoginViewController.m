//
//  ReactoViewController.m
//  Reacto
//
//  Created by Ashkan Nasseri on 7/2/14.
//  Copyright (c) 2014 Reacto. All rights reserved.
//

#import "LoginViewController.h"
#import "ReactoViewController.h"

@interface LoginViewController ()
@property(nonatomic) NSString *userId;
@property(nonatomic) NSString *userName;

@end

@implementation LoginViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    FBLoginView *loginView = [[FBLoginView alloc] initWithReadPermissions:
                              @[@"public_profile", @"email", @"user_friends"]];
    
    loginView.delegate = self;
    [self.view addSubview:loginView];
    
}

// This method will be called when the user information has been fetched
- (void)loginViewFetchedUserInfo:(FBLoginView *)loginView
                            user:(id<FBGraphUser>)user {
    
    self.userId = user.id;
    self.userName = user.name;
    
    [self performSegueWithIdentifier:@"showWebView" sender:self];
    
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    if([segue.identifier isEqualToString:@"showWebView"]){
        ReactoViewController *controller = (ReactoViewController *)segue.destinationViewController;
        controller.userId = self.userId;
        controller.userName = self.userName;
    }
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
