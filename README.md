# AndroidEventGenerator
#This is a very simple demo for how to use AWS lambda function on Android
(1) create AWS Cognito pool
     Enable access to unauthenticated identities
     
(2) create lambda function called cloundFunction in AWS lambda console
console.log('Loading function');
exports.handler = function(event, context) {
    console.log('Received event:', JSON.stringify(event, null, 2));
    context.done(null, event + 'Lambda'); //will send "Hello From Lambda"
};

(3) to put the following policy in the AWS IAM Unauth role.

   {
            "Effect": "Allow",
            "Action": [
                "lambda:invokefunction"
            ],
            "Resource": [
                "arn:aws:lambda:us-east-1:youraccountId:function:cloudFunction"
            ]
        }

(4) download and compile the code using Android studio


Notes:

(1) The above code runs good on the real android phone.


(2) Running on the Android Emulator, but I always got the error:

 final LambdaFunctionsInterface myInterface = factory.build(LambdaFunctionsInterface.class);

Method threw 'java.lang.UnsupportedOperationException' exception. Cannot evaluate $Proxy0.toString()
 
 then it crashed the app in the emultor. what could be wrong?
 
